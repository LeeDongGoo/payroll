package com.paymaster.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymaster.payroll.model.PayrollUser;

public interface PayrollUserRepository extends JpaRepository<PayrollUser, String> {

}
