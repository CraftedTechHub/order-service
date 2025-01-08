package com.KalaroApplication.KALARO_ORDERS.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StandardResponse {
    private int httpStatusCode;
    private String httpMessage;
}
