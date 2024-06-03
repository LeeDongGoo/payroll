package com.paymaster.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymaster.payroll.model.WorkingDay;

public interface WorkingDayRepository extends JpaRepository<WorkingDay, Long> {

}
