package com.example.its.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "position")
public class Position implements Serializable {
    @Id
    private String id;

//    @NotBlank
//    @Indexed(unique = true)
    private String key;

    private String description;
}
