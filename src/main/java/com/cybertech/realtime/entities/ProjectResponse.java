package com.cybertech.realtime.entities;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private int id;
    private int customerId;
    private String name;
    private boolean archived;
    private long created;
    private Map<String, Boolean> allowedActions;
    private String description;
}
