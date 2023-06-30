package com.cybertech.realtime.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPayload {
    private String name;
    private String description;
    private boolean archived;

}
