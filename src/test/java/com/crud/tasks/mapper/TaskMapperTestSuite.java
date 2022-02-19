package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskTest(){
        //Given
        TaskDto taskDto = new TaskDto(113L,"title_333","content_333");
        Task task = new Task(113L,"title_333","content_333");

        //When
        Task testTask = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(task.getId(),testTask.getId());
        assertEquals(task.getTitle(),testTask.getTitle());
        assertEquals(task.getContent(),testTask.getContent());
    }

    @Test
    public void mapToTaskDtoTest(){
        //Given
        Task task = new Task(113L,"title_333","content_333");
        TaskDto taskDto = new TaskDto(113L,"title_333","content_333");

        //When
        TaskDto testTaskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(taskDto.getId(),testTaskDto.getId());
        assertEquals(taskDto.getTitle(),testTaskDto.getTitle());
        assertEquals(taskDto.getContent(),testTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoListTest(){
        //Given
        Task task1 = new Task(113L,"title_333","content_333");
        Task task2 = new Task(114L,"title_444","content_444");
        Task task3 = new Task(115L,"title_555","content_555");
        TaskDto taskDto1 = new TaskDto(113L,"title_333","content_333");
        TaskDto taskDto2 = new TaskDto(114L,"title_444","content_444");
        TaskDto taskDto3 = new TaskDto(115L,"title_555","content_555");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        //When
        List<TaskDto> taskDtoList= taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(3,taskDtoList.size());
        assertEquals(taskDto1.getId(),taskDtoList.get(0).getId());
        assertEquals(taskDto2.getTitle(),taskDtoList.get(1).getTitle());
        assertEquals(taskDto3.getContent(),taskDtoList.get(2).getContent());
    }
}
