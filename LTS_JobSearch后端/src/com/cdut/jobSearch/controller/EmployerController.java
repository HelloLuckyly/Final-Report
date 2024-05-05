package com.cdut.jobSearch.controller;

import com.cdut.jobSearch.common.Result;
import com.cdut.jobSearch.common.ResultCodeEnum;
import com.cdut.jobSearch.pojo.*;
import com.cdut.jobSearch.service.EmployerService;
import com.cdut.jobSearch.service.JobSeekerService;
import com.cdut.jobSearch.service.impl.EmployerServiceImpl;
import com.cdut.jobSearch.service.impl.JobSeekerServiceImpl;
import com.cdut.jobSearch.util.MD5Util;
import com.cdut.jobSearch.util.WebUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@WebServlet("/eUser/*")
public class EmployerController extends BaseContoller {
    private EmployerService employerService = new EmployerServiceImpl();

    protected void checkUsernameUsed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收用户名
        String username = req.getParameter("username");
        if (username != null) {
            System.out.println("username:" + username);
        } else {
            System.out.println("kong");
        }
        // 调用服务层业务处理方法查询该用于名是否有对应的用户
        Employer employer = employerService.findByUsername(username);
        // 如果有 响应 已占用
        // 如果没有 响应 可用

        Result result = Result.ok(null);

        if (null != employer) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        // 将result对象转换为JSON串响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 接收用户名和密码
        Employer employer = WebUtil.readJson(req, Employer.class);
        //2 调用服务层方法,根据用户名查询用户信息
        Employer loginUserE = employerService.findByUsername(employer.getUsername());

        Result result = null;

