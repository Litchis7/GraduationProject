package cqust.hjj.spring_boot_plc_draggable.biz.interfaces;




import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cqust.hjj.spring_boot_plc_draggable.model.Student;
import cqust.hjj.spring_boot_plc_draggable.model.Teacher;

public interface ITeacherBiz {
	public Teacher loginTeacher(Teacher teacher) throws SQLException ;

	public void downloadXlsx(HttpServletResponse response);

	public void insertStudent(Integer sno,String susername,String spassword) throws SQLException;

	public List<Student> queryStudent() throws SQLException;
	
	public void insert(List<Student> list) throws SQLException;
	
	public void delete(Integer sid) throws SQLException;
	
}
