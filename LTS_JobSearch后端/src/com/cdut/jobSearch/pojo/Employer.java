package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employer implements Serializable {
    private Integer employerId;
    private String username;
    private String password;
    private Integer defaultList;
}
