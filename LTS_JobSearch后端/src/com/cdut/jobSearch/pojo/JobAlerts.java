package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobAlerts implements Serializable {
    private Integer jobAlertsId;
    private Integer jobSeekerId;
    private Integer mode;
    private String startingTime;
}
