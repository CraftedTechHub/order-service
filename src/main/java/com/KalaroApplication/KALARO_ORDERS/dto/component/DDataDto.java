package com.KalaroApplication.KALARO_ORDERS.dto.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DDataDto {
    private String startDate;
    private String centerName;
    private List<SizeAndQuantityDto> sizeQuantities = new ArrayList<>();
    private String expectedCompletionDate;
}
