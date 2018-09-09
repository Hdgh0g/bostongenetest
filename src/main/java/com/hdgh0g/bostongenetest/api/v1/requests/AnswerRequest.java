package com.hdgh0g.bostongenetest.api.v1.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class AnswerRequest {

    @NotNull
    private UUID appealId;
    @NotNull
    @Size(min = 10, max = 500)
    private String text;

}
