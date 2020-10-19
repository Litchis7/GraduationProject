package cqust.hjj.spring_boot_plc_draggable.biz.interfaces;




import java.sql.SQLException;

import cqust.hjj.spring_boot_plc_draggable.model.Student;


public interface IStudentBiz {
	public Student loginStudent(Student student) throws SQLException;
}
