package com.paymaster.payroll.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {

  public static String nvl(Object str, String rep) {
    return nvl(str.toString());
  }

  public static String nvl(Object str) {
    return nvl(str.toString(), "");
  }

  public static String nvl(String str) {
    return nvl(str, "");
  }

  public static String nvl(String str, String rep) {
    return str == null || "".equals(str) || str.hashCode() == 0 ? rep : str;
  }

  public static boolean isEmpty(String str) {
    if (nvl(str).trim().equals("")) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isEmpty(Object str) {
    if (nvl(str).trim().equals("")) {
      return true;
    } else {
      return false;
    }
  }
  
  public static String formatCurrency(double amount) {
      DecimalFormat formatter = new DecimalFormat("#,###");
      return formatter.format(amount);
  }
  
  public static String getFormattedTime(String str, String inputFormat, String outputFormat) {
      // 입력 문자열을 ZonedDateTime 객체로 변환
      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
      ZonedDateTime dateTime = ZonedDateTime.parse(str, inputFormatter);
      
      // ZonedDateTime 객체를 원하는 시간 포맷의 문자열로 변환
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
      return dateTime.format(outputFormatter);
  }
  
  public static String getFormattedTime(String str, String format) {
      // 입력 문자열을 ZonedDateTime 객체로 변환
      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
      ZonedDateTime dateTime = ZonedDateTime.parse(str, inputFormatter);
      
      // ZonedDateTime 객체를 원하는 시간 포맷의 문자열로 변환
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(format);
      return dateTime.format(outputFormatter);
  }
  
  public static String getFormatDate(String str, String format) {
	  SimpleDateFormat sdfDate = new SimpleDateFormat(format);
	  Date date = new Date();
	  
	  try {
		  date = sdfDate.parse(str);
	  }catch (ParseException e) {
		  log.error("[StringUtil - getFormatDate] Parse Date Exception", e, e.toString());
	  }
	  return sdfDate.format(date);
  }
  
  public static String getFormatDate(String str, String inputFormat, String outputFormat) {
	  SimpleDateFormat sdfDate = new SimpleDateFormat(inputFormat);
      Date date = new Date();
      SimpleDateFormat sdfOutput = null;
	  try {
		  date = sdfDate.parse(str);
		  sdfOutput = new SimpleDateFormat(outputFormat);
		  
	  }catch (ParseException e) {
		  log.error("[StringUtil - getFormatDate] Parse Date Exception", e, e.toString());
	  }
	  return sdfOutput.format(date);
  }

  public static String getCurrentTime(String timeFormat) {
    return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
  }

  public static String getCurrentTimeStamp() {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    Date now = new Date();
    String strDate = sdfDate.format(now);
    return strDate;
  }

  public static String existYn(String str) {
    return str == null || str == "" || str.length() == 0 ? "N" : "Y";
  }

  public static String getJsonInString(Object obj) {
    String rtn = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      rtn = mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      rtn = obj.toString();
    }
    return rtn;
  }

  public static Map<String, String> makeHealthInfoMap(List<String> ipList, List<String> statusList,
      String hostIp, String keyName) {
    Map<String, String> rmap = new HashMap<>();
    try {
      // 디폴트 값
      rmap.put("proc_name", keyName);
      rmap.put("status", "0");

      if (ipList != null && ipList.size() > 0) {
        for (int i = 0; i < ipList.size(); i++) {
          if (ipList.get(i).contains(hostIp)) {
            if (statusList.get(i).equals("connected")) {
              rmap.put("status", "1");
            } else {
              rmap.put("status", "0");
            }
          }
        }
      } else {
        rmap.put("status", "0");
      }
    } catch (Exception e) {
      rmap.put("status", "0");
    }
    return rmap;
  }
}
