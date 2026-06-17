package com.catmanscodes.todoapis.service;

import com.catmanscodes.todoapis.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto createTodo(TodoDto todoDto);

    List<TodoDto> findAllTodo();

    TodoDto findById(Long id);

    TodoDto updateTodo(Long id, TodoDto todoDto);

    void deleteTodo(Long id);

    TodoDto completeTodo(Long id);

    TodoDto incompleteTodo(Long id);
}
