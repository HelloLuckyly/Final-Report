package com.cdut.jobSearch.service.impl;

import com.cdut.jobSearch.dao.EmployerDao;
import com.cdut.jobSearch.dao.impl.EmployerDaoImpl;
import com.cdut.jobSearch.pojo.*;
import com.cdut.jobSearch.service.EmployerService;
import com.cdut.jobSearch.util.MD5Util;

import java.util.List;

public class EmployerServiceImpl implements EmployerService {
    EmployerDao useDao = new EmployerDaoImpl();

    @Override
        public int regist(Employer employer) {
        String getEmployerPwd = employer.getPassword();
        String encrypt = MD5Util.encrypt(getEmployerPwd);
        employer.setPassword(encrypt);
        int rows = useDao.addEmployer(employer);
        return rows;
    }

    @Override
    public Employer findByUsername(String username) {
        return  useDao.login(username);
    }

    @Override
    public int postCampusRecruitment(CampusRecruitment campusRecruitment) {

        return useDao.postCampusRecruitment(campusRecruitment);
    }

    @Override
    public List<CampusRecruitment> findAllPostCampusRecruitment(Integer employerId) {
        return useDao.findAllPostCampusRecruitment(employerId);
    }

    @Override
    public int saveCompanyInfo(Company company) {
        return useDao.saveCompanyInfo(company);
    }

    @Override
    public int updateCompanyInfo(Company company) {
        return useDao.updateCompanyInfo(company);
    }

    @Override
    public Company findCompanyByEmployerId(Integer employerId) {
        return useDao.findCompanyByEmployerId(employerId);
    }

    @Override
    public CompanyReview findCompanyReviewByEmployerId(Integer employerId) {
        return useDao.findCompanyReviewByEmployerId(employerId);
    }

    @Override
    public int saveCompanyReview(CompanyReview companyReview) {
        return useDao.saveCompanyReview(companyReview);
    }

    @Override
    public int updateCompanyReview(CompanyReview companyReview) {
        return useDao.updateCompanyReview(companyReview);
    }

    @Override
    public List<Company> findAllCompanyReview(String companyName) {
        return useDao.findAllCompanyReview(companyName);
    }

    @Override
    public Employer findNameByEmployerId(Integer employerId) {
        return useDao.findNameByEmployerId(employerId);
    }

    @Override
    public List<ApplicationManagement> getAllApplication(Integer employerId) {
        return useDao.getAllApplication(employerId);
    }

    @Override
    public List<Profiles> getProfileByWorkExperience(String workExperience) {
        return useDao.getProfileByWorkExperience(workExperience);
    }

    @Override
    public List<Profiles> getProfileByEducation(String education) {
        return useDao.getProfileByEducation(education);
    }

    @Override
    public List<Profiles> getProfileByAge(Integer age) {
        return useDao.getProfileByAge(age);
    }

    @Override
    public List<Profiles> getProfileByPosition(String expectedPosition) {
        return useDao.getProfileByPosition(expectedPosition);
    }

    @Override
    public Resume checkResume(Integer jobSeekerId) {
        return useDao.checkResume(jobSeekerId);
    }

    @Override
    public int arrangingInterviews(ApplicationManagement applicationManagement) {
        return useDao.arrangingInterviews(applicationManagement);
    }

    @Override
    public ApplicationManagement getStatus(ApplicationManagement applicationManagement) {
        return useDao.getStatus(applicationManagement);
    }

    @Override
    public InterviewDetails findInterviewDetails(Integer applicationManagementId) {
        return useDao.findInterviewDetails(applicationManagementId);
    }

    @Override
    public int saveInterviewDetails(InterviewDetails interviewDetails) {
        return useDao.saveInterviewDetails(interviewDetails);
    }

    @Override
    public int updateInterviewDetails(InterviewDetails interviewDetails) {
        return useDao.updateInterviewDetails(interviewDetails);
    }
}
