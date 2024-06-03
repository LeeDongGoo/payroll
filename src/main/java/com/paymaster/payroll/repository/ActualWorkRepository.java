package com.paymaster.payroll.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymaster.payroll.model.ActualWork;

public interface ActualWorkRepository extends JpaRepository<ActualWork, Long> {
	//유니크 key 검증을 위한 method
	Optional<ActualWork> findByUserIdAndWorkingDay(String userId, Date workingDay);
}
