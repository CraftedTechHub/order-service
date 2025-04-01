package com.KalaroApplication.KALARO_ORDERS.dto;

import com.KalaroApplication.KALARO_ORDERS.dto.component.DDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DOrdersDto {
    private String order;
    private List<DDataDto> data = new ArrayList<>();;
}
