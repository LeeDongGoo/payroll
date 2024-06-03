package com.paymaster.payroll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymaster.payroll.model.PayrollUser;
import com.paymaster.payroll.repository.PayrollUserRepository;

@Service
public class PayrollUserService {

	@Autowired
    private PayrollUserRepository repository;
	
	public PayrollUser savePayrollUser(PayrollUser payrollUser) {
		return repository.save(payrollUser);
	}
	
	public Optional<PayrollUser> getPayrollUserById(String userId) {
        return repository.findById(userId);
    }

    public List<PayrollUser> getAllPayrollUsers() {
        return repository.findAll();
    }

    public void deletePayrollUser(String userId) {
    	repository.deleteById(userId);
    }
    
    public PayrollUser updatePayrollUser(String userId, PayrollUser updatedPayrollUser) {
        Optional<PayrollUser> existingUserOpt = repository.findById(userId);
        if (existingUserOpt.isPresent()) {
            PayrollUser existingUser = existingUserOpt.get();
            existingUser.setUserPw(updatedPayrollUser.getUserPw());
            existingUser.setWorkTime(updatedPayrollUser.getWorkTime());
            existingUser.setModifierId(updatedPayrollUser.getModifierId());
            existingUser.setUpdDt(updatedPayrollUser.getUpdDt());
            return repository.save(existingUser);
        } else {
            throw new RuntimeException("PayrollUser not found with id: " + userId);
        }
    }
}
