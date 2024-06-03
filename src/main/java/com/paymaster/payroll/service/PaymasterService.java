package com.paymaster.payroll.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymaster.payroll.dto.WorkDetailsDTO;
import com.paymaster.payroll.mapper.PaymasterMapper;
import com.paymaster.payroll.model.SearchCriteria;
import com.paymaster.payroll.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymasterService {

	@Autowired
	private PaymasterMapper mapper;
	
	public List<WorkDetailsDTO> getActualWorkDetails(SearchCriteria searchCirteria){
		List<WorkDetailsDTO> dtoList = mapper.getActualWorkDetails(searchCirteria);
		List<WorkDetailsDTO> rtnList = new ArrayList<WorkDetailsDTO>();
		
		for(WorkDetailsDTO dto : dtoList) {
			boolean isHoly = dto.getIsHoly();
			long hour = 3600000;

			double breakDuration = (dto.getBreakEnd().getTime() - dto.getBreakStart().getTime())/hour; //휴게시간 
			double workDuration = ((double)((dto.getStandardWorkEnd().getTime() - dto.getStandardWorkStart().getTime()))/hour) - breakDuration ; //표준근무시간
			double actualWorkDuration = ((double)(dto.getWorkEnd().getTime() - dto.getWorkStart().getTime())/hour) - breakDuration; //실제근무시간
			double overDuration = isHoly ? actualWorkDuration : (actualWorkDuration - workDuration); //초과근무시간
			
			overDuration = (long)overDuration;
			
			double regulerPay = dto.getIsHourly() == true ? dto.getDailySalary() : (dto.getDailySalary() / Math.round((double)workDuration));
			
			double realPay = 0.0;
			if(!dto.getIsHoly()) {
				realPay = dto.getIsHourly() == true ? dto.getDailySalary() * workDuration : dto.getDailySalary();
			}
						
			//한시간 보다 많거나, 휴일인 경우.
			if(overDuration >= 1 || dto.getIsHoly()) {
				regulerPay = (regulerPay * dto.getOvertimeRate());
				realPay += overDuration * regulerPay;
			}
			
			dto.setBreakDuration(breakDuration);
			dto.setWorkDuration(workDuration);
			dto.setActualWorkDuration(actualWorkDuration);
			dto.setOverDuration(overDuration);
			dto.setRealPay(realPay);
			
			rtnList.add(dto);
			
		}
		
		return rtnList;
		
	}
	
	public ByteArrayInputStream workDetailsToExcel(SearchCriteria searchCirteria, String filePath) {
		String[] columns = {"근무일", "휴일여부", "근무시작시간", "근무종료시간", "총근무시간", "기준근무시간", "초과근무시간", "일급여"};
		List<WorkDetailsDTO> dtoList = getActualWorkDetails(searchCirteria);
		
		ByteArrayInputStream rtnExcel = null;
		double totalSalary = 0.0;
		
		try {
			
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileOutputStream fileOut = new FileOutputStream(filePath);
			
			Sheet sheet = workbook.createSheet("WorkDetails");
			
            Row headerRow = sheet.createRow(0); // Header
			
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
            }
            
            int rowIdx = 1;
            
            for (WorkDetailsDTO workDetail : dtoList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(StringUtil.getFormatDate(workDetail.getWorkingDay().toString(), "yyyy-MM-dd")); //근무일
                row.createCell(1).setCellValue(workDetail.getIsHoly() ? "Y" : "N"); //휴일여부
                row.createCell(2).setCellValue(StringUtil.getFormatDate(workDetail.getWorkStart().toString(), "yyyy-MM-dd HH:mm:ss.S", "HH:mm:ss")); //근무시작시간
                row.createCell(3).setCellValue(StringUtil.getFormatDate(workDetail.getWorkEnd().toString(), "yyyy-MM-dd HH:mm:ss.S", "HH:mm:ss")); //근무종료시간
                row.createCell(4).setCellValue(workDetail.getActualWorkDuration()); //총근무시간
                row.createCell(5).setCellValue(workDetail.getIsHoly() ? 0 : workDetail.getWorkDuration()); //기준근무시간
                row.createCell(6).setCellValue(workDetail.getOverDuration()); //초과근무시간
                row.createCell(7).setCellValue(StringUtil.formatCurrency(workDetail.getRealPay())); //일일급여
                
                totalSalary += workDetail.getRealPay();
            }
            
            Row totalRow = sheet.createRow(rowIdx);
            Cell totalCellLabel = totalRow.createCell(6);
            totalCellLabel.setCellValue("Total");
            Cell totalCellValue = totalRow.createCell(7);
            totalCellValue.setCellValue(StringUtil.formatCurrency(totalSalary));

            workbook.write(out);
            workbook.write(fileOut);
            rtnExcel = new ByteArrayInputStream(out.toByteArray());
            
		}catch(IOException e) {
			log.error("[PaymasterService - workDetailsToExcel] Json convert to Excel error : " , e, e.toString() );
		}
		
		return rtnExcel;
	}
}
