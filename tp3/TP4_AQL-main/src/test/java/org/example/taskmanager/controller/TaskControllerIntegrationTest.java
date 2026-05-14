package org.example.taskmanager.controller;


import org.example.tp4.taskmanager.service.TaskService;
import org.example.tp4.taskmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
public class TaskControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("password");

    @Autowired
    private TaskService taskService;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Test
    void testCreateTask() {
        Task task = new Task("Tâche " + System.currentTimeMillis(), "Description de la tâche");
        taskService.saveTask(task);

        Long taskId = task.getId();
        Optional<Task> retrievedTask = taskService.findTaskById(taskId);

        assertTrue(retrievedTask.isPresent());
        assertEquals(task.getTitle(), retrievedTask.get().getTitle());
    }

    @Test
    void testGetTask() {
        Task task = createAndSaveTask();

        Long taskId = task.getId();
        Optional<Task> retrievedTask = taskService.findTaskById(taskId);

        assertTrue(retrievedTask.isPresent());
        assertEquals(task.getTitle(), retrievedTask.get().getTitle());
    }

    @Test
    void testDeleteTask() {
        Task task = createAndSaveTask();

        Long taskId = task.getId();
        taskService.deleteTaskById(taskId);

        Optional<Task> retrievedTask = taskService.findTaskById(taskId);

        assertFalse(retrievedTask.isPresent());
    }

    private Task createAndSaveTask() {
        Task task = new Task("Tâche " + System.currentTimeMillis(), "Description temporaire");
        taskService.saveTask(task);
        return task;
    }
}
