package com.whir.ht.cms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.whir.ht.cms.entity.Classes;
import com.whir.ht.cms.entity.FrontUser;
import com.whir.ht.cms.entity.Grade;
import com.whir.ht.cms.entity.Teacher;
import com.whir.ht.cms.service.ClassesService;
import com.whir.ht.cms.service.GradeService;
import com.whir.ht.cms.service.TeacherService;
import com.whir.ht.cms.service.UserService;
import com.whir.ht.common.config.Global;
import com.whir.ht.common.persistence.Page;
import com.whir.ht.common.utils.StringUtils;
import com.whir.ht.common.web.BaseController;

/**
 * 教师Controller
 * @author wuxiaoyuan
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/teacher")
public class TeacherController extends BaseController {
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private GradeService gradeService;
	
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
	@RequiresPermissions("cms:teacher:edit")
	@RequestMapping(value = "checkuserName")
	public String checkLoginName(String olduserName, String userName) {
		if (userName !=null && userName.equals(olduserName)) {
			return "true";
		} else if (userName !=null && teacherService.getName(userName) == null) {
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
	@RequiresPermissions("cms:teacher:edit")
	@RequestMapping(value = "checkuserEmail")
	public String checkEmail(String oldEmail, String email) {
		if (email !=null && email.equals(oldEmail)) {
			return "true";
		} else if (email !=null && teacherService.getEmail(email) == null) {
			return "true";
		}
		return "false";
	}
	
	
	@ModelAttribute
	public Teacher get(@RequestParam(required = false)String id){
		if(StringUtils.isNotBlank(id)){
			return teacherService.getTeacher(id);
			
		}else{
			return new  Teacher();
		}
	}
	
	@RequiresPermissions("cms:teacher:view")
	@RequestMapping(value = {"list", ""})
	public String list(Teacher teacher, HttpServletRequest request, HttpServletResponse response, Model model){
		
		Page<Teacher> page = teacherService.findTeacher(new Page<Teacher>(request,response),teacher);
		model.addAttribute("page",page);
		return "cms/teacherList";
	}
	
	@RequiresPermissions("cms:teacher:view")
	@RequestMapping(value="form")
	public String form(Teacher teacher,Model model){
		String teacherid = teacher.getId();
		List<Classes> classesList = classesService.getClasses();
		if(StringUtils.isNotEmpty(teacherid)){
			List<Classes> list=classesService.findByClassesId(teacherid);
			StringBuilder classIds = new StringBuilder();
			for(Classes classes:list){
				String classId=classes.getId();
				classIds.append(classId);
				classIds.append(",");
			}
			model.addAttribute("classIds", classIds.toString());
			model.addAttribute("list", list);
		}
		
		model.addAttribute("classesList", classesList);
		model.addAttribute("teacher",teacher);
		return"cms/teacherForm";
	}
	
	@RequiresPermissions("cms:teacher:edit")
	@RequestMapping(value="save")
	public String save(Teacher teacher,String userName,String newPassword,String gradeIds,String classesIds,HttpServletRequest request, Model model,RedirectAttributes redirectAttributes){
		 Teacher pteacher  = null;
		 if(StringUtils.isNotBlank(teacher.getId())){
		 pteacher = teacherService.getTeacher(teacher.getId());
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
				if(teacher.getId()!= null && !teacher.getId().equals("")){
					teacher = teacherService.getTeacher(teacher.getId());
					String teacherid = teacher.getId();
					List<Classes> classesList = classesService.getClasses();
					if(StringUtils.isNotEmpty(teacherid)){
						List<Classes> list=classesService.findByClassesId(teacherid);
						StringBuilder classIds = new StringBuilder();
						for(Classes classes:list){
							String classId=classes.getId();
							classIds.append(classId);
							classIds.append(",");
						}
						model.addAttribute("classIds", classIds.toString());
						model.addAttribute("list", list);
					}
					model.addAttribute("classesList", classesList);
					model.addAttribute("teacher",teacher);
					return"cms/teacherForm";
				}else{
					return"cms/teacherForm";
				}
				
			}else{
				teacher.setPassword(DigestUtils.md5Hex(newPassword));
			}
		}else{
			teacher.setPassword(pteacher.getPassword());
		}

		Set<Grade> grades = new HashSet<Grade>();
	/*	if (gradeIds != null){
			String[] ids = StringUtils.split(gradeIds, ",");
			for (String gradeId : ids) {
				Grade grade = new Grade();
				grade.setId(gradeId);
				grades.add(grade);
			}
		}*/
		
		Set<Classes> classess = new HashSet<Classes>();
		if (classesIds != null){
			String[] ids = StringUtils.split(classesIds, ",");
			for (String classesId : ids) {
				Classes classes = new Classes();
				classes.setId(classesId);
				List<Classes> classesList = classesService.findList(classesId);
				for(Classes classe:classesList){
					Grade grade=classe.getGrade();
					grades.add(grade);
				}
				classess.add(classes);
			}
		}
		
		teacher.setGrades(grades);
		teacher.setClassess(classess);
		teacherService.save(teacher);
		
		Boolean isAdd=false;
		String sql="";
		String user_id="";
		sql = "select user_id from jo_user where cuid='"+teacher.getId()+"' limit 0,1";
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
							"Values ("+String.valueOf(lMaxCount)+",'"+teacher.getUserName()+"',16,CURRENT_TIMESTAMP(),'"+request.getRemoteAddr()
							+"',CURRENT_TIMESTAMP(),0,0,0,CURRENT_TIMESTAMP(),0,0,0,0,0,0,0,0,0,0,0,0,2,0) ";
					
					forumJdbcTemplate.execute(sql);
					
					//插入用户表jo_user
					sql = "insert into jo_user(user_id,username,cuid,password,register_time,register_ip,last_login_time,"+
							"login_count,activation,error_count) "+
							"Values ("+String.valueOf(lMaxCount)+",'"+teacher.getUserName()+"','"+teacher.getId()+"','"+teacher.getPassword()+"',CURRENT_TIMESTAMP(),'"+request.getRemoteAddr()
							+"',CURRENT_TIMESTAMP(),0,1,0) ";
					
					forumJdbcTemplate.execute(sql);		
				}
			}else{			
				//修改用户表jb_user
				sql = "update jb_user set username ='" + teacher.getUserName() +"' where user_id="+user_id;
				forumJdbcTemplate.execute(sql);	
				
				//修改用户表jo_user
				sql="update jo_user set username='" + teacher.getUserName() +"',password='"+teacher.getPassword()+"',activation=1 where user_id="+user_id;	
				forumJdbcTemplate.execute(sql);			
			}
					
		} catch (Exception e) {
//			String aa = e.getMessage();
//			System.out.print(aa);
		}
		addMessage(redirectAttributes, "保存用户"+teacher.getUserName()+"成功");
		return "redirect:"+Global.getAdminPath()+"/cms/teacher/?repage";
	}

	@RequiresPermissions("cms:teacher:edit")
	@RequestMapping(value="delete")
	public String delete(String id,RedirectAttributes redirectAttributes){
		teacherService.delete(id);
		try {
			  String sql = "update jo_user set activation=0 where cuid ='"+id+"'";
			  forumJdbcTemplate.execute(sql);
		} catch (Exception e) {
//			String aa = e.getMessage();
//			System.out.print(aa);
		}
		addMessage(redirectAttributes, "删除用户成功");
		return"redirect:"+Global.getAdminPath()+"/cms/teacher/?repage";
	}
	
	/**
	 * 年级绑定班级
	 * @param gradeId
	 * @return
	 */
	@RequiresPermissions("cms:teacher:view")
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
	
}
