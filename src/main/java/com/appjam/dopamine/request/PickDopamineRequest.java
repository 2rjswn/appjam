package com.appjam.dopamine.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickDopamineRequest {
    @NotNull
    private Long dopamine;
}
