package com.paymaster.payroll.model;

import javax.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Time standardWorkStart;
    private Time standardWorkEnd;
    private BigDecimal dailySalary;
    private Boolean isHourly;
    private BigDecimal overtimeRate;

    private String creatorId;
    private String modifierId;

    @Column(updatable = false)
    private Timestamp regDt;
    private Timestamp updDt;

    @OneToMany(mappedBy = "workTime")
    private List<PayrollUser> payrollUsers;

    @PrePersist
    protected void onCreate() {
    	if (creatorId == null) creatorId = "paymaster_admin";
        if (modifierId == null) modifierId = "paymaster_admin";
        regDt = new Timestamp(System.currentTimeMillis());
        updDt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        if (modifierId == null) modifierId = "paymaster_admin";
        updDt = new Timestamp(System.currentTimeMillis());
    }
}