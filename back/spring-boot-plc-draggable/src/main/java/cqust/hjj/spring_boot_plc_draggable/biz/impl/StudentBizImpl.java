package cqust.hjj.spring_boot_plc_draggable.biz.impl;



import java.sql.SQLException;

import org.springframework.stereotype.Service;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IStudentBiz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.StudentDaoImpl;
import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IStudentDao;
import cqust.hjj.spring_boot_plc_draggable.model.Student;

@Service
public class StudentBizImpl implements IStudentBiz{

	@Override
	public Student loginStudent(Student student) throws SQLException {
		// TODO Auto-generated method stub
		IStudentDao iStudentDao = new StudentDaoImpl();
		Student student2 = iStudentDao.loginStudent(student);
		return student2;
	}


	

}
