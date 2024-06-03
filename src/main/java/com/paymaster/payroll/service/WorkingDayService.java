package com.paymaster.payroll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymaster.payroll.model.WorkingDay;
import com.paymaster.payroll.repository.WorkingDayRepository;

@Service
public class WorkingDayService {
	
	@Autowired
	private WorkingDayRepository repository;
	
	public WorkingDay saveWorkingDay(WorkingDay workingDay) {
        return repository.save(workingDay);
    }

    public Optional<WorkingDay> getWorkingDayById(Long id) {
        return repository.findById(id);
    }

    public List<WorkingDay> getAllWorkingDays() {
        return repository.findAll();
    }

    public void deleteWorkingDay(Long id) {
    	repository.deleteById(id);
    }
    
    public WorkingDay updateWorkingDay(Long id, WorkingDay updatedWorkingDay) {
        Optional<WorkingDay> existingWorkingDayOpt = repository.findById(id);
        if (existingWorkingDayOpt.isPresent()) {
            WorkingDay existingWorkingDay = existingWorkingDayOpt.get();
            existingWorkingDay.setWDay(updatedWorkingDay.getWDay());
            existingWorkingDay.setIsHoly(updatedWorkingDay.getIsHoly());
            existingWorkingDay.setModifierId(updatedWorkingDay.getModifierId());
            existingWorkingDay.setUpdDt(updatedWorkingDay.getUpdDt());
            return repository.save(existingWorkingDay);
        } else {
            throw new RuntimeException("WorkingDay not found with id: " + id);
        }
    }


}
