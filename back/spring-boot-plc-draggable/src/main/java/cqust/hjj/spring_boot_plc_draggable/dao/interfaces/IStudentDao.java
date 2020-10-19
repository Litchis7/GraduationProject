package cqust.hjj.spring_boot_plc_draggable.dao.interfaces;

import java.sql.SQLException;


import cqust.hjj.spring_boot_plc_draggable.model.Student;

public interface IStudentDao {
	public Student loginStudent(Student student) throws SQLException;
}
