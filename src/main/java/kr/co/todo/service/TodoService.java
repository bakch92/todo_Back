package kr.co.todo.service;

import kr.co.todo.dto.TodoDTO;
import kr.co.todo.model.TodoEntity;
import kr.co.todo.persistence.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoEntity> createTodo(TodoEntity entity) {
        validate(entity);

        todoRepository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());

        return todoRepository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(String userId) {

        return todoRepository.findByUserId(userId);
    }

    public List<TodoEntity> update(TodoEntity entity) {

        validate(entity);

        Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        original.ifPresent( saveTodo -> {
            saveTodo.setTitle(entity.getTitle());
            saveTodo.setDone((entity.isDone()));

            todoRepository.save(saveTodo);
        });

        return retrieve(entity.getUserId());

    }

    public List<TodoEntity> delete(TodoEntity entity) {
        validate(entity);

        try {
            todoRepository.delete(entity);

        } catch(Exception e) {
            log.error("error deleting entity ", entity.getId(), e);

            throw new RuntimeException("error deleting entity : " + entity.getId());
        }
            return retrieve(entity.getUserId());
    }

    private void validate(TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown User");
            throw new RuntimeException("Unknown User");
        }
    }
}
