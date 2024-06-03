package com.paymaster.payroll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymaster.payroll.model.BreakTime;
import com.paymaster.payroll.repository.BreakTimeRepository;

@Service
public class BreakTimeService {

    @Autowired
    private BreakTimeRepository repository;

    public BreakTime saveBreakTime(BreakTime breakTime) {
        return repository.save(breakTime);
    }

    public Optional<BreakTime> getBreakTimeById(Long id) {
        return repository.findById(id);
    }

    public List<BreakTime> getAllBreakTimes() {
        return repository.findAll();
    }

    public void deleteBreakTime(Long id) {
    	repository.deleteById(id);
    }
    
    public BreakTime updateBreakTime(Long id, BreakTime updatedBreakTime) {
        Optional<BreakTime> existingBreakTimeOpt = repository.findById(id);
        if (existingBreakTimeOpt.isPresent()) {
            BreakTime existingBreakTime = existingBreakTimeOpt.get();
            existingBreakTime.setWorkTime(updatedBreakTime.getWorkTime());
            existingBreakTime.setBreakType(updatedBreakTime.getBreakType());
            existingBreakTime.setBreakStart(updatedBreakTime.getBreakStart());
            existingBreakTime.setBreakEnd(updatedBreakTime.getBreakEnd());
            existingBreakTime.setModifierId(updatedBreakTime.getModifierId());
            existingBreakTime.setUpdDt(updatedBreakTime.getUpdDt());
            return repository.save(existingBreakTime);
        } else {
            throw new RuntimeException("BreakTime not found with id: " + id);
        }
    }

}
