package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Profiles implements Serializable {
    private Integer profileId;
    private String workExperience;
    private String education;
    private String skills;
    private  Integer age;
    private Integer jobSeekerId;
    private String gender;
    private String hobby;
    private String realName;
    private String expectedSalary;
    private String expectedPosition;

}