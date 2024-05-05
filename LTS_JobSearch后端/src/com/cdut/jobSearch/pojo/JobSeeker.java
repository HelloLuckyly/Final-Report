package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobSeeker implements Serializable {
    private Integer jobSeekerId;
    private String username;
    private String password;
    private Integer campusRecruitmentId;
    private Integer defaultList;
//    private Integer profileId;
}
