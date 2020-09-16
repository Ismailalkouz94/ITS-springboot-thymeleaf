package com.example.its.dto;

import com.example.its.entity.Issue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class IssueDTO {

    private String id;
    private String title;
    private String description;
    private Long owner;
    private Long assignTo;
    private Long type;
    private Long status;

    public Issue toIssues() {
        Issue issues = new Issue();
        issues.setId(this.id);
        issues.setTitle(this.title);
        issues.setDescription(this.description);
        return issues;
    }

}
