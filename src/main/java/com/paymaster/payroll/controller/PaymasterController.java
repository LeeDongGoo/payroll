package com.paymaster.payroll.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymaster.payroll.dto.WorkDetailsDTO;
import com.paymaster.payroll.model.SearchCriteria;
import com.paymaster.payroll.service.PaymasterService;
import com.paymaster.payroll.util.StringUtil;

@RestController
@RequestMapping("/paymaster")
public class PaymasterController {

	@Autowired
	private PaymasterService service;
	
	@PostMapping("/workDetails")
	public ResponseEntity<List<WorkDetailsDTO>> getWorkDetails(@RequestBody SearchCriteria searchWorkDetail){
		List<WorkDetailsDTO> workDetails = service.getActualWorkDetails(searchWorkDetail);
		return ResponseEntity.ok(workDetails);
	}
	
	@GetMapping("/getTotalPay")
	public Double getTotalPay(@RequestBody SearchCriteria searchWorkDetail) {
		Double rtnDouble = 0.0;
		
		List<WorkDetailsDTO> workDetails = service.getActualWorkDetails(searchWorkDetail);
		
		for(WorkDetailsDTO dto : workDetails) {
			rtnDouble += (double)dto.getRealPay();
		}
		
		return rtnDouble;
	}
	
	@PostMapping("/workDetails/excel")
	public ResponseEntity<byte[]> downloadWorkDetailsExcel(@RequestBody SearchCriteria criteria) throws IOException {
		String filePath = "/Users/dgmen/Desktop/My_Projects/file/";
		String fileName = criteria.getUserId()+"_work_details_"+StringUtil.getCurrentTimeStamp()+".xlsx";
		String totalFilePath = filePath + fileName;
		ByteArrayInputStream in = service.workDetailsToExcel(criteria, totalFilePath);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		int nRead;
		byte[] data = new byte[1024];
		while((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
		
		buffer.flush();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename="+fileName);
		
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		
		return ResponseEntity.ok().headers(headers).body(buffer.toByteArray());
	}
}
