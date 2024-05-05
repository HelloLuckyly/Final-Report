package com.cdut.jobSearch.dao.impl;

import com.cdut.jobSearch.dao.BaseDao;
import com.cdut.jobSearch.dao.JobSeekerDao;
import com.cdut.jobSearch.pojo.*;

import java.util.List;

public class JobSeekerDaoImpl extends BaseDao implements JobSeekerDao {

    @Override
    public int addJobSeeker(JobSeeker jobSeeker) {

        String sql = "insert into jobseekers values(DEFAULT,?,?,?)";
        return baseUpdate(sql, jobSeeker.getUsername(), jobSeeker.getPassword(),jobSeeker.getDefaultList());
        //        Integer profileID = jobSeeker.getProfileId();
//        if (profileID == null) {
////            当前时间毫秒
//            jobSeeker.setProfileId((int) System.currentTimeMillis());
//        }
    }

    @Override
    public JobSeeker login(String username) {
        String sql = "SELECT jobSeekerId,username,password,defaultList from jobseekers where username = ?";
        List<JobSeeker> jobSeekerList = baseQuery(JobSeeker.class, sql, username);
        return jobSeekerList != null && jobSeekerList.size() > 0 ? jobSeekerList.get(0) : null;
    }

    @Override
    public int saveProfiles(Profiles profiles) {

        String sql = "update profiles set WorkExperience = ?,education=?,skills=?,age=?,jobSeekerId=?,gender=?,hobby=?,realName=?,expectedSalary=?,expectedPosition=? where jobSeekerId=?";
        return baseUpdate(sql, profiles.getWorkExperience(),profiles.getEducation(),profiles.getSkills(),profiles.getAge(),profiles.getJobSeekerId(),profiles.getGender(),profiles.getHobby(),profiles.getRealName(),profiles.getExpectedSalary(),profiles.getExpectedPosition(),profiles.getJobSeekerId());
    }



    @Override
    public int modifyUsername(String username,Integer jobSeekerId) {
        String sql = "update jobseekers SET  username = ? where jobSeekerId = ?";
        return  baseUpdate(sql, username,jobSeekerId);


    }

    @Override
    public Profiles checkProfile(Integer jobSeekerId) {
        String sql = "SELECT profileId,workExperience,education,skills,age,jobSeekerId,gender,hobby,realName,expectedSalary,expectedPosition from profiles where jobSeekerId = ?";
        List<Profiles> profileList = baseQuery( Profiles.class, sql, jobSeekerId);
        return profileList != null && profileList.size() > 0 ? profileList.get(0) : null;
    }

    @Override
    public int addProfiles(Profiles profiles) {
        String sql = "insert into  profiles values(DEFAULT,?,?,?,?,?,?,?,?,?,?)";
        return baseUpdate(sql, profiles.getWorkExperience(),profiles.getEducation(),profiles.getSkills(),profiles.getAge(),profiles.getJobSeekerId(),profiles.getGender(),profiles.getHobby(),profiles.getRealName(),profiles.getExpectedSalary(),profiles.getExpectedPosition());
    }

    @Override
    public List<CampusRecruitment> findAllPostCampusRecruitment(Integer defaultList) {
        String sql = "select campusRecruitmentId, workingTitle,salary,position,experience,education,count,workCity,addressDetails,workContent,jobRequirements,employerId,defaultList,industry,postTime from campus_recruitment where defaultList = ? ";
        List<CampusRecruitment> campusRecruitmentList = baseQuery(CampusRecruitment.class, sql, defaultList);
        return campusRecruitmentList;
    }

    @Override
    public Employer findEmployerNameByEmployerId(Integer employerId) {
        String sql = "select employerId,username,password,defaultList from employers where employerId = ?";
        List<Employer> employerList = baseQuery(Employer.class, sql, employerId);
        return employerList != null && employerList.size() > 0 ? employerList.get(0) : null;
    }

    @Override
    public Company findCompanyNameByEmployerId(Integer employerId) {
        String sql = "SELECT companyId,companyName,companyDescription,companyCulture,companyAddress,specificAddress,employerId,jobOpportunities  from companies where employerId like ?";
        List<Company> companiesList = baseQuery(Company.class, sql, employerId);
        return companiesList != null && companiesList.size() > 0 ? companiesList.get(0) : null;
    }

    @Override
    public List<CampusRecruitment> getAllJobByIndustry(String industry) {
        String industryWithWildcard = "%" + industry + "%";
        String sql = "select campusRecruitmentId, workingTitle,salary,position,experience,education,count,workCity,addressDetails,workContent,jobRequirements,employerId,defaultList,industry,postTime from campus_recruitment where industry like ? ";
        List<CampusRecruitment> searchByIndustryList = baseQuery(CampusRecruitment.class, sql, industryWithWildcard);
        return searchByIndustryList;
    }

