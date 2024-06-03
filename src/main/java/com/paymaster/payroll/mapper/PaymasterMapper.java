package com.paymaster.payroll.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.paymaster.payroll.dto.WorkDetailsDTO;
import com.paymaster.payroll.model.SearchCriteria;

@Mapper
public interface PaymasterMapper {
	List<WorkDetailsDTO> getActualWorkDetails(@Param("criteria") SearchCriteria criteria);
}
