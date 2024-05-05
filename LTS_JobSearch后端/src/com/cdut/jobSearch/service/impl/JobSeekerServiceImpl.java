package com.cdut.jobSearch.service.impl;

import com.cdut.jobSearch.dao.JobSeekerDao;
import com.cdut.jobSearch.dao.JobSeekerDao;
import com.cdut.jobSearch.dao.impl.JobSeekerDaoImpl;
import com.cdut.jobSearch.pojo.*;
import com.cdut.jobSearch.pojo.JobSeeker;
import com.cdut.jobSearch.service.JobSeekerService;
import com.cdut.jobSearch.util.MD5Util;

import java.util.List;

public class JobSeekerServiceImpl implements JobSeekerService {

    JobSeekerDao userDao = new JobSeekerDaoImpl();
    @Override
    public int regist(JobSeeker jobSeeker) {
        String getJobSeekerPwd = jobSeeker.getPassword();
        String encrypt = MD5Util.encrypt(getJobSeekerPwd);
        jobSeeker.setPassword(encrypt);
        int rows = userDao.addJobSeeker(jobSeeker);
        return rows;
    }

    @Override
    public int saveProfiles(Profiles profiles) {
        int rows = userDao.saveProfiles(profiles);
        return rows;
    }

    @Override
    public JobSeeker findByUsername(String username) {
       return  userDao.login(username);
    }



    @Override
    public Profiles checkProfile(Integer jobSeekerId) {

        return userDao.checkProfile(jobSeekerId);
    }

    @Override
    public int addProfiles(Profiles profiles) {
        return userDao.addProfiles(profiles);
    }

    @Override
    public List<CampusRecruitment> findAllPostCampusRecruitment(Integer defaultList) {
        return userDao.findAllPostCampusRecruitment(defaultList);
    }

    @Override
    public Employer findEmployerNameByEmployerId(Integer employerId) {
        return userDao.findEmployerNameByEmployerId(employerId);
    }

    @Override
    public Company findCompanyNameByEmployerId(Integer employerId) {
        return userDao.findCompanyNameByEmployerId(employerId);
    }

    @Override
    public List<CampusRecruitment> getAllJobByIndustry(String industry) {
        return userDao.getAllJobByIndustry(industry);
    }

    @Override
    public List<CampusRecruitment> getAllJobByJobTitle(String position) {
        return userDao.getAllJobByJobTitle(position);
    }

    @Override
    public List<CampusRecruitment> getAllJobByLocation(String workCity) {
        return userDao.getAllJobByLocation(workCity);
    }

    @Override
    public JobAlerts checkJobAlerts(Integer jobSeekerId) {
        return userDao.checkJobAlerts(jobSeekerId);
    }

    @Override
    public Integer removeJobAlerts(Integer jobSeekerId) {
        return userDao.removeJobAlerts(jobSeekerId);
    }

    @Override
    public Integer addJobAlerts(JobAlerts jobAlerts) {
        return userDao.addJobAlerts(jobAlerts);
    }

    @Override
    public Integer sendApply(ApplicationManagement applicationManagement) {
        return userDao.sendApply(applicationManagement);
    }

    @Override
    public ApplicationManagement checkApply(Integer jobSeekerId) {
        return userDao.checkApply(jobSeekerId);
    }

    @Override
    public List<ApplicationManagement> getAllApplication(Integer jobSeekerId) {
        return userDao.getAllApplication(jobSeekerId);
    }

    @Override
    public CampusRecruitment getAllCampusRecruitmentByCampusRecruitmentId(Integer campusRecruitmentId) {
        return userDao.getAllCampusRecruitmentByCampusRecruitmentId(campusRecruitmentId);
    }

    @Override
    public Integer removeApply(Integer campusRecruitmentId) {
        return userDao.removeApply(campusRecruitmentId);
    }

    @Override
    public JobSeeker getUserNameById(Integer jobSeekerId) {
        return userDao.getUserNameById(jobSeekerId);
    }

    @Override
    public int saveResume(Resume resume) {
        return userDao.saveResume(resume);
    }

    @Override
    public int saveBasicInfo(Resume resume) {
        return userDao.saveBasicInfo(resume);
    }

    @Override
    public Resume findResumeByJobSeekerId(Integer jobSeekerId) {
        return userDao.findResumeByJobSeekerId(jobSeekerId);
    }

    @Override
    public int saveProjectExperience(Resume resume) {
        return userDao.saveProjectExperience(resume);
    }

    @Override
    public int saveCareerObjective(Resume resume) {
        return userDao.saveCareerObjective(resume);
    }

    @Override
    public int saveWorkExperience(Resume resume) {
        return userDao.saveWorkExperience(resume);
    }

    @Override
    public int savePersonalAdvantage(Resume resume) {
        return userDao.savePersonalAdvantage(resume);
    }

    @Override
    public int updateFile1(Resume resume) {
        return userDao.updateFile1(resume);
    }

    @Override
    public int updateResume(Resume resume) {
        return userDao.updateResume(resume);
    }


}
