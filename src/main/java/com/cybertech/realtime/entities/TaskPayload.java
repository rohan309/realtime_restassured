package com.cybertech.realtime.entities;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskPayload {

    private String name;
    private String description;
    private String status;
    private int projectId;
    private int typeOfWorkId;
    private int estimatedTime;
}
