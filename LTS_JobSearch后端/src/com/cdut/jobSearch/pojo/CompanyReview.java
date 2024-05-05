package com.cdut.jobSearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  java.util.Date;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyReview implements Serializable {
    private Integer companyReviewId;
    private String companyReview;
    private Integer employerId;
    private Date companyReviewTime;
    private Integer companyId;
    private  Integer rating;
}