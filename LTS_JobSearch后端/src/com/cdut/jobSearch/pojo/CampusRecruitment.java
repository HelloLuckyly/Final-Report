package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampusRecruitment  implements Serializable{
        private Integer campusRecruitmentId;
        private String workingTitle;
        private String salary;
        private String position;
        private String experience;
        private String education;
        private Integer count;
        private String workCity;
        private String addressDetails;
        private String workContent;
        private String jobRequirements;
        private Integer employerId;
        private Integer defaultList;
        private String industry;
        private String postTime;

}
