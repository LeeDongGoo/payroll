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

import com.paymaster.payroll.dto.ActualWorkDTO;
import com.paymaster.payroll.model.ActualWork;
import com.paymaster.payroll.service.ActualWorkService;
import com.paymaster.payroll.util.ConvertUtil;

@RestController
@RequestMapping("/actualWork")
public class ActualWorkController {
	/**
	 * 실제 근무한 내역을 등록하고 조회하는 controller
	 * ConvertUtil.convertToPayrollUserDTO
	 * */
	@Autowired
    private ActualWorkService service;
	/* 근무내역 등록 */
	@PostMapping
    public ResponseEntity<ActualWorkDTO> createActualWork(@RequestBody ActualWork actualWork) {
        ActualWork createdActualWork = service.saveActualWork(actualWork);
        return ResponseEntity.ok(ConvertUtil.convertToActualWorkDTO(createdActualWork));
    }
	/* 근무내역 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ActualWorkDTO> getActualWorkById(@PathVariable Long id) {
        Optional<ActualWork> actualWork = service.getActualWorkById(id);
        return actualWork.map(work -> ResponseEntity.ok(ConvertUtil.convertToActualWorkDTO(work))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /* 전체 근무내역 조회 */
    @GetMapping
    public ResponseEntity<List<ActualWorkDTO>> getAllActualWorks() {
        List<ActualWork> actualWorks = service.getAllActualWorks();
        List<ActualWorkDTO> rtnList = actualWorks.stream().map(ConvertUtil::convertToActualWorkDTO).collect(Collectors.toList());
        return ResponseEntity.ok(rtnList);
    }
    /* 근무내역 수정 */
    @PutMapping
    public ResponseEntity<ActualWorkDTO> updateActualWork(@RequestBody ActualWork updatedActualWork) {
        ActualWork actualWork = service.updateActualWork(updatedActualWork);
        return ResponseEntity.ok(ConvertUtil.convertToActualWorkDTO(actualWork));
    }
    /* 근무내역 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActualWork(@PathVariable Long id) {
    	service.deleteActualWork(id);
        return ResponseEntity.noContent().build();
    }
}
