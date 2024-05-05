package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationManagement implements Serializable{
    private Integer applicationManagementId;
    private Integer jobSeekerId;
    private Integer employerId;
    private Integer campusRecruitmentId;
    private String status;
}
