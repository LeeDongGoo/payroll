package com.paymaster.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymaster.payroll.model.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

	List<WorkTime> findByDailySalaryBetween(double minSalary, double maxSalary);
    List<WorkTime> findByIsHourly(boolean isHourly);
	
}
