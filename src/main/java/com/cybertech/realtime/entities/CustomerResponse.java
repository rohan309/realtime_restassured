package com.cybertech.realtime.entities;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private int id;
    private String name;
    private boolean archived;
    private long created;
    private String url;
    private Map<String,Boolean> allowedActions;
    private String description;

}
