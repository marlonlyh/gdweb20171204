package com.whir.ht.cms.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.druid.support.logging.Log;
import com.whir.ht.cms.Message;
import com.whir.ht.cms.entity.ClassGroup;
import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.entity.Student;
import com.whir.ht.cms.service.ClassGroupService;
import com.whir.ht.cms.service.ClassesService;
import com.whir.ht.cms.service.GradeService;
import com.whir.ht.cms.service.StudentService;
import com.whir.ht.cms.service.UserService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 学生Controller
 * @author wuxiaoyuan
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/student")
public class StudentController extends BaseController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private ClassGroupService classGroupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JdbcTemplate forumJdbcTemplate;
	
	/**
	 * 检查用户名是否存在
	 * @param olduserName
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:student:edit")
	@RequestMapping(value = "checkuserName")
	public String checkLoginName(String olduserName, String userName) {
		if (userName !=null && userName.equals(olduserName)) {
			return "true";
		} else if (userName !=null && studentService.getName(userName) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 检查邮箱是否存在
	 * @param oldEmail
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("cms:student:edit")
	@RequestMapping(value = "checkuserEmail")
	public String checkEmail(String oldEmail, String email) {
		if (email !=null && email.equals(oldEmail)) {
			return "true";
		} else if (email !=null && studentService.getEmail(email) == null) {
			return "true";
		}
		return "false";
	}
	
	
	@ModelAttribute
	public Student get(@RequestParam(required = false)String id){
		if(StringUtils.isNotBlank(id)){
			return studentService.getStudent(id);
			
		}else{
			return new  Student();
		}
	}
	
	/**列表*/
	@RequiresPermissions("cms:student:view")
	@RequestMapping(value = {"list", ""})
	public String list(Student student, HttpServletRequest request, HttpServletResponse response, Model model){
		
		Page<Student> page = studentService.findStudent(new Page<Student>(request,response),student);
		model.addAttribute("page",page);
		return "cms/studentList";
	}
	
	/**添加、修改*/
	@RequiresPermissions("cms:student:view")
	@RequestMapping(value="form")
	public String form(Student student,Model model){
		String id = student.getId();
		if(StringUtils.isNotEmpty(id)){
			String gradeId = student.getGrade().getId();
			String classesId = student.getClasses().getId();
			if(null!=student.getClassGroup()){
				String classGroupId = student.getClassGroup().getId();
				model.addAttribute("classGroupId", classGroupId);
			}
			Grade grade = gradeService.get(gradeId);
			Classes classes= classesService.get(classesId);
			List<Classes> classesList=classesService.findList(grade);
			List<ClassGroup> classGroupList= classGroupService.findList(classes);;
			model.addAttribute("gradeId", gradeId);
			model.addAttribute("classesId", classesId);
			model.addAttribute("classesList", classesList);
			model.addAttribute("classGroupList", classGroupList);
		}
		List<Grade> gradeList = gradeService.getGrade();
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("student",student);
		return"cms/studentForm";
	}
	
	/**保存*/
	@RequiresPermissions("cms:student:edit")
	@RequestMapping(value="save")
	public String save(Student student,String userName,String newPassword,String gradeId,String classesId,String classGroupId,HttpServletRequest request, Model model,RedirectAttributes redirectAttributes){
		 Student pstudent  = null;
		 if(StringUtils.isNotBlank(student.getId())){
			 pstudent = studentService.getStudent(student.getId());
		 }
		if(StringUtils.isNotBlank(newPassword)){
			List<FrontUser> lFrontUsers = userService.getUserName(userName); 
			FrontUser user = null;
			for(FrontUser frontUser:lFrontUsers){
				if(DigestUtils.md5Hex(newPassword).equals(frontUser.getPassword())){
					user = frontUser;
					break;
				}
			}
			
			if(null!=user){
				model.addAttribute("message","用户名或密码重复");
				if(student.getId()!= null && !student.getId().equals("")){
					student=studentService.getStudent(student.getId());
					String id = student.getId();
					if(StringUtils.isNotEmpty(id)){
						Grade grade = gradeService.get(gradeId);
						Classes classes= classesService.get(classesId);
						List<Classes> classesList=classesService.findList(grade);
						List<ClassGroup> classGroupList= classGroupService.findList(classes);;
						model.addAttribute("gradeId", gradeId);
						model.addAttribute("classesId", classesId);
						model.addAttribute("classesList", classesList);
						model.addAttribute("classGroupList", classGroupList);
					}
					List<Grade> gradeList = gradeService.getGrade();
					model.addAttribute("gradeList", gradeList);
					model.addAttribute("student",student);
					return"cms/studentForm";
				}else{
					List<Grade> gradeList = gradeService.getGrade();
					model.addAttribute("gradeList", gradeList);
					return"cms/studentForm";
				}
			}else{
				student.setPassword(DigestUtils.md5Hex(newPassword));
			}
		}else{
			student.setPassword(pstudent.getPassword());
		}
				
		Grade grade = gradeService.get(gradeId);
		Classes classes = classesService.get(classesId);
		ClassGroup classGroup = classGroupService.get(classGroupId);
		student.setGrade(grade);
		student.setClasses(classes);
		student.setClassGroup(classGroup);	
	
		studentService.save(student);
		
		Boolean isAdd=false;
		String sql="";
		String user_id="";
		sql = "select user_id from jo_user where cuid='"+student.getId()+"' limit 0,1";
		try {
			List<Map<String, Object>> data=forumJdbcTemplate.queryForList(sql);		
			if ((null!=data) && (data.size()>0)){
				user_id = String.valueOf(data.get(0).get("user_id"));
				isAdd=false;
			}else{
				isAdd=true;
			}
			
			if (isAdd){
				/**获取目前最大记录数*/
				int lMaxCount = -1;
				sql = "select MAX(user_id) as maxcount from jb_user ";
				List<Map<String, Object>> data01=forumJdbcTemplate.queryForList(sql);
				if ((null!=data01) && (data01.size()>0)){
					Map<String, Object> map = data01.get(0);
					lMaxCount = Integer.valueOf(String.valueOf(map.get("maxcount")));		
				}
				
				if (lMaxCount>-1){
					lMaxCount=lMaxCount+1;
					//插入用户表jb_user
					sql = "insert into jb_user(user_id,username,group_id,register_time,register_ip,last_login_time,"+
							"login_count,upload_total,upload_size,upload_date,is_admin,is_disabled,PROHIBIT_POST,UPLOAD_TODAY,POINT"+
							",AVATAR_TYPE,TOPIC_COUNT,REPLY_COUNT,PRIME_COUNT,POST_TODAY,PRESTIGE,magic_packet_size,active_level_id,is_official) "+
							"Values ("+String.valueOf(lMaxCount)+",'"+student.getUserName()+"',15,CURRENT_TIMESTAMP(),'"+request.getRemoteAddr()
							+"',CURRENT_TIMESTAMP(),0,0,0,CURRENT_TIMESTAMP(),0,0,0,0,0,0,0,0,0,0,0,0,2,0) ";
					
					forumJdbcTemplate.execute(sql);
					
					//插入用户表jo_user
					sql = "insert into jo_user(user_id,username,cuid,password,register_time,register_ip,last_login_time,"+
							"login_count,activation,error_count) "+
							"Values ("+String.valueOf(lMaxCount)+",'"+student.getUserName()+"','"+student.getId()+"','"+student.getPassword()+"',CURRENT_TIMESTAMP(),'"+request.getRemoteAddr()
							+"',CURRENT_TIMESTAMP(),0,1,0) ";
					
					forumJdbcTemplate.execute(sql);		
				}
			}else{			
				//修改用户表jb_user
				sql = "update jb_user set username ='" + student.getUserName() +"' where user_id="+user_id;
				forumJdbcTemplate.execute(sql);	
				
				//修改用户表jo_user		
				sql="update jo_user set username='" + student.getUserName() +"',password='"+student.getPassword()+"',activation=1 where user_id="+user_id;					
				forumJdbcTemplate.execute(sql);			
			}
					
		} catch (Exception e) {
//			String aa = e.getMessage();
//			System.out.print(aa);
		}
		
					
		addMessage(redirectAttributes, "保存用户"+student.getUserName()+"成功");
		return "redirect:"+Global.getAdminPath()+"/cms/student/?repage";
	}

	@RequiresPermissions("cms:student:edit")
	@RequestMapping(value="delete")
	public String delete(String id,RedirectAttributes redirectAttributes){
		studentService.delete(id);	
		try {
			  String sql = "update jo_user set activation=0 where cuid ='"+id+"'";
			  forumJdbcTemplate.execute(sql);
		} catch (Exception e) {
//			String aa = e.getMessage();
//			System.out.print(aa);
		}
			
		addMessage(redirectAttributes, "删除用户成功");
		return"redirect:"+Global.getAdminPath()+"/cms/student/?repage";
	}
	
	/**
	 * 年级绑定班级
	 * @param gradeId
	 * @return
	 */
	@RequiresPermissions("cms:student:view")
	@RequestMapping(value = "searchclasses", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> searchclasses(String gradeId) {
		Grade grade = gradeService.get(gradeId);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		if (grade!=null){		
			List<Classes> classesList = classesService.findList(grade);
			for(Classes classes:classesList){
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", classes.getId().toString());
				map.put("className", classes.getClassName());
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 班级绑定班组
	 * @param sellerId
	 * @return
	 */
	@RequiresPermissions("cms:student:view")
	@RequestMapping(value = "searchclassGroup", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> searchclassGroup(String classesId) {
		Classes classes=classesService.get(classesId);
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		if (classes!=null){		
			List<ClassGroup> classGroupList = classGroupService.findList(classes);
			for(ClassGroup classGroup:classGroupList){
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", classGroup.getId().toString());
				map.put("groupName", classGroup.getGroupName());
				list.add(map);
			}
		}
		return list;
	}
	
}
