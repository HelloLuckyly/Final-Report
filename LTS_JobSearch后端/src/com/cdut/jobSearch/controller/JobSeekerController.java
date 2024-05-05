package com.cdut.jobSearch.controller;



import com.cdut.jobSearch.common.Result;
import com.cdut.jobSearch.common.ResultCodeEnum;
import com.cdut.jobSearch.pojo.*;
import com.cdut.jobSearch.service.JobSeekerService;
import com.cdut.jobSearch.service.impl.JobSeekerServiceImpl;
import com.cdut.jobSearch.util.MD5Util;
import com.cdut.jobSearch.util.WebUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 10,  // 10 MB
        maxFileSize         = 1024 * 1024 * 50,  // 50 MB
        maxRequestSize      = 1024 * 1024 * 100  // 100 MB
)
@WebServlet("/user/*")
public class JobSeekerController  extends BaseContoller {

    private JobSeekerService jobSeekerService = new JobSeekerServiceImpl();
//反射生效doget 和post可以省略
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String requestURI = req.getRequestURI();
//        if (requestURI.endsWith("/checkUsernameUsed")) {
//            checkUsernameUsed(req, resp);
//        } else if (requestURI.endsWith("/login")) {
//            login(req, resp);
//        } else if (requestURI.endsWith("/regist")) {
//            regist(req, resp);
//        } else {
//            System.out.println("error");
//        }
//    }
//
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("进入post");
//        doGet(req, resp);
//    }

    /**
     * 注册时,接收要注册的用户名,校验用户名是否被占用的业务接口
     */
    protected void checkUsernameUsed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收用户名
        String username = req.getParameter("username");
        if (username != null) {
            System.out.println("username:" + username);
        } else {
            System.out.println("kong");
        }
        // 调用服务层业务处理方法查询该用于名是否有对应的用户
        JobSeeker jobSeeker = jobSeekerService.findByUsername(username);
        // 如果有 响应 已占用
        // 如果没有 响应 可用

        Result result = Result.ok(null);

