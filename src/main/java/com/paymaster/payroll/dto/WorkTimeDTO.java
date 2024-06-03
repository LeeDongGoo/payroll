package com.paymaster.payroll.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WorkTimeDTO {

	private Long id;
    private String standardWorkStart;
    private String standardWorkEnd;
    private BigDecimal dailySalary;
    private Boolean isHourly;
    private BigDecimal overtimeRate;
    
}
