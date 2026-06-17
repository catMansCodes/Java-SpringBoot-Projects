package com.catmanscodes.todoapis.utils;

import com.catmanscodes.todoapis.dto.TodoDto;
import com.catmanscodes.todoapis.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public Todo mapToTodoEntity(TodoDto todoDto) {
        Todo todo = new Todo();

        todo.setId(todoDto.id());
        todo.setTitle(todoDto.title());
        todo.setDescription(todoDto.description());
        todo.setCompleted(todoDto.completed());

        return todo;
    }

    public TodoDto mapToTodoDto(Todo todo){
        return new TodoDto(
          todo.getId(),
          todo.getTitle(),
          todo.getDescription(),
          todo.getCompleted()
        );
    }
}
