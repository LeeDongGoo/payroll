package com.paymaster.payroll.dto;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class WorkDetailsDTO {
	private String userId;
	private Date workingDay;
    private Boolean isHoly;
    private Timestamp workStart;
    private Timestamp workEnd;
    private Boolean isHourly;
    private Double dailySalary;
    private Time standardWorkStart;
    private Time standardWorkEnd;
    private String breakType;
    private Time breakStart;
    private Time breakEnd;
    private Double overtimeRate;
    
    private Double breakDuration;
    private Double workDuration;
    private Double actualWorkDuration;
    private Double overDuration;
    private Double realPay;

}
