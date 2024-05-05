package com.cdut.jobSearch.service;

import com.cdut.jobSearch.pojo.*;

import java.util.List;

public interface EmployerService {
    int regist(Employer employer);


    Employer findByUsername(String username);
    int postCampusRecruitment(CampusRecruitment campusRecruitment);
    List<CampusRecruitment> findAllPostCampusRecruitment(Integer employerId);
    int saveCompanyInfo(Company company);
    int updateCompanyInfo(Company company);
    Company findCompanyByEmployerId(Integer employerId);
    CompanyReview findCompanyReviewByEmployerId(Integer employerId);
    int saveCompanyReview(CompanyReview companyReview);
    int updateCompanyReview(CompanyReview companyReview);
    List<Company> findAllCompanyReview(String companyName);
    Employer findNameByEmployerId(Integer employerId);
    List<ApplicationManagement> getAllApplication(Integer employerId);
    List<Profiles> getProfileByWorkExperience(String workExperience);

    List<Profiles> getProfileByEducation(String education);


    List<Profiles> getProfileByAge(Integer age);
    List<Profiles> getProfileByPosition(String expectedPosition);
    Resume checkResume(Integer jobSeekerId);
    int arrangingInterviews(ApplicationManagement applicationManagement);
    ApplicationManagement getStatus(ApplicationManagement applicationManagement);
    InterviewDetails findInterviewDetails(Integer applicationManagementId);
    int saveInterviewDetails(InterviewDetails interviewDetails);
    int updateInterviewDetails(InterviewDetails interviewDetails);
}
