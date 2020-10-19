package cqust.hjj.spring_boot_plc_draggable.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cqust.hjj.spring_boot_plc_draggable.biz.impl.StudentBizImpl;
import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IStudentBiz;
import cqust.hjj.spring_boot_plc_draggable.model.Student;

@CrossOrigin(origins = "*", maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/Student")
public class StudentController {

	public static Gson gson = new Gson();

	@RequestMapping(value = "/login")
	public ResponseEntity<?> loginStu(String sn, String spwd, HttpSession session)
			throws SQLException {

		Student student = new Student();
		Integer sn2 = Integer.valueOf(sn.toString());
		IStudentBiz iStudentBiz = new StudentBizImpl();
		student.setSno(sn2);
		student.setSpassword(spwd);
		Student student2 = iStudentBiz.loginStudent(student);

		System.out.println(student2);
		Map<String, Object> map = new HashMap<String, Object>();

		if (student2 != null) {
			session.setAttribute("student", student2);
			map.put("code", 0);
			map.put("logininf", "登录成功！");
			map.put("info", student2);
			// Cookie cookie = new Cookie("student", gson.toJson(student2));
//			cookie = new HttpCookie("student", gson.toJson(student2));
//			cookie.setValue("张三");
			// cookie.setValue(gson.toJson(student2));
		} else {
			map.put("code", -1);
			map.put("logininf", "账号或者密码错误！");
		}
		return ResponseEntity.ok(map);

	}

	@RequestMapping("/getStudent")
	public Map<String, Object> getStudent(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Student student = (Student)session.getAttribute("student");
		if (student != null) {
			map.put("code", 0);
			map.put("data", student);
		} else {
			map.put("code", -1);
			map.put("data", "超时，请重新登录");
		}
		return map;
	}
}
