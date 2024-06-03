package com.paymaster.payroll.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
public class WorkingDay {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(unique = true)
    private Date wDay;

    private Boolean isHoly;

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
