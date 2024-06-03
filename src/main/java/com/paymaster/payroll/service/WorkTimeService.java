package com.paymaster.payroll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymaster.payroll.model.WorkTime;
import com.paymaster.payroll.repository.WorkTimeRepository;

@Service
public class WorkTimeService {
	@Autowired
    private WorkTimeRepository workTimeRepository;

    public WorkTime saveWorkTime(WorkTime workTime) {
        return workTimeRepository.save(workTime);
    }

    public Optional<WorkTime> getWorkTimeById(Long id) {
        return workTimeRepository.findById(id);
    }
    
    public List<WorkTime> getAllWorkTimes() {
        return workTimeRepository.findAll();
    }
    
    public WorkTime updateWorkTime(Long id, WorkTime updatedWorkTime) {
        Optional<WorkTime> existingWorkTimeOpt = workTimeRepository.findById(id);
        if (existingWorkTimeOpt.isPresent()) {
            WorkTime existingWorkTime = existingWorkTimeOpt.get();
            existingWorkTime.setStandardWorkStart(updatedWorkTime.getStandardWorkStart());
            existingWorkTime.setStandardWorkEnd(updatedWorkTime.getStandardWorkEnd());
            existingWorkTime.setDailySalary(updatedWorkTime.getDailySalary());
            existingWorkTime.setIsHourly(updatedWorkTime.getIsHourly());
            existingWorkTime.setOvertimeRate(updatedWorkTime.getOvertimeRate());
            existingWorkTime.setModifierId(updatedWorkTime.getModifierId());
            existingWorkTime.setUpdDt(updatedWorkTime.getUpdDt());
            return workTimeRepository.save(existingWorkTime);
        } else {
            throw new RuntimeException("WorkTime not found with id: " + id);
        }
    }
    
    public void deleteWorkTime(Long id) {
        workTimeRepository.deleteById(id);
    }
    
    //특정 급여 범위 내의 객체 조회 
    public List<WorkTime> findWorkTimesBySalaryRange(double minSalary, double maxSalary) {
        return workTimeRepository.findByDailySalaryBetween(minSalary, maxSalary);
    }
    
    //특정 근무 유형(시급제 또는 일급제)에 해당하는 객체조회 
    public List<WorkTime> findWorkTimesByIsHourly(boolean isHourly) {
        return workTimeRepository.findByIsHourly(isHourly);
    }
}