        if (null != jobSeeker) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        // 将result对象转换为JSON串响应给客户端
        WebUtil.writeJson(resp, result);
    }

    /**
     * 接收用登录请求,完成的登录业务接口
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1 接收用户名和密码
        JobSeeker jobSeeker = WebUtil.readJson(req, JobSeeker.class);
        //2 调用服务层方法,根据用户名查询用户信息
        JobSeeker loginUser = jobSeekerService.findByUsername(jobSeeker.getUsername());

        Result result = null;

        if (null == loginUser) {
            result = Result.build(null, ResultCodeEnum.USERNAEM_ERROR);

        } else if (!MD5Util.encrypt(jobSeeker.getPassword()).equals(loginUser.getPassword())) {
            result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        } else {
            // 登录程序,将用户uid和username响应给客户端
            Map data = new HashMap();
            loginUser.setPassword("");
            data.put("loginUser", loginUser);

            result = Result.ok(data);
        }
        // 3将登录结果响应给客户端
        WebUtil.writeJson(resp, result);

    }

    /**
     * 接收用户注册请求的业务处理方法( 业务接口 不是java中的interface  )
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
        JobSeeker jobSeeker = WebUtil.readJson(req, JobSeeker.class);
        jobSeeker.setDefaultList(6);
        // 2 调用服务层方法,完成注册功能
        //将参数放入一个SysUser对象中,在调用regist方法时传入
        int rows = jobSeekerService.regist(jobSeeker);
        // 3 根据注册结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }

    protected void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
//        Profiles profiles = WebUtil.readJson(req, Profiles.class);
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
        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值
        String realName = (String) jsonMap.get("realName");
        int age = (Integer) jsonMap.get("age");
        String workExperience = (String) jsonMap.get("workExperience");
        String education = (String) jsonMap.get("education");
        String skills = (String) jsonMap.get("skills");
        String gender = (String) jsonMap.get("gender");
        String hobby = (String) jsonMap.get("hobby");
        String expectedSalary = (String) jsonMap.get("expectedSalary");
        String expectedPosition = (String) jsonMap.get("expectedPosition");


        Profiles profiles = new Profiles();
        profiles.setExpectedPosition(expectedPosition);
        profiles.setExpectedSalary(expectedSalary);
        profiles.setRealName(realName);
        profiles.setWorkExperience(workExperience);
        profiles.setEducation(education);
        profiles.setSkills(skills);
        profiles.setJobSeekerId(jobSeekerId);
        profiles.setAge(age);
        profiles.setGender(gender);
        profiles.setHobby(hobby);
        int row2 = jobSeekerService.saveProfiles(profiles);

        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }


    protected void addProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
//        Profiles profiles = WebUtil.readJson(req, Profiles.class);
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


        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值
        String realName = (String) jsonMap.get("realName");
        String ageParameter = (String) jsonMap.get("age");
        String workExperience = (String) jsonMap.get("workExperience");
        String education = (String) jsonMap.get("education");
        String skills = (String) jsonMap.get("skills");
        String gender = (String) jsonMap.get("gender");
        String hobby = (String) jsonMap.get("hobby");
        String expectedSalary = (String) jsonMap.get("expectedSalary");
        String expectedPosition = (String) jsonMap.get("expectedPosition");

        int age = Integer.parseInt(ageParameter);

        Profiles profiles = new Profiles();
        profiles.setExpectedSalary(expectedSalary);
        profiles.setExpectedPosition(expectedPosition);
        profiles.setRealName(realName);
        profiles.setWorkExperience(workExperience);
        profiles.setEducation(education);
        profiles.setSkills(skills);
        profiles.setJobSeekerId(jobSeekerId);
        profiles.setAge(age);
        profiles.setGender(gender);
        profiles.setHobby(hobby);
        int row2 = jobSeekerService.addProfiles(profiles);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }


    protected void checkProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));
        // 2 调用服务层方法,完成检查功能
        Profiles profiles = jobSeekerService.checkProfile(jobSeekerId);
        // 3 根据注册结果(成功  失败) 做页面跳转

        Map data = new HashMap();
        data.put("profiles", profiles);

        Result result = null;
        if (profiles == null) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        } else {
            result = Result.ok(data);
        }
        WebUtil.writeJson(resp, result);
    }

    protected void findAllPostCampusRecruitment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
        int defaultList = Integer.parseInt(req.getParameter("defaultList"));
        // 2 调用服务层方法,完成检查功能
        List<CampusRecruitment> campusRecruitmentList = jobSeekerService.findAllPostCampusRecruitment(defaultList);

        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("campusRecruitmentList", campusRecruitmentList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void findEmployerNameByEmployerId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int employerId = Integer.parseInt(req.getParameter("employerId"));
        Employer employer = jobSeekerService.findEmployerNameByEmployerId(employerId);
        Map data = new HashMap();
        data.put("employer", employer);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp, result);
    }

    protected void findCompanyNameByEmployerId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int employerId = Integer.parseInt(req.getParameter("employerId"));
        Company company = jobSeekerService.findCompanyNameByEmployerId(employerId);
        Map data = new HashMap();
        data.put("company", company);
        Result result = Result.ok(data);
        WebUtil.writeJson(resp, result);
    }

    protected void getAllJobByIndustry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String industry = req.getParameter("industry");

        // 查询用户
        List<CampusRecruitment> searchByIndustryList = jobSeekerService.getAllJobByIndustry(industry);

        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("searchByIndustryList", searchByIndustryList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void getAllJobByWorkCity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String workCity = req.getParameter("workCity");

        // 查询用户
        List<CampusRecruitment> searchByWorkCityList = jobSeekerService.getAllJobByLocation(workCity);
        System.out.println(searchByWorkCityList);
        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("searchByWorkCityList", searchByWorkCityList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void getAllJobByJobTitle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        String position = req.getParameter("position");
        System.out.println(position);

        // 查询用户
        List<CampusRecruitment> searchByJobTitleList = jobSeekerService.getAllJobByJobTitle(position);
        System.out.println(searchByJobTitleList);
        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("searchByJobTitleList", searchByJobTitleList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void checkJobAlerts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));

        JobAlerts jobAlerts = jobSeekerService.checkJobAlerts(jobSeekerId);
        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("jobAlerts", jobAlerts);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void addJobAlerts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        JobAlerts jobAlerts = WebUtil.readJson(req, JobAlerts.class);
        Integer defaultList = 6;
        int row = jobSeekerService.addJobAlerts(jobAlerts);
        List<CampusRecruitment> CampusRecruitmentList = jobSeekerService.findAllPostCampusRecruitment(defaultList);
        List<CampusRecruitment> newCampusRecruitmentList = new ArrayList<>();
        for (CampusRecruitment campusRecruitment : CampusRecruitmentList) {
            // 获取当前 CampusRecruitment 对象的 dataTime 属性
            String campusRecruitmentTime = campusRecruitment.getPostTime();

            // 比较当前 CampusRecruitment 对象的时间和给定时间
            if (campusRecruitmentTime.compareTo(jobAlerts.getStartingTime()) > 0) {
                // 如果当前 CampusRecruitment 对象的时间更靠后，则添加到新的列表中
                newCampusRecruitmentList.add(campusRecruitment);
            }
        }
        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("jobAlertsList", newCampusRecruitmentList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void getJobAlerts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));
        JobAlerts jobAlerts = jobSeekerService.checkJobAlerts(jobSeekerId);
        Profiles profiles = jobSeekerService.checkProfile(jobSeekerId);

        Integer defaultList = 6;

        List<CampusRecruitment> CampusRecruitmentList = jobSeekerService.findAllPostCampusRecruitment(defaultList);
        List<CampusRecruitment> newCampusRecruitmentList = new ArrayList<>();
        for (CampusRecruitment campusRecruitment : CampusRecruitmentList) {
            // 获取当前 CampusRecruitment 对象的 dataTime 属性
            String campusRecruitmentTime = campusRecruitment.getPostTime();

            // 比较当前 CampusRecruitment 对象的时间和给定时间
            if (campusRecruitmentTime.compareTo(jobAlerts.getStartingTime()) > 0 && profiles.getEducation().equals(campusRecruitment.getEducation()) && profiles.getExpectedSalary().equals(campusRecruitment.getSalary()) && profiles.getExpectedPosition().equals(campusRecruitment.getPosition())) {
                // 如果当前 CampusRecruitment 对象的时间更靠后，则添加到新的列表中
                newCampusRecruitmentList.add(campusRecruitment);
            }
        }
        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("jobAlertsList", newCampusRecruitmentList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void removeJobAlerts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));
        Integer row = jobSeekerService.removeJobAlerts(jobSeekerId);

        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }


    protected void sendApply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 接收客户端提交的json参数,并转换为User对象,获取信息
        ApplicationManagement applicationManagement = WebUtil.readJson(req, ApplicationManagement.class);

        // 2 调用服务层方法

        int rows = jobSeekerService.sendApply(applicationManagement);
        // 3 根据 结果(成功  失败) 做页面跳转
        Result result = Result.ok(null);
        if (rows < 1) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);
    }


    protected void checkApply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));

        ApplicationManagement applicationManagement = jobSeekerService.checkApply(jobSeekerId);
        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("applicationManagement", applicationManagement);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void getAllApplication(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));

        List<ApplicationManagement> applicationManagementList = jobSeekerService.getAllApplication(jobSeekerId);

        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("applicationManagementList", applicationManagementList);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void getAllCampusRecruitmentByCampusRecruitmentId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int campusRecruitmentId = Integer.parseInt(req.getParameter("campusRecruitmentId"));

        CampusRecruitment campusRecruitment = jobSeekerService.getAllCampusRecruitmentByCampusRecruitmentId(campusRecruitmentId);

        // 将用户的所有放入一个Result对象
        Map data = new HashMap();
        data.put("campusRecruitment", campusRecruitment);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void removeApply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int campusRecruitmentId = Integer.parseInt(req.getParameter("campusRecruitmentId"));
        Integer row = jobSeekerService.removeApply(campusRecruitmentId);

        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }

    protected void getUserNameById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId = Integer.parseInt(req.getParameter("jobSeekerId"));
        JobSeeker jobSeeker = jobSeekerService.getUserNameById(jobSeekerId);

        Map data = new HashMap();
        data.put("jobSeeker", jobSeeker);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp, result);
    }

    protected void saveFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //获取上传的文件
        Part filePart = request.getPart("file");
        // 获取文件名
        String fileName = getSubmittedFileName(filePart);
       String savePath = "/img/" + fileName;



        System.out.println(savePath);
        File directory1 = new File(savePath).getAbsoluteFile().getParentFile();
        String absolutePath = directory1.getAbsolutePath();
        System.out.println(absolutePath);
        String realPath = absolutePath+"\\"+fileName;
        System.out.println(realPath);


        // 将上传的文件保存到指定路径
        try (InputStream inputStream = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save file");
            return;
        }

        Map data =new HashMap();
        data.put("realPath",realPath);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(response,result);

    }
    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    protected void showImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int jobSeekerId = Integer.parseInt(request.getParameter("jobSeekerId"));
        Resume resume = jobSeekerService.findResumeByJobSeekerId(jobSeekerId);

        // 检查是否找到相应的简历
        if (resume == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String savePath = resume.getImgPath();

        // 获取图片文件
        File file = new File(savePath);

        // 如果文件不存在，返回错误信息
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 读取图片内容并转换为 Base64 编码字符串
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String base64Image = Base64.getEncoder().encodeToString(fileContent);

        // 设置响应头
        response.setContentType("text/plain"); // 设置响应内容类型为文本类型，因为返回的是字符串
        response.setCharacterEncoding("UTF-8"); // 设置字符编码为 UTF-8

        // 将 Base64 编码字符串作为响应体返回给前端
        PrintWriter writer = response.getWriter();
        writer.write(base64Image);
        writer.flush();
    }

    protected void saveFile1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String imagePath = (String) jsonMap.get("imagePath");


//        String familyAddress = (String) jsonMap.get("familyAddress");
//        String telephoneNumber = (String) jsonMap.get("telephoneNumber");
//        String email = (String) jsonMap.get("email");
//        String projectName = (String) jsonMap.get("projectName");
//
//
//        String projectDate11 = (String) jsonMap.get("projectDate1");
//        System.out.println(projectDate11);
//        Date projectDate1 = null;
//        if (projectDate11 != "") {
//            projectDate1 = dateFormat.parse(projectDate11);
//            resume.setProjectDate1(projectDate1);
//
//        }
//
//
//        String projectDate22 = (String) jsonMap.get("projectDate2");
//        Date projectDate2 = null;
//        if (projectDate22!=""){
//            projectDate2 = dateFormat.parse(projectDate22);
//            resume.setProjectDate2(projectDate2);
//        }
//
//
//
//        String projectDesc = (String) jsonMap.get("projectDesc");
//        String preferredFunction = (String) jsonMap.get("preferredFunction");
//        String preferredLocation = (String) jsonMap.get("preferredLocation");
//        String salaryExpectation = (String) jsonMap.get("salaryExpectation");
//        String companyName = (String) jsonMap.get("companyName");
//        String jobCategory = (String) jsonMap.get("jobCategory");
//
//
//
//        String workDate11 = (String) jsonMap.get("workDate1");
//        Date workDate1 = null;
//        if (workDate11!=""){
//            workDate1 = dateFormat.parse(workDate11);
//            resume.setWorkDate1(workDate1);
//        }
//
//        String workDate22 = (String) jsonMap.get("workDate2");
//        Date workDate2 = null;
//        if (workDate22!=""){
//            workDate2 = dateFormat.parse(workDate22);
//            resume.setWorkDate2(workDate2);
//        }
//
//
//        String salary = (String) jsonMap.get("salary");
//        String jobDesc = (String) jsonMap.get("jobDesc");
//        String personalAdvantage = (String) jsonMap.get("personalAdvantage");


        String familyAddress = (String) jsonMap.get("familyAddress");
        familyAddress = familyAddress.isEmpty() ? "defaultFamilyAddress" : familyAddress;
        resume.setFamilyAddress(familyAddress);


        String telephoneNumber = (String) jsonMap.get("telephoneNumber");
        telephoneNumber = telephoneNumber.isEmpty() ? "123456789" :telephoneNumber;
        resume.setTelephoneNumber(telephoneNumber);




        String email = (String) jsonMap.get("email");
        email = email.isEmpty() ? "example@email.com" :email;
        resume.setEmail(email);

        String projectName = (String) jsonMap.get("projectName");
        projectName =projectName.isEmpty() ? "null" :projectName;
        resume.setProjectName(projectName);



        // 用于设置项目日期的初始值
        Date defaultProjectDate1 = dateFormat.parse("2024-01-01"); // 设置一个默认的初始日期，可以根据需要更改

        String projectDate11 = (String) jsonMap.get("projectDate1");
        System.out.println(projectDate11);
        Date projectDate1 = defaultProjectDate1; // 初始值设为默认日期
        if (projectDate11 != null && !projectDate11.isEmpty()) {
            projectDate1 = dateFormat.parse(projectDate11); // 如果projectDate11不为空，将其解析为日期
        }
        resume.setProjectDate1(projectDate1); // 设置项目日期


        String projectDate22 = (String) jsonMap.get("projectDate1");
        System.out.println(projectDate11);
        Date projectDate2 = defaultProjectDate1; // 初始值设为默认日期
        if (projectDate22 != null && !projectDate22.isEmpty()) {
            projectDate2 = dateFormat.parse(projectDate22); // 如果projectDate11不为空，将其解析为日期
        }
        resume.setProjectDate2(projectDate2); // 设置项目日期

        String projectDesc = (String) jsonMap.get("projectDesc");
        projectDesc =projectDesc.isEmpty() ? "null" : projectDesc;
        resume.setProjectDesc(projectDesc);

        String preferredFunction = (String) jsonMap.get("preferredFunction");
        preferredFunction = preferredFunction != null && !preferredFunction.isEmpty() ? preferredFunction : "null";
        resume.setPreferredFunction(preferredFunction);

        String preferredLocation = (String) jsonMap.get("preferredLocation");
        preferredLocation = preferredLocation != null && !preferredLocation.isEmpty() ? preferredLocation : "null";
        resume.setPreferredLocation(preferredLocation);

        String salaryExpectation = (String) jsonMap.get("salaryExpectation");
        salaryExpectation = salaryExpectation != null && !salaryExpectation.isEmpty() ? salaryExpectation : "null";
        resume.setSalaryExpectation(salaryExpectation);

        String companyName = (String) jsonMap.get("companyName");
        companyName = companyName != null && !companyName.isEmpty() ? companyName : "null";
        resume.setCompanyName(companyName);

        String jobCategory = (String) jsonMap.get("jobCategory");
        jobCategory = jobCategory != null && !jobCategory.isEmpty() ? jobCategory : "null";
        resume.setJobCategory(jobCategory);

        String workDate11 = (String) jsonMap.get("workDate1");
        System.out.println(workDate11);
        Date workDate1 = defaultProjectDate1; // 初始值设为默认日期
        if (workDate11 != null && !workDate11.isEmpty()) {
            workDate1 = dateFormat.parse(workDate11); //
        }
        resume.setWorkDate1(workDate1); // 设置项目日期



        String workDate22 = (String) jsonMap.get("workDate2");
        System.out.println(workDate22);
        Date workDate2 = defaultProjectDate1; // 初始值设为默认日期
        if (workDate22 != null && !workDate22.isEmpty()) {
            workDate2 = dateFormat.parse(workDate22); //
        }
        resume.setWorkDate2(workDate2); // 设置项目日期




        String salary = (String) jsonMap.get("salary");
        salary = salary != null && !salary.isEmpty() ? salary : "null";
        resume.setSalary(salary);

        String jobDesc = (String) jsonMap.get("jobDesc");
        jobDesc = jobDesc != null && !jobDesc.isEmpty() ? jobDesc : "null";
        resume.setJobDesc(jobDesc);

        String personalAdvantage = (String) jsonMap.get("personalAdvantage");
        personalAdvantage = personalAdvantage != null && !personalAdvantage.isEmpty() ? personalAdvantage : "null";
        resume.setPersonalAdvantage(personalAdvantage);


        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值


        resume.setImgPath(imagePath);
        resume.setFamilyAddress(familyAddress);
        resume.setTelephoneNumber(telephoneNumber);
        resume.setEmail(email);
        resume.setProjectName(projectName);
        resume.setProjectDesc(projectDesc);
        resume.setPreferredFunction(preferredFunction);
        resume.setPreferredLocation(preferredLocation);
        resume.setSalaryExpectation(salaryExpectation);
        resume.setCompanyName(companyName);
        resume.setJobCategory(jobCategory);
        resume.setSalary(salary);
        resume.setJobDesc(jobDesc);
        resume.setPersonalAdvantage(personalAdvantage);
        resume.setJobSeekerId(jobSeekerId);



        int row1 = jobSeekerService.saveResume(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);

    }

        protected void saveProjectExperience(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        String projectName = (String) jsonMap.get("projectName");


        String projectDate11 = (String) jsonMap.get("projectDate1");
        System.out.println(projectDate11);
        Date projectDate1 = null;
        if (!projectDate11.isEmpty()) {
            projectDate1 = dateFormat.parse(projectDate11);
            resume.setProjectDate1(projectDate1);
            System.out.println( projectDate1);
        }


        String projectDate22 = (String) jsonMap.get("projectDate2");
        Date projectDate2 = null;
        if (!projectDate22 .isEmpty()) {
            projectDate2 = dateFormat.parse(projectDate22);
            resume.setProjectDate2(projectDate2);
        }
        String projectDesc = (String) jsonMap.get("projectDesc");

        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值
        resume.setProjectName(projectName);
        resume.setProjectDesc(projectDesc);
        resume.setJobSeekerId(jobSeekerId);


        int row1 = jobSeekerService.saveProjectExperience(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }



    protected void saveBasicInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();
        String familyAddress = (String) jsonMap.get("familyAddress");
        String telephoneNumber = (String) jsonMap.get("telephoneNumber");
        String email = (String) jsonMap.get("email");
        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值



        resume.setFamilyAddress(familyAddress);
        resume.setTelephoneNumber(telephoneNumber);
        resume.setEmail(email);

        resume.setJobSeekerId(jobSeekerId);


        int row1 = jobSeekerService.saveBasicInfo(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }

        protected void saveCareerObjective(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();

        String preferredFunction = (String) jsonMap.get("preferredFunction");
        String preferredLocation = (String) jsonMap.get("preferredLocation");
        String salaryExpectation = (String) jsonMap.get("salaryExpectation");
        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值
        resume.setPreferredFunction(preferredFunction);
        resume.setPreferredLocation(preferredLocation);
        resume.setSalaryExpectation(salaryExpectation);
        resume.setJobSeekerId(jobSeekerId);


        int row1 = jobSeekerService.saveCareerObjective(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }

    protected void saveWorkExperience(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        String companyName = (String) jsonMap.get("companyName");
        String jobCategory = (String) jsonMap.get("jobCategory");


        String workDate11 = (String) jsonMap.get("workDate1");
        Date workDate1 = null;
        if (workDate11 != "") {
            workDate1 = dateFormat.parse(workDate11);
            resume.setWorkDate1(workDate1);
        }

        String workDate22 = (String) jsonMap.get("workDate2");
        Date workDate2 = null;
        if (workDate22 != "") {
            workDate2 = dateFormat.parse(workDate22);
            resume.setWorkDate2(workDate2);
        }


        String salary = (String) jsonMap.get("salary");
        String jobDesc = (String) jsonMap.get("jobDesc");

        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值



        resume.setCompanyName(companyName);
        resume.setJobCategory(jobCategory);
        resume.setSalary(salary);
        resume.setJobDesc(jobDesc);
        resume.setJobSeekerId(jobSeekerId);


        int row1 = jobSeekerService.saveWorkExperience(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }

    protected void savePersonalAdvantage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();

        String personalAdvantage = (String) jsonMap.get("personalAdvantage");
        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值
        resume.setPersonalAdvantage(personalAdvantage);
        resume.setJobSeekerId(jobSeekerId);


        int row1 = jobSeekerService.savePersonalAdvantage(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }

    protected void findResumeByJobSeekerId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        int jobSeekerId =Integer.parseInt(req.getParameter("jobSeekerId"));

        Resume resume = jobSeekerService.findResumeByJobSeekerId(jobSeekerId);



            // 判断每个属性是否为空或不存在，然后赋予默认值
            // 判断 imgPath 属性并赋予默认值
//            if (resume.getImgPath() == null || resume.getImgPath().isEmpty()) {
//                resume.setImgPath("default_img_path.jpg");
//            }

            // 判断 familyAddress 属性并赋予默认值


        // 将用户的所有放入一个Result对象
        Map data =new HashMap();
        data.put("resume",resume);

        Result result = Result.ok(data);
        // 将Result对象转换为json响应给客户端
        WebUtil.writeJson(resp,result);
    }
    protected void updateFile1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {
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
        Resume resume = new Resume();

        String imgPath = (String) jsonMap.get("imgPath");
        int jobSeekerId = (Integer) jsonMap.get("jobSeekerId");//从map中取值
        resume.setImgPath(imgPath);
        resume.setJobSeekerId(jobSeekerId);


        int row1 = jobSeekerService.updateFile1(resume);
        Result result = Result.ok(null);
        WebUtil.writeJson(resp, result);
    }
}
