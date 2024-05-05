package com.cdut.jobSearch.dao.impl;

import com.cdut.jobSearch.dao.BaseDao;
import com.cdut.jobSearch.dao.EmployerDao;
import com.cdut.jobSearch.pojo.*;
import lombok.Data;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EmployerDaoImpl extends BaseDao implements EmployerDao {

    @Override
    public int addEmployer(Employer employer) {

        String sql = "insert into employers values(DEFAULT,?,?,?)";
        return baseUpdate(sql, employer.getUsername(), employer.getPassword(),employer.getDefaultList());
    }

    @Override
    public Employer login(String username) {
        String sql = "SELECT employerId,username,password,defaultList from employers where username = ?";
        List<Employer> employersList = baseQuery(Employer.class, sql, username);
        return employersList != null && employersList.size() > 0 ? employersList.get(0) : null;
    }

    @Override
    public int postCampusRecruitment(CampusRecruitment campusRecruitment) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse(campusRecruitment.getPostTime(), formatter);
        String sql = "insert into campus_recruitment values(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return baseUpdate(sql, campusRecruitment.getWorkingTitle(),campusRecruitment.getSalary(),campusRecruitment.getPosition(),campusRecruitment.getExperience(),campusRecruitment.getEducation(),campusRecruitment.getCount(),campusRecruitment.getWorkCity(),campusRecruitment.getAddressDetails(),campusRecruitment.getWorkContent(),campusRecruitment.getJobRequirements(),campusRecruitment.getEmployerId(),campusRecruitment.getDefaultList(),campusRecruitment.getIndustry(),campusRecruitment.getPostTime());
    }

    @Override
    public List<CampusRecruitment> findAllPostCampusRecruitment(Integer employerId) {
        String sql = "select campusRecruitmentId, workingTitle,salary,position,experience,education,count,workCity,addressDetails,workContent,jobRequirements,employerId,defaultList,industry,postTime from campus_recruitment where employerId = ? ";
        List<CampusRecruitment> campusRecruitmentList = baseQuery(CampusRecruitment.class, sql, employerId);
        return campusRecruitmentList;
    }

    @Override
    public int saveCompanyInfo(Company company) {
     String sql = "insert into companies values(DEFAULT,?,?,?,?,?,?,?)";
        return baseUpdate(sql, company.getCompanyName(), company.getCompanyDescription(),company.getCompanyCulture(),company.getCompanyAddress(),company.getSpecificAddress(),company.getEmployerId(),company.getJobOpportunities());
    }

    @Override
    public int updateCompanyInfo(Company company) {
        String sql = "update  companies set companyName=?,companyDescription=?,companyCulture=?,companyAddress=?,specificAddress=?,employerId=?,jobOpportunities=?  where employerId = ?";
        return baseUpdate(sql, company.getCompanyName(), company.getCompanyDescription(),company.getCompanyCulture(),company.getCompanyAddress(),company.getSpecificAddress(),company.getEmployerId(),company.getJobOpportunities(),company.getEmployerId());
    }

    @Override
    public Company findCompanyByEmployerId(Integer employerId) {
        String sql = "SELECT companyId,companyName,companyDescription,companyCulture,companyAddress,specificAddress,employerId,jobOpportunities  from companies where employerId = ?";
        List<Company> companiesList = baseQuery(Company.class, sql, employerId);
        return companiesList != null && companiesList.size() > 0 ? companiesList.get(0) : null;
    }

    @Override
    public CompanyReview findCompanyReviewByEmployerId(Integer employerId) {
        String sql = "SELECT companyReviewId,companyReview,employerId,companyReviewTime,companyId,rating from company_review where employerId = ?";
        List<CompanyReview> companyReviewList = baseQuery(CompanyReview.class, sql, employerId);
        return companyReviewList != null && companyReviewList.size() > 0 ? companyReviewList.get(0) : null;
    }
    @Override
    public int saveCompanyReview(CompanyReview companyReview) {
        String sql = "insert into company_review values(DEFAULT,?,?,?,?,?)";
        return baseUpdate(sql, companyReview.getCompanyReview(), companyReview.getEmployerId(),companyReview.getCompanyReviewTime(),companyReview.getCompanyId(),companyReview.getRating());
    }

    @Override
    public int updateCompanyReview(CompanyReview companyReview) {
        String sql = "update  company_review set companyReview=?,employerId=?,companyReviewTime=?,companyId=?,rating=? where employerId = ?";
        return baseUpdate(sql, companyReview.getCompanyReview(), companyReview.getEmployerId(),companyReview.getCompanyReviewTime(),companyReview.getCompanyId(),companyReview.getRating(),companyReview.getEmployerId());
    }

    @Override
    public List<Company> findAllCompanyReview(String companyName) {
        String sql = "SELECT companyId,companyName,companyDescription,companyCulture,companyAddress,specificAddress,employerId,jobOpportunities  from companies where companyName = ?";
        List<Company> companyList = baseQuery(Company.class, sql, companyName);
        return companyList;
    }

    @Override
    public Employer findNameByEmployerId(Integer employerId) {
        String sql = "SELECT employerId,username,password,defaultList from employers where employerId = ?";
        List<Employer> employersList = baseQuery(Employer.class, sql, employerId);
        return employersList != null && employersList.size() > 0 ? employersList.get(0) : null;
    }

    @Override
    public List<ApplicationManagement> getAllApplication(Integer employerId) {
        String sql = "SELECT applicationManagementId,jobSeekerId,employerId,campusRecruitmentId,status  from application_management where employerId = ?";
        List<ApplicationManagement> applicationManagementList = baseQuery(ApplicationManagement.class, sql, employerId);
        return applicationManagementList;
    }

    @Override
    public List<Profiles> getProfileByEducation(String education) {
        String educationWithWildcard = "%" + education + "%";
        String sql = "SELECT profileId,workExperience,education,skills,age,jobSeekerId,gender,hobby,realName,expectedSalary,expectedPosition from profiles where education like ?";
        List<Profiles> profilesList = baseQuery(Profiles.class, sql, educationWithWildcard);
        return profilesList;
    }

    @Override
    public List<Profiles> getProfileByWorkExperience(String workExperience) {
        String workExperienceWithWildcard = "%" + workExperience + "%";
        String sql = "SELECT profileId,workExperience,education,skills,age,jobSeekerId,gender,hobby,realName,expectedSalary,expectedPosition from profiles where workExperience like ?";
        List<Profiles> profilesList = baseQuery(Profiles.class, sql, workExperienceWithWildcard);
        return profilesList;
    }

    @Override
    public List<Profiles> getProfileByAge(Integer age) {
        String ageWithWildcard = "%" + age + "%";
        String sql = "SELECT profileId,workExperience,education,skills,age,jobSeekerId,gender,hobby,realName,expectedSalary,expectedPosition from profiles where age like ?";
        List<Profiles> profilesList = baseQuery(Profiles.class, sql, ageWithWildcard);
        return profilesList;
    }

    @Override
    public List<Profiles> getProfileByPosition(String expectedPosition) {
        String expectedPositionWithWildcard = "%" + expectedPosition + "%";
        String sql = "SELECT profileId,workExperience,education,skills,age,jobSeekerId,gender,hobby,realName,expectedSalary,expectedPosition from profiles where expectedPosition like ?";
        List<Profiles> profilesList = baseQuery(Profiles.class, sql, expectedPositionWithWildcard);
        return profilesList;
    }

    @Override
    public Resume checkResume(Integer jobSeekerId) {
        String sql = "select resumeId, imgPath,familyAddress,telephoneNumber,email,projectName,projectDate1,projectDate2,projectDesc,preferredFunction,preferredLocation,salaryExpectation,companyName,jobCategory,workDate1,workDate2,salary,jobDesc,personalAdvantage,jobSeekerId from resume  where jobSeekerId = ?";
        List<Resume> resumeList = baseQuery(Resume.class, sql, jobSeekerId);
        return resumeList != null && resumeList.size() > 0 ? resumeList.get(0) : null;
    }

    @Override
    public int arrangingInterviews(ApplicationManagement applicationManagement) {
        String sql = "update application_management SET status=?  where applicationManagementId = ?";
        return baseUpdate(sql,applicationManagement.getStatus(),applicationManagement.getApplicationManagementId());
    }

    @Override
    public ApplicationManagement getStatus(ApplicationManagement applicationManagement) {
        String sql = "select applicationManagementId, jobSeekerId,employerId,campusRecruitmentId,status from application_Management  where applicationManagementId = ?";
        List<ApplicationManagement> applicationManagementList = baseQuery(ApplicationManagement.class, sql, applicationManagement.getApplicationManagementId());
        return applicationManagementList != null && applicationManagementList.size() > 0 ? applicationManagementList.get(0) : null;
    }

    @Override
    public InterviewDetails findInterviewDetails(Integer applicationManagementId) {
        String sql = "select interviewDetailsId,location,time,tips,applicationManagementId from interview_details  where applicationManagementId = ?";
        List<InterviewDetails> interviewDetailsList = baseQuery(InterviewDetails.class, sql, applicationManagementId);
        return interviewDetailsList != null && interviewDetailsList.size() > 0 ? interviewDetailsList.get(0) : null;
    }

    @Override
    public int saveInterviewDetails(InterviewDetails interviewDetails) {
        String sql = "insert into interview_details values(DEFAULT,?,?,?,?)";
        return baseUpdate(sql,interviewDetails.getLocation(),interviewDetails.getTime(),interviewDetails.getTips(),interviewDetails.getApplicationManagementId());
    }

    @Override
    public int updateInterviewDetails(InterviewDetails interviewDetails) {
        String sql = "update  interview_details set location=?,time=?,tips=? where applicationManagementId = ?";
        return baseUpdate(sql,interviewDetails.getLocation(),interviewDetails.getTime(),interviewDetails.getTips(),interviewDetails.getApplicationManagementId());
    }


}
