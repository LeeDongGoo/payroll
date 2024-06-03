# Payroll REST API

## 프로젝트 개요

Payroll REST API는 아르바이트 생들의 근무 시간, 급여 정보를 관리하고 계산하는 시스템입니다. 이 프로젝트는 Spring Boot와 MyBatis를 사용하여 개발되었습니다.

## 주요 기능

- 직원 정보 관리
- 근무 시간 관리
- 휴게 시간 관리
- 근무 일지 관리
- 급여 계산
- 엑셀 파일로 데이터 내보내기

## 기술 스택

- Java 8
- Spring Boot
- MyBatis
- MySQL
- Apache POI
- Lombok

### 프로젝트 클론

```bash
git clone https://github.com/LeeDongGoo/payroll.git
cd payroll
```

## API 사용법
1. 직원 정보 관리 
- 직원 생성 (URL: /payrollUser, 메서드: POST)
```json
{
  "userId": "user123",
  "userPw": "password",
  "workTimeId": 1,
  "creatorId": "paymaster_admin",
  "modifierId": "paymaster_admin"
}
```
- 직원 정보 수정 (URL: /payrollUser/{userId}, 메서드: PUT)
```json
{
  "userPw": "newpassword",
  "workTimeId": 1,
  "modifierId": "paymaster_admin"
}
```
- 직원 삭제 (URL: /payrollUser/{userId}, 메서드: DELETE)
- 전체 직원 조회 (URL: /payrollUser, 메서드: GET)
- 특정 직원 조회 (URL: /payrollUser/{userId}, 메서드: GET)

2.근무 일지 관리
- 근무 일지 생성 (URL: /actualWork, 메서드: POST)
```json
{
  "userId": "user123",
  "workStart": "09:00:00",
  "workEnd": "18:00:00",
  "wDay": "2024-05-13",
  "creatorId": "paymaster_admin",
  "modifierId": "paymaster_admin"
}
```
- 근무 일지 수정 (URL: /actualWork, 메서드: PUT)
```json
{
  "workStart": "09:00:00",
  "workEnd": "18:00:00",
  "modifierId": "paymaster_admin"
}
```
- 근무 일지 삭제 (URL: /actualWork/{id}, 메서드: DELETE)

3.근무 일지와 일당 확인
- 근무 일지 및 일당 확인 (URL: /paymaster/workDetails, 메서드: POST)
```json
{
  "userId": "userId1",
  "searchStartDate": "2024-05-01",
  "searchEndDate":"2024-05-31"
}
```

- 근무 일지 및 일당 엑셀 다운로드 (URL: /paymaster/workDetails/excel, 메서드: POST)
```json
{
  "userId": "userId1",
  "searchStartDate": "2024-05-01",
  "searchEndDate":"2024-05-31"
}
```
