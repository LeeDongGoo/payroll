package com.paymaster.payroll.model;

import lombok.Data;

@Data
public class SearchCriteria {
	private String userId;
    private String searchStartDate;
    private String searchEndDate;
}