        if (null == loginUserE) {
            result = Result.build(null, ResultCodeEnum.USERNAEM_ERROR);

        } else if (!MD5Util.encrypt(employer.getPassword()).equals(loginUserE.getPassword())) {
            result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        } else {
            // 登录程序,将用户uid和username响应给客户端
            Map data = new HashMap();
            loginUserE.setPassword("");
            data.put("loginUserE", loginUserE);

            result = Result.ok(data);
        }
        // 3将登录结果响应给客户端
        WebUtil.writeJson(resp, result);

    }


    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
        Employer employer = WebUtil.readJson(req, Employer.class);
        // 2 调用服务层方法,完成注册功能
        //将参数放入一个SysUser对象中,在调用regist方法时传入
        System.out.println(employer.getDefaultList());
        int rows = employerService.regist(employer);
        // 3 根据注册结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }


    protected void postCampusRecruitment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
        CampusRecruitment campusRecruitment = WebUtil.readJson(req, CampusRecruitment.class);
        // 2 调用服务层方法,完成功能
        int rows = employerService.postCampusRecruitment(campusRecruitment);
        // 3 根据结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);

    }

    protected void findAllPostCampusRecruitment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int employerId =Integer.parseInt(req.getParameter("employerId"));

        // 查询用户
        List<CampusRecruitment> campusRecruitmentList =employerService.findAllPostCampusRecruitment(employerId);

        // 将用户的所有放入一个Result对象
        Map data =new HashMap();
        data.put("campusRecruitmentList",campusRecruitmentList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void saveCompanyInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        Company company = WebUtil.readJson(req, Company.class);

        // 查询
        int rows = employerService.saveCompanyInfo(company);
        // 3 根据结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    protected void updateCompanyInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        Company company = WebUtil.readJson(req, Company.class);

        // 查询
        int rows = employerService.updateCompanyInfo(company);
        // 3 根据结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    protected void findCompanyByEmployerId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int employerId =Integer.parseInt(req.getParameter("employerId"));

        // 查询用户
        Company company =employerService.findCompanyByEmployerId(employerId);
        Result result = null;

        Map data = new HashMap();

         data.put("company", company);
          result = Result.ok(data);
    //  3将登录结果响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void findCompanyReviewByEmployerId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int employerId =Integer.parseInt(req.getParameter("employerId"));

        // 查询用户
        CompanyReview companyReview   =employerService.findCompanyReviewByEmployerId(employerId);
        Result result = null;

        Map data = new HashMap();

        data.put("review", companyReview);
        result = Result.ok(data);
        //  3将登录结果响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void saveCompanyReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
        // 接收请求中的uid参数
//        CompanyReview companyReview = WebUtil.readJson(req, CompanyReview.class);

        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine())!=null){
            stringBuilder.append(line);
        }
        String requestBody = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(requestBody, new TypeReference<Map<String, Object>>() {
        }); //解析存储到map中
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        int size = jsonMap.size();
        System.out.println(size);
        int employerId = (Integer) jsonMap.get("employerId");//从map中取值
        int companyId = (Integer) jsonMap.get("companyId");//从map中取值
        String companyReview1 = (String) jsonMap.get("companyReview");
        int rating = (Integer) jsonMap.get("rating");//从map中取值
        System.out.println(rating);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String companyReviewTime1 = (String) jsonMap.get("companyReviewTime");
        Date companyReviewTime = dateFormat.parse(companyReviewTime1);




        CompanyReview companyReview = new CompanyReview();
        companyReview.setCompanyReview(companyReview1);
        companyReview.setEmployerId(employerId);
        companyReview.setCompanyReviewTime(companyReviewTime);
        companyReview.setCompanyId(companyId);
        companyReview.setRating(rating);

        // 查询
        int rows = employerService.saveCompanyReview(companyReview);
        // 3 根据结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    protected void updateCompanyReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
        // 接收请求中的uid参数
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String requestBody = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(requestBody, new TypeReference<Map<String, Object>>() {
        }); //解析存储到map中
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        int size = jsonMap.size();
        System.out.println(size);
        int employerId = (Integer) jsonMap.get("employerId");//从map中取值
        int companyId = (Integer) jsonMap.get("companyId");//从map中取值
        String companyReview1 = (String) jsonMap.get("companyReview");
        int rating = (Integer) jsonMap.get("rating");//从map中取值

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String companyReviewTime1 = (String) jsonMap.get("companyReviewTime");
        Date companyReviewTime = dateFormat.parse(companyReviewTime1);


        CompanyReview companyReview = new CompanyReview();
        companyReview.setCompanyReview(companyReview1);
        companyReview.setEmployerId(employerId);
        companyReview.setCompanyReviewTime(companyReviewTime);
        companyReview.setCompanyId(companyId);
        companyReview.setRating(rating);
        // 查询
        int rows = employerService.updateCompanyReview(companyReview);
        // 3 根据结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);

    }


    protected void findAllCompanyReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String companyName =req.getParameter("companyName");

        // 查询用户
        List<Company> companyList =employerService.findAllCompanyReview(companyName);

        List<Integer> employerIdList = new ArrayList<>();
        for (Company company : companyList) {
            employerIdList.add(company.getEmployerId());
        }

        // 将用户的所有放入一个Result对象
        Map data =new HashMap();
        data.put("employerIdList",employerIdList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }

    protected void findNameByEmployerId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int employerId =Integer.parseInt(req.getParameter("employerId"));

        // 查询用户
        Employer employer =employerService.findNameByEmployerId(employerId);

        Map data =new HashMap();
        data.put("employer",employer);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void getAllApplication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int employerId =Integer.parseInt(req.getParameter("employerId"));

        // 查询用户
       List<ApplicationManagement>  applicationManagementList=employerService.getAllApplication(employerId);
        Map data =new HashMap();
        data.put("applicationManagementList",applicationManagementList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }

    protected void getProfileByWorkExperience(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String workExperience =req.getParameter("workExperience");

        // 查询用户
        List<Profiles> searchByWorkExperience=employerService.getProfileByWorkExperience(workExperience);
        Map data =new HashMap();
        data.put("searchByWorkExperience",searchByWorkExperience);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void getProfileByEducation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String education =req.getParameter("education");

        // 查询用户
        List<Profiles> searchByWorkEducation=employerService.getProfileByEducation(education);
        Map data =new HashMap();
        data.put("searchByWorkEducation",searchByWorkEducation);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void getProfileByAge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        Integer age =Integer.parseInt(req.getParameter("age"));

        // 查询用户
        List<Profiles> searchByAge=employerService.getProfileByAge(age);
        Map data =new HashMap();
        data.put("searchByAge",searchByAge);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void getProfileByPosition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String expectedPosition =req.getParameter("expectedPosition");

        // 查询用户
        List<Profiles> searchByPosition=employerService.getProfileByPosition(expectedPosition);
        Map data =new HashMap();
        data.put("searchByPosition",searchByPosition);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }

    protected void checkResume(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId =Integer.parseInt(req.getParameter("jobSeekerId"));

        // 查询用户
        Resume resume=employerService.checkResume(jobSeekerId);
        Map data =new HashMap();
        data.put("resume",resume);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }

    protected void arrangingInterviews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int applicationManagementId = Integer.parseInt(req.getParameter("applicationManagementId"));
        ApplicationManagement applicationManagement = new ApplicationManagement();
        applicationManagement.setApplicationManagementId(applicationManagementId);
        applicationManagement = employerService.getStatus(applicationManagement);
        String abc = "Arranging interviews";
        if (abc.equals(applicationManagement.getStatus())) {
            applicationManagement.setStatus("under review");
        } else {
            applicationManagement.setStatus("Arranging interviews");
        }

        int rows=employerService.arrangingInterviews(applicationManagement);
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    protected void findInterviewDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int applicationManagementId =Integer.parseInt(req.getParameter("applicationManagementId"));

        // 查询用户
        InterviewDetails interviewDetails=employerService.findInterviewDetails(applicationManagementId);
        Map data =new HashMap();
        data.put("interviewDetails",interviewDetails);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void saveInterviewDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        InterviewDetails interviewDetails = WebUtil.readJson(req,InterviewDetails.class);

        // 查询用户
        int rows=employerService.saveInterviewDetails(interviewDetails);
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }
    protected void updateInterviewDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        InterviewDetails interviewDetails = WebUtil.readJson(req,InterviewDetails.class);

        // 查询用户
        int rows=employerService.updateInterviewDetails(interviewDetails);
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

}