package com.example.its.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User implements Serializable {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean active;

//    @NotBlank
//    @Indexed(unique = true)
    private String userName;

    @JsonIgnore
    private String password;

    private Role role;

    private Position position;
}
