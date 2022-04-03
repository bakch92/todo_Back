package kr.co.todo.controller;

import kr.co.todo.dto.ResponseDTO;
import kr.co.todo.dto.TodoDTO;
import kr.co.todo.model.TodoEntity;
import kr.co.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
@Slf4j
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<?> todoCreate(@RequestBody TodoDTO dto) {
        String userId = "tempUser";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setId(null);
        entity.setUserId(userId);

        List<TodoEntity> entities = todoService.createTodo(entity);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        log.info("dtos list : " + dtos);

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().todoList(dtos).build();
        log.info("ResponseDTO : " + response.getTodoList());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> todoRetrieve() {
        String userId = "tempUser";

        List<TodoEntity> entities = todoService.retrieve(userId);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().todoList(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> todoUpdate(@RequestBody TodoDTO dto) {
        String userId = "tempUser";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(userId);

        List<TodoEntity> updateTodoList = todoService.update(entity);

        List<TodoDTO> updateDTO = updateTodoList.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().todoList(updateDTO).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> todoDelete(@RequestBody TodoDTO dto) {
        try {
            String userId = "tempUser";

            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);

            List<TodoEntity> updateEntityList = todoService.delete(entity);

            List<TodoDTO> updateDTOList = updateEntityList.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().todoList(updateDTOList).build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.ok().body(response);
        }
    }

}
