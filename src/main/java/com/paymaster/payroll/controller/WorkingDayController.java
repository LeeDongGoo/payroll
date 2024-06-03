package com.paymaster.payroll.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymaster.payroll.dto.WorkingDayDTO;
import com.paymaster.payroll.model.WorkingDay;
import com.paymaster.payroll.service.WorkingDayService;
import com.paymaster.payroll.util.ConvertUtil;

@RestController
@RequestMapping("/workingDay")
public class WorkingDayController {
	/**
	 * 근무일과 관련한 내용을 등록하고 조회하는 controller
	 * */
	@Autowired
    private WorkingDayService service;
	/* 근무일 등록 */
    @PostMapping
    public ResponseEntity<WorkingDayDTO> createWorkingDay(@RequestBody WorkingDay workingDay) {
        WorkingDay createdWorkingDay = service.saveWorkingDay(workingDay);
        return ResponseEntity.ok(ConvertUtil.convertToWorkingDayDTO(createdWorkingDay));
    }
    /* 근무일 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<WorkingDayDTO> getWorkingDayById(@PathVariable Long id) {
        Optional<WorkingDay> workingDay = service.getWorkingDayById(id);
        return workingDay.map(work -> ResponseEntity.ok(ConvertUtil.convertToWorkingDayDTO(work))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /* 전체 근무일 조회 */
    @GetMapping
    public ResponseEntity<List<WorkingDayDTO>> getAllWorkingDays() {
        List<WorkingDay> workingDays = service.getAllWorkingDays();
        List<WorkingDayDTO> rtnList = workingDays.stream().map(ConvertUtil::convertToWorkingDayDTO).collect(Collectors.toList());
        return ResponseEntity.ok(rtnList);
    }
    /* 근무일 변경 */
    @PutMapping("/{id}")
    public ResponseEntity<WorkingDayDTO> updateWorkingDay(@PathVariable Long id, @RequestBody WorkingDay updatedWorkingDay) {
        WorkingDay workingDay = service.updateWorkingDay(id, updatedWorkingDay);
        return ResponseEntity.ok(ConvertUtil.convertToWorkingDayDTO(workingDay));
    }
    /* 근무일 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkingDay(@PathVariable Long id) {
    	service.deleteWorkingDay(id);
        return ResponseEntity.noContent().build();
    }
}
