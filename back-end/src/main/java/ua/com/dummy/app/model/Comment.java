package ua.com.dummy.app.model;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    String body;
    Long postId;
    String username;
    String updatedAt;

}