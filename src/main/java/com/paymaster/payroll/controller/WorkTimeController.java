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

import com.paymaster.payroll.dto.WorkTimeDTO;
import com.paymaster.payroll.model.WorkTime;
import com.paymaster.payroll.service.WorkTimeService;
import com.paymaster.payroll.util.ConvertUtil;

@RestController
@RequestMapping("/workTime")
public class WorkTimeController {
	/**
	 * 근무정보와 관련한 내용을 등록하고 조회하는 controller
	 * */
	@Autowired
    private WorkTimeService service;
	/* 근무정보 생성 */
	@PostMapping
    public ResponseEntity<WorkTimeDTO> createWorkTime(@RequestBody WorkTime workTime) {
        WorkTime createdWorkTime = service.saveWorkTime(workTime);
        return ResponseEntity.ok(ConvertUtil.convertToWorkTimeDTO(createdWorkTime));
    }
	/* 근무정보 가져오기 */
    @GetMapping("/{id}")
    public ResponseEntity<WorkTimeDTO> getWorkTimeById(@PathVariable Long id) {
        Optional<WorkTime> workTime = service.getWorkTimeById(id);
        return workTime.map(work -> ResponseEntity.ok(ConvertUtil.convertToWorkTimeDTO(work))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /* 전체 근무정보 가져오기 */
    @GetMapping
    public ResponseEntity<List<WorkTimeDTO>> getAllWorkTimes() {
        List<WorkTime> workTimes = service.getAllWorkTimes();
        List<WorkTimeDTO> rtnList = workTimes.stream().map(ConvertUtil::convertToWorkTimeDTO).collect(Collectors.toList());
        return ResponseEntity.ok(rtnList);
    }
    /* 근무정보 변경하기 */
    @PutMapping("/{id}")
    public ResponseEntity<WorkTimeDTO> updateWorkTime(@PathVariable Long id, @RequestBody WorkTime updatedWorkTime) {
        WorkTime workTime = service.updateWorkTime(id, updatedWorkTime);
        return ResponseEntity.ok(ConvertUtil.convertToWorkTimeDTO(workTime));
    }
    /* 근무정보 삭제하기 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkTime(@PathVariable Long id) {
    	service.deleteWorkTime(id);
        return ResponseEntity.noContent().build();
    }

}
