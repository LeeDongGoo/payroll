package com.paymaster.payroll.dto;

import lombok.Data;

@Data
public class BreakTimeDTO {

	private Long id;
    private Long workTimeId;
    private String breakType;
    private String breakStart;
    private String breakEnd;
    
}
