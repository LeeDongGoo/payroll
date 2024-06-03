package com.paymaster.payroll.dto;

import lombok.Data;

@Data
public class PayrollUserDTO {

	private String userId;
	private String userPw;
	private Long workTimeId; // workTime의 ID만 포함

}
