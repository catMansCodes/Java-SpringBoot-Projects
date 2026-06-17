package com.catmanscodes.todoapis.service;

import com.catmanscodes.todoapis.dto.TodoDto;
import com.catmanscodes.todoapis.entity.Todo;
import com.catmanscodes.todoapis.exception.ResourceNotFound;
import com.catmanscodes.todoapis.repository.TodoRepository;
import com.catmanscodes.todoapis.utils.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = todoMapper.mapToTodoEntity(todoDto);
        Todo savedTodoObj = todoRepository.save(todo);

        return todoMapper.mapToTodoDto(savedTodoObj);
    }

    @Override
    public List<TodoDto> findAllTodo() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoDto> todoDtos = new ArrayList<>();

        todos.forEach(t -> todoDtos.add(todoMapper.mapToTodoDto(t)));

        return todoDtos;
    }

    @Override
    public TodoDto findById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No todo found with id: " + id));

        return todoMapper.mapToTodoDto(todo);
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No todo found with id: " + id));

        todo.setTitle(todoDto.title());
        todo.setDescription(todoDto.description());
        todo.setCompleted(todoDto.completed());

        Todo save = todoRepository.save(todo);

        return todoMapper.mapToTodoDto(save);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No todo found with id: " + id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No todo found with id: " + id));
        todo.setCompleted(Boolean.TRUE);

        Todo save = todoRepository.save(todo);

        return todoMapper.mapToTodoDto(save);
    }

    @Override
    public TodoDto incompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No todo found with id: " + id));
        todo.setCompleted(Boolean.FALSE);

        Todo save = todoRepository.save(todo);

        return todoMapper.mapToTodoDto(save);
    }
}
