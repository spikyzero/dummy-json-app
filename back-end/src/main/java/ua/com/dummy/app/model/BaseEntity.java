package ua.com.dummy.app.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}