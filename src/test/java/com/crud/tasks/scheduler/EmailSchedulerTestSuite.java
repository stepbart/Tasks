package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTestSuite {

    @Mock
    private EmailScheduler mockedEmailScheduler;

    @Autowired
    private EmailScheduler realEmailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Test
    void sendInformationEmailTest() {
        //Given
        Mail mail = new Mail("test@test.pl","test","test",null);
        when(mockedEmailScheduler.createMail()).thenReturn(mail);

        //When
        mockedEmailScheduler.sendInformationEmail();
        mockedEmailScheduler.sendMail(mockedEmailScheduler.createMail());
        simpleEmailService.send(mail);

        //Then
        verify(mockedEmailScheduler,times(1)).sendInformationEmail();
        verify(mockedEmailScheduler,times(1)).sendMail(mail);
        verify(simpleEmailService,times(1)).send(mail);
    }

    @Test
    void createMessageTest(){
        //Given
        String message = "Currently in database you got: 2 tasks";

        //When
        String testText = realEmailScheduler.createMessage(2L);

        //Then
        assertEquals(message,testText);
    }
}
