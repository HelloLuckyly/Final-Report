package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterviewDetails implements Serializable{
    private Integer interviewDetailsId;
    private String location;
    private String time;
    private String tips;
    private Integer applicationManagementId;
}
