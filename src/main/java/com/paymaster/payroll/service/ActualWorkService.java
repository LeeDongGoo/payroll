package com.paymaster.payroll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymaster.payroll.model.ActualWork;
import com.paymaster.payroll.repository.ActualWorkRepository;

@Service
public class ActualWorkService {
	@Autowired
	private ActualWorkRepository repository;

	
	public ActualWork saveActualWork(ActualWork actualWork) {
		Optional<ActualWork> existingWork = repository.findByUserIdAndWorkingDay(actualWork.getUserId(), actualWork.getWorkingDay());
		if(existingWork.isPresent()) {
			throw new IllegalArgumentException("This user already has work recorded for this day.");
		}
		return repository.save(actualWork);
    }

    public Optional<ActualWork> getActualWorkById(Long id) {
        return repository.findById(id);
    }

    public List<ActualWork> getAllActualWorks() {
        return repository.findAll();
    }

    public void deleteActualWork(Long id) {
    	repository.deleteById(id);
    }
    
    public ActualWork updateActualWork(ActualWork updatedActualWork) {
        Optional<ActualWork> existingActualWorkOpt = repository.findByUserIdAndWorkingDay(updatedActualWork.getUserId(), updatedActualWork.getWorkingDay());
        if (existingActualWorkOpt.isPresent()) {
            ActualWork existingActualWork = existingActualWorkOpt.get();
            existingActualWork.setWorkStart(updatedActualWork.getWorkStart());
            existingActualWork.setWorkEnd(updatedActualWork.getWorkEnd());
            existingActualWork.setModifierId(updatedActualWork.getModifierId());
            return repository.save(existingActualWork);
        } else {
            throw new RuntimeException("ActualWork not found with id: ");
        }
    }

}
