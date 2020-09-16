package com.example.its.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "issue")
public class Issue implements Serializable {
    @Id
    private String id;

    private String title;

    private String description;

    @Transient
    private MultipartFile[] attachmentFiles;

    private String attachmentPath;

    private User owner;

    private User assignTo;

    private Status status;

    private Type type;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;
}
