package cqust.hjj.spring_boot_plc_draggable.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import cqust.hjj.spring_boot_plc_draggable.model.Student;
import cqust.hjj.spring_boot_plc_draggable.model.Teacher;

public interface ITeacherDao {
	public Teacher loginTeacher(Teacher teacher) throws SQLException;
	public void insertStudent(Integer sno,String susername,String spassword) throws SQLException;
	public List<Student> queryall() throws SQLException;
	public void insert(List<Student> list) throws SQLException;
	public void delete(Integer sid) throws SQLException;
}
