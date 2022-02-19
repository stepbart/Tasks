package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService service;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    Task task1 = new Task(1L,"title1","content1");
    Task task2 = new Task(2L,"title2","content2");
    List<Task> taskList= new ArrayList<>();

    TaskDto taskDto1 = new TaskDto(1L,"title1","content1");
    TaskDto taskDto2 = new TaskDto(2L,"title2","content2");
    List<TaskDto> taskDtoList= new ArrayList<>();


    @Test
    public void getAllTasksTest(){
        //Given
        taskList.add(task1);
        taskList.add(task2);

        taskDtoList.add(taskDto1);
        taskDtoList.add(taskDto2);

        when(taskRepository.findAll()).thenReturn(taskList);

        //When
        List<Task> tasks = service.getAllTasks();

        //Then
        assertEquals(2,tasks.size());
        assertEquals("title2",tasks.get(1).getTitle());
        assertEquals(1L,tasks.get(0).getId());
    }

    @Test
    public void getTaskTest() throws TaskNotFoundException {
        //Given
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(task1));

        //When
        Task task = service.getTask(1L).orElseThrow();

        //Then
        assertEquals("title1",task.getTitle());
        assertEquals("content1",task.getContent());
    }

    @Test
    public void deleteTaskTest() throws TaskNotFoundException {
        //Given
        doNothing().when(taskRepository).deleteById(1L);

        //When
        service.deleteTask(1L);

        //Then
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void saveTaskTest(){
        //Given
        when(taskRepository.save(task1)).thenReturn(task2);

        //When
        Task task = service.saveTask(task1);

        //Then
        assertEquals("title2",task.getTitle());
        assertEquals("content2",task.getContent());
    }
}
