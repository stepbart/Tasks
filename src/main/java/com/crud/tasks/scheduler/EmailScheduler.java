package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class EmailScheduler {

    protected static final String SUBJECT = "Tasks: Once a day email";
    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        sendMail(createMail());
    }

    public void sendMail(Mail mail) {
        simpleEmailService.send(mail);
    }

    public String createMessage(long size){
        String message = "Currently in database you got: " + size + " task";
        if (size>1){
            message += "s";
        }
        return message;
    }

    public Mail createMail() {
        Mail mail = new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                createMessage(taskRepository.count()),
                null
        );
        return mail;
    }
}