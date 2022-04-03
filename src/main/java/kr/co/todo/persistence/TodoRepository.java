package kr.co.todo.persistence;

import kr.co.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity,String> {
    List<TodoEntity> findByUserId(String userId);
}
