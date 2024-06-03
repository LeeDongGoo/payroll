package com.paymaster.payroll.util;

import com.paymaster.payroll.dto.ActualWorkDTO;
import com.paymaster.payroll.dto.BreakTimeDTO;
import com.paymaster.payroll.dto.PayrollUserDTO;
import com.paymaster.payroll.dto.WorkTimeDTO;
import com.paymaster.payroll.dto.WorkingDayDTO;
import com.paymaster.payroll.model.ActualWork;
import com.paymaster.payroll.model.BreakTime;
import com.paymaster.payroll.model.PayrollUser;
import com.paymaster.payroll.model.WorkTime;
import com.paymaster.payroll.model.WorkingDay;

/* 형변환을 처리하는 Util Class */
public class ConvertUtil {
	public static PayrollUserDTO convertToPayrollUserDTO(PayrollUser payrollUser) {
        PayrollUserDTO dto = new PayrollUserDTO();
        dto.setUserId(payrollUser.getUserId());
        dto.setUserPw(payrollUser.getUserPw());
        dto.setWorkTimeId(payrollUser.getWorkTime().getId());
        return dto;
    }

    public static WorkTimeDTO convertToWorkTimeDTO(WorkTime workTime) {
        WorkTimeDTO dto = new WorkTimeDTO();
        dto.setId(workTime.getId());
        dto.setStandardWorkStart(workTime.getStandardWorkStart().toString());
        dto.setStandardWorkEnd(workTime.getStandardWorkEnd().toString());
        dto.setDailySalary(workTime.getDailySalary());
        dto.setIsHourly(workTime.getIsHourly());
        dto.setOvertimeRate(workTime.getOvertimeRate());
        return dto;
    }

    public static ActualWorkDTO convertToActualWorkDTO(ActualWork actualWork) {
        ActualWorkDTO dto = new ActualWorkDTO();
        dto.setId(actualWork.getId());
        dto.setUserId(actualWork.getUserId());
        dto.setWorkStart(actualWork.getWorkStart().toString());
        dto.setWorkEnd(actualWork.getWorkEnd().toString());
        dto.setWorkingDay(actualWork.getWorkingDay().toString());
        return dto;
    }

    public static WorkingDayDTO convertToWorkingDayDTO(WorkingDay workingDay) {
        WorkingDayDTO dto = new WorkingDayDTO();
        dto.setId(workingDay.getId());
        dto.setWDay(workingDay.getWDay().toString());
        dto.setIsHoly(workingDay.getIsHoly());
        return dto;
    }

    public static BreakTimeDTO convertToBreakTimeDTO(BreakTime breakTime) {
        BreakTimeDTO dto = new BreakTimeDTO();
        dto.setId(breakTime.getId());
        dto.setWorkTimeId(breakTime.getWorkTime().getId());
        dto.setBreakType(breakTime.getBreakType());
        dto.setBreakStart(breakTime.getBreakStart().toString());
        dto.setBreakEnd(breakTime.getBreakEnd().toString());
        return dto;
    }
}
