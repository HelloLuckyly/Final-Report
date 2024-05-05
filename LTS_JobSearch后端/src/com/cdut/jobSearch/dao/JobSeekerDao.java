package com.cdut.jobSearch.dao;

import com.cdut.jobSearch.pojo.*;

import java.util.List;

public interface JobSeekerDao {
    int addJobSeeker(JobSeeker jobSeeker);


    JobSeeker login(String username);
    int saveProfiles(Profiles profiles);
    int modifyUsername(String username,Integer jobSeekerId);
    Profiles checkProfile(Integer jobSeekerId);
    int addProfiles(Profiles profiles);
    List<CampusRecruitment> findAllPostCampusRecruitment(Integer defaultList);
    Employer findEmployerNameByEmployerId(Integer employerId);
    Company findCompanyNameByEmployerId(Integer employerId);
    List<CampusRecruitment> getAllJobByIndustry(String industry);
    List<CampusRecruitment> getAllJobByJobTitle(String position);
    List<CampusRecruitment> getAllJobByLocation(String workCity);
    JobAlerts checkJobAlerts(Integer jobSeekerId);
    Integer removeJobAlerts(Integer jobSeekerId );

    Integer addJobAlerts(JobAlerts jobAlerts);
    Integer sendApply(ApplicationManagement applicationManagement);
    ApplicationManagement checkApply(Integer jobSeekerId);
    List<ApplicationManagement>  getAllApplication(Integer jobSeekerId);
    CampusRecruitment getAllCampusRecruitmentByCampusRecruitmentId(Integer campusRecruitmentId);
    Integer removeApply(Integer campusRecruitmentId);
    JobSeeker getUserNameById(Integer jobSeekerId);
    int saveResume(Resume resume);
    int saveBasicInfo(Resume resume);
    Resume findResumeByJobSeekerId(Integer jobSeekerId);
    int saveProjectExperience(Resume resume);
    int saveCareerObjective(Resume resume);
    int saveWorkExperience(Resume resume);
    int savePersonalAdvantage(Resume resume);
    int updateFile1(Resume resume);
    int updateResume(Resume resume);

}
