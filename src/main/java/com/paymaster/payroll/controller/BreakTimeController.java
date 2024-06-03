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

import com.paymaster.payroll.dto.BreakTimeDTO;
import com.paymaster.payroll.model.BreakTime;
import com.paymaster.payroll.service.BreakTimeService;
import com.paymaster.payroll.util.ConvertUtil;

@RestController
@RequestMapping("/breakTime")
public class BreakTimeController {
	/**
	 * 휴게시간과 관련한 내용을 설정하고 조회하는 controller
	 * */
	@Autowired
    private BreakTimeService service;
	/* 휴게시간 등록 */
    @PostMapping
    public ResponseEntity<BreakTimeDTO> createBreakTime(@RequestBody BreakTime breakTime) {
        BreakTime createdBreakTime = service.saveBreakTime(breakTime);
        return ResponseEntity.ok(ConvertUtil.convertToBreakTimeDTO(createdBreakTime));
    }
    /* 휴게시간 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<BreakTimeDTO> getBreakTimeById(@PathVariable Long id) {
        Optional<BreakTime> breakTime = service.getBreakTimeById(id);
        return breakTime.map(work -> ResponseEntity.ok(ConvertUtil.convertToBreakTimeDTO(work))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /* 전체 휴게시간 조회 */
    @GetMapping
    public ResponseEntity<List<BreakTimeDTO>> getAllBreakTimes() {
        List<BreakTime> breakTimes = service.getAllBreakTimes();
        List<BreakTimeDTO> rtnList = breakTimes.stream().map(ConvertUtil::convertToBreakTimeDTO).collect(Collectors.toList());
        return ResponseEntity.ok(rtnList);
    }
    /* 휴게시간 변경 */
    @PutMapping("/{id}")
    public ResponseEntity<BreakTimeDTO> updateBreakTime(@PathVariable Long id, @RequestBody BreakTime updatedBreakTime) {
        BreakTime breakTime = service.updateBreakTime(id, updatedBreakTime);
        return ResponseEntity.ok(ConvertUtil.convertToBreakTimeDTO(breakTime));
    }
    /* 휴게시간 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreakTime(@PathVariable Long id) {
    	service.deleteBreakTime(id);
        return ResponseEntity.noContent().build();
    }
}
