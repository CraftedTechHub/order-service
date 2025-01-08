package com.KalaroApplication.KALARO_ORDERS.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpResponse {
    private int httpStatusCode; // 200, 201, 400, 401, 404, 500
    private String httpMessage; // OK, Created, Bad Request, Unauthorized, Not Found, Internal Server Error
    private Object httpBody;
}
