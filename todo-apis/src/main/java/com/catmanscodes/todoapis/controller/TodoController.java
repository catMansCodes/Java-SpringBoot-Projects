package com.catmanscodes.todoapis.controller;

import com.catmanscodes.todoapis.dto.TodoDto;
import com.catmanscodes.todoapis.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {

        return new ResponseEntity<>(todoService.createTodo(todoDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAllTodo());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Todo deleted");
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id) {

        return ResponseEntity.ok(todoService.completeTodo(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.incompleteTodo(id));
    }

}
