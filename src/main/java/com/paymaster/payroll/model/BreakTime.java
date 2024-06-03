package com.paymaster.payroll.model;

import javax.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
public class BreakTime {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_time_id")
    private WorkTime workTime;

    private String breakType;
    private Time breakStart;
    private Time breakEnd;

    private String creatorId;
    private String modifierId;

    @Column(updatable = false)
    private Timestamp regDt;
    private Timestamp updDt;

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
