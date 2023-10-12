package com.cybertech.realtime.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProjectPayload {

    private int customerId;
    private String name;
    private boolean archived;
    private String description;
}
