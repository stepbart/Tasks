package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @InjectMocks
    private DbService mockedService;

    @MockBean
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void getTasksTest() throws Exception {
        //Given
        Task task1 = new Task(1L,"title1","content1");
        Task task2 = new Task(2L,"title2","content2");
        List<Task> tasks= new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        TaskDto taskDto1 = new TaskDto(1L,"title1","content1");
        TaskDto taskDto2 = new TaskDto(2L,"title2","content2");
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto1);
        taskDtoList.add(taskDto2);

        when(service.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("title2")));
    }

    @Test
    void getTaskTest() throws Exception {
        //Given
        Task task = new Task(1L,"title1","content1");
        TaskDto taskDto = new TaskDto(1L,"title1","content1");

        when(service.getTask(1L)).thenReturn(java.util.Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/task/getTask")
                    .param("taskId","1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("title1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("content1")));
    }

    @Test
    void deleteTaskTest(){
        //Given
        Long taskId = 1L;

        //When
        mockedService.deleteTask(taskId);

        //Then
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }

    @Test
    void updateTaskTest() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(12L,"title333","content333");
        Task task = new Task(12L,"title333","content333");

        when(taskMapper.mapToTask(any())).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("title333")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("content333")));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Task createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        return service.saveTask(task);
    }

    @Test
    void createTaskTest() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(142L,"title444","content444");
        Task task = new Task(1425L,"title4445","content4445");

        when(taskMapper.mapToTask(any())).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        //When & Then
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/task/createTask")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1425)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("title4445")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("content4445")));
    }
}