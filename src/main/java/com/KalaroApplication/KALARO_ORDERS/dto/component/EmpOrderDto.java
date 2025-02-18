package com.KalaroApplication.KALARO_ORDERS.dto.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpOrderDto {
    private String modelName;
    private List<String> sizes;
}
