package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resume implements Serializable{
    private Integer resumeId;
    private String imgPath;
    private String familyAddress;
    private String telephoneNumber;
    private String email;
    private String projectName;
    private Date projectDate1;
    private Date projectDate2;
    private String projectDesc;
    private String preferredFunction;
    private String preferredLocation;
    private String salaryExpectation;
    private String companyName;
    private String jobCategory;
    private Date workDate1;
    private Date workDate2;
    private String salary;
    private String jobDesc;
    private String personalAdvantage;
    private Integer jobSeekerId;





}