    @Override
    public List<CampusRecruitment> getAllJobByJobTitle(String position) {
        // 在参数中添加通配符 %
        String positionWithWildcard = "%" + position + "%";
        String sql = "SELECT campusRecruitmentId, workingTitle, salary, position, experience, education, count, workCity, addressDetails, workContent, jobRequirements, employerId, defaultList, industry,postTime FROM campus_recruitment WHERE position LIKE ?";
        List<CampusRecruitment> searchByJobTitleList = baseQuery(CampusRecruitment.class, sql, positionWithWildcard);
        return searchByJobTitleList;
    }
    @Override
    public List<CampusRecruitment> getAllJobByLocation(String workCity) {
        String workCityWithWildcard = "%" + workCity + "%";
        String sql = "select campusRecruitmentId, workingTitle,salary,position,experience,education,count,workCity,addressDetails,workContent,jobRequirements,employerId,defaultList,industry,postTime from campus_recruitment where workCity like ? ";
        List<CampusRecruitment> searchByWorkCityList = baseQuery(CampusRecruitment.class, sql, workCityWithWildcard);
        return searchByWorkCityList;
    }

    @Override
    public JobAlerts checkJobAlerts(Integer jobSeekerId) {
        String sql = "select jobAlertsId,jobSeekerId,mode,startingTime from job_alerts where jobSeekerId = ?";
        List<JobAlerts> jobAlertsList = baseQuery(JobAlerts.class, sql, jobSeekerId);
        return jobAlertsList != null && jobAlertsList.size() > 0 ? jobAlertsList.get(0) : null;
    }

    @Override
    public Integer removeJobAlerts(Integer jobSeekerId) {
        String sql = "delete from job_alerts where jobSeekerId = ?";
        return baseUpdate(sql,jobSeekerId);
    }

    @Override
    public Integer addJobAlerts(JobAlerts jobAlerts) {
        String sql = "insert into job_alerts values(DEFAULT,?,?,?)";
        return baseUpdate(sql,jobAlerts.getJobSeekerId(),jobAlerts.getMode(),jobAlerts.getStartingTime());
    }

    @Override
    public Integer sendApply(ApplicationManagement applicationManagement) {
        String sql = "insert into application_management values(DEFAULT,?,?,?,?)";
        return baseUpdate(sql,applicationManagement.getJobSeekerId(),applicationManagement.getEmployerId(),applicationManagement.getCampusRecruitmentId(),applicationManagement.getStatus());
    }

    @Override
    public ApplicationManagement checkApply(Integer jobSeekerId) {
        String sql = "select applicationManagementId,jobSeekerId,employerId,campusRecruitmentId,status from application_management where jobSeekerId = ?";
        List<ApplicationManagement> applicationManagementList = baseQuery(ApplicationManagement.class, sql, jobSeekerId);
        return applicationManagementList != null && applicationManagementList.size() > 0 ? applicationManagementList.get(0) : null;
    }

    @Override
    public List<ApplicationManagement> getAllApplication(Integer jobSeekerId) {
        String sql = "select applicationManagementId, jobSeekerId,employerId,campusRecruitmentId,status from application_management where jobSeekerId = ? ";
        List<ApplicationManagement> applicationManagementList = baseQuery(ApplicationManagement.class, sql, jobSeekerId);
        return applicationManagementList;
    }

    @Override
    public CampusRecruitment getAllCampusRecruitmentByCampusRecruitmentId(Integer campusRecruitmentId) {
        String sql = "select campusRecruitmentId, workingTitle,salary,position,experience,education,count,workCity,addressDetails,workContent,jobRequirements,employerId,defaultList,industry,postTime from campus_recruitment where campusRecruitmentId = ? ";
        List<CampusRecruitment> campusRecruitmentList = baseQuery(CampusRecruitment.class, sql, campusRecruitmentId);
        return campusRecruitmentList != null && campusRecruitmentList.size() > 0 ? campusRecruitmentList.get(0) : null;
    }

    @Override
    public Integer removeApply(Integer campusRecruitmentId) {
        String sql = "delete from application_management where campusRecruitmentId = ?";
        return baseUpdate(sql,campusRecruitmentId);
    }

    @Override
    public JobSeeker getUserNameById(Integer jobSeekerId) {
        String sql = "select jobSeekerId,username,password,defaultList from jobseekers where jobSeekerId = ?";
        List<JobSeeker> jobSeekerList = baseQuery(JobSeeker.class, sql, jobSeekerId);
        return jobSeekerList != null && jobSeekerList.size() > 0 ? jobSeekerList.get(0) : null;
    }

