package com.fullstack.api.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonError {

    private int errorCode;
    private String errorMessage;
}
