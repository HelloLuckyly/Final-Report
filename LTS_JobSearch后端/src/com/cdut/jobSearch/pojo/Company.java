package com.cdut.jobSearch.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company  implements Serializable{
    private Integer companyId;
    private String companyName;
    private String companyDescription;
    private String companyCulture;
    private String companyAddress;
    private String specificAddress;
    private Integer employerId;
    private String jobOpportunities;
}
