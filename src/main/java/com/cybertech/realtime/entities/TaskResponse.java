package com.cybertech.realtime.entities;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private int id;
    private String name;
    private String description;
    private long created;
    private String status;
    private int workflowStatusId;
    private int typeOfWorkId;
    private Map<String,Boolean> allowedActions;
    private String deadline;
    private int estimatedTime;
    private int customerId;
    private int projectId;

}
