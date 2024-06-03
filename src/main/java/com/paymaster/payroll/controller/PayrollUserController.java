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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymaster.payroll.dto.PayrollUserDTO;
import com.paymaster.payroll.model.PayrollUser;
import com.paymaster.payroll.service.PayrollUserService;
import com.paymaster.payroll.util.ConvertUtil;

@RestController
@RequestMapping("/payrollUser")
public class PayrollUserController {
	/**
	 * 유저를 등록하고 조회하는 controller
	 * */
	@Autowired
    private PayrollUserService service;
	
	/* 유저 생성 */
	@PostMapping
//    public ResponseEntity<PayrollUser> createPayrollUser(@RequestHeader("X-USER-ID") String loginUserId, @RequestBody PayrollUser payrollUser) {
	public ResponseEntity<PayrollUserDTO> createPayrollUser(@RequestBody PayrollUser payrollUser) {
		PayrollUser createdPayrollUser = service.savePayrollUser(payrollUser);
        return ResponseEntity.ok(ConvertUtil.convertToPayrollUserDTO(createdPayrollUser));
    }
	/* 유저 조회 */
    @GetMapping("/{userId}") 
//    public ResponseEntity<PayrollUser> getPayrollUserById(@RequestHeader("X-USER-ID") String loginUserId, @PathVariable String userId) {
    public ResponseEntity<PayrollUserDTO> getPayrollUserById(@PathVariable String userId) {
        Optional<PayrollUser> payrollUser = service.getPayrollUserById(userId);
        return payrollUser.map(user -> ResponseEntity.ok(ConvertUtil.convertToPayrollUserDTO(user))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /* 전체 유저 조회*/
    @GetMapping
//    public ResponseEntity<List<PayrollUser>> getAllPayrollUsers(@RequestHeader("X-USER-ID") String loginUserId) {
    public ResponseEntity<List<PayrollUserDTO>> getAllPayrollUsers() {
        List<PayrollUser> payrollUsers = service.getAllPayrollUsers();
        List<PayrollUserDTO> rtnList = payrollUsers.stream().map(ConvertUtil::convertToPayrollUserDTO).collect(Collectors.toList());
        return ResponseEntity.ok(rtnList);
    }
    /* 유저 정보 update */
    @PutMapping("/{userId}")
//    public ResponseEntity<PayrollUser> updatePayrollUser(@RequestHeader("X-USER-ID") String loginUserId, @PathVariable String userId, @RequestBody PayrollUser updatedPayrollUser) {
    public ResponseEntity<PayrollUserDTO> updatePayrollUser(@PathVariable String userId, @RequestBody PayrollUser updatedPayrollUser) {
        PayrollUser payrollUser = service.updatePayrollUser(userId, updatedPayrollUser);
        return ResponseEntity.ok(ConvertUtil.convertToPayrollUserDTO(payrollUser));
    }
    /* 유저 삭제 */
    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deletePayrollUser(@RequestHeader("X-USER-ID") String loginUserId, @PathVariable String userId) {
    public ResponseEntity<Void> deletePayrollUser(@PathVariable String userId) {
    	service.deletePayrollUser(userId);
        return ResponseEntity.noContent().build();
    }
}
