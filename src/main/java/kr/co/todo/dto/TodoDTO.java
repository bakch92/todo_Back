package kr.co.todo.dto;


import kr.co.todo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private String id;

    private String title;
    private boolean isDone;

    public TodoDTO(TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.isDone = entity.isDone();
    }

    public static TodoEntity toEntity(TodoDTO todoDTO) {
        return TodoEntity.builder()
                .id(todoDTO.getId())
                .title(todoDTO.getTitle())
                .isDone(todoDTO.isDone)
                .build();
    }

}