    @Override
    public int saveResume(Resume resume) {
        String sql = "insert into resume values(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return baseUpdate(sql,resume.getImgPath(),resume.getFamilyAddress(),resume.getTelephoneNumber(),resume.getEmail(),resume.getProjectName(),resume.getProjectDate1(),resume.getProjectDate2(),resume.getProjectDesc(),resume.getPreferredFunction(),resume.getPreferredLocation(),resume.getSalaryExpectation(),resume.getCompanyName(),resume.getJobCategory(),resume.getWorkDate1(),resume.getWorkDate2(),resume.getSalary(),resume.getJobDesc(),resume.getPersonalAdvantage(),resume.getJobSeekerId());
    }

//    @Override
//    public int saveBasicInfo(Resume resume) {
//        String sql = "update resume SET imgPath=?,familyAddress=?,telephoneNumber=?,email=?,projectName=?,projectDate1=?,projectDate2=?,projectDesc=?,preferredFunction=?,preferredLocation=?,salaryExpectation=?,companyName=?,jobCategory=?,workDate1=?,workDate2=?,salary=?,jobDesc=?,personalAdvantage=?,jobSeekerId=?  where jobSeekerId = ?";
//        return baseUpdate(sql,resume.getImgPath(),resume.getFamilyAddress(),resume.getTelephoneNumber(),resume.getEmail(),resume.getProjectName(),resume.getProjectDate1(),resume.getProjectDate2(),resume.getProjectDesc(),resume.getPreferredFunction(),resume.getPreferredLocation(),resume.getSalaryExpectation(),resume.getCompanyName(),resume.getJobCategory(),resume.getWorkDate1(),resume.getWorkDate2(),resume.getSalary(),resume.getJobDesc(),resume.getPersonalAdvantage(),resume.getJobSeekerId(),resume.getJobSeekerId());
//    }


    @Override
    public int saveBasicInfo(Resume resume) {
        String sql = "update resume SET familyAddress=?,telephoneNumber=?,email=?  where jobSeekerId = ?";
        return baseUpdate(sql,resume.getFamilyAddress(),resume.getTelephoneNumber(),resume.getEmail(),resume.getJobSeekerId());
    }

    @Override
    public Resume findResumeByJobSeekerId(Integer jobSeekerId) {
        String sql = "select resumeId, imgPath,familyAddress,telephoneNumber,email,projectName,projectDate1,projectDate2,projectDesc,preferredFunction,preferredLocation,salaryExpectation,companyName,jobCategory,workDate1,workDate2,salary,jobDesc,personalAdvantage,jobSeekerId from resume  where jobSeekerId = ?";
        List<Resume> resumeList = baseQuery(Resume.class, sql, jobSeekerId);
        return resumeList != null && resumeList.size() > 0 ? resumeList.get(0) : null;
    }

    @Override
    public int saveProjectExperience(Resume resume) {
        String sql = "update resume SET projectName=?,projectDate1=?,projectDate2=?,projectDesc=?  where jobSeekerId = ?";
        return baseUpdate(sql,resume.getProjectName(),resume.getProjectDate1(),resume.getProjectDate2(),resume.getProjectDesc(),resume.getJobSeekerId());
    }

    @Override
    public int saveCareerObjective(Resume resume) {
        String sql = "update resume SET preferredFunction=?,preferredLocation=?,salaryExpectation=?  where jobSeekerId = ?";
        return baseUpdate(sql,resume.getPreferredFunction(),resume.getPreferredLocation(),resume.getSalaryExpectation(),resume.getJobSeekerId());
    }

    @Override
    public int saveWorkExperience(Resume resume) {
        String sql = "update resume SET companyName=?,jobCategory=?,workDate1=?,workDate2=?,salary=?,jobDesc=?  where jobSeekerId = ?";
        return baseUpdate(sql,resume.getCompanyName(),resume.getJobCategory(),resume.getWorkDate1(),resume.getWorkDate2(),resume.getSalary(),resume.getJobDesc(),resume.getJobSeekerId());
    }

    @Override
    public int savePersonalAdvantage(Resume resume) {
        String sql = "update resume SET personalAdvantage=?  where jobSeekerId = ?";
        return baseUpdate(sql,resume.getPersonalAdvantage(),resume.getJobSeekerId());
    }

    @Override
    public int updateFile1(Resume resume) {
        String sql = "update resume SET imgPath=?  where jobSeekerId = ?";
        return baseUpdate(sql,resume.getImgPath(),resume.getJobSeekerId());
    }

    @Override
    public int updateResume(Resume resume) {
        String sql = "update resume SET imgPath=?,familyAddress=?,telephoneNumber=?,email=?,projectName=?,projectDate1=?,projectDate2=?,projectDesc=?,preferredFunction=?,preferredLocation=?,salaryExpectation=?,companyName=?,jobCategory=?,workDate1=?,workDate2=?,salary=?,jobDesc=?,personalAdvantage=? where jobSeekerId = ?";
        return baseUpdate(sql, resume.getImgPath(), resume.getFamilyAddress(), resume.getTelephoneNumber(), resume.getEmail(), resume.getProjectName(), resume.getProjectDate1(), resume.getProjectDate2(), resume.getProjectDesc(), resume.getPreferredFunction(), resume.getPreferredLocation(), resume.getSalaryExpectation(), resume.getCompanyName(), resume.getJobCategory(), resume.getWorkDate1(), resume.getWorkDate2(), resume.getSalary(), resume.getJobDesc(), resume.getPersonalAdvantage(), resume.getJobSeekerId());

    }
}