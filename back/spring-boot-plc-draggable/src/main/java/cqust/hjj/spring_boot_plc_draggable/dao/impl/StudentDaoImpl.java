package cqust.hjj.spring_boot_plc_draggable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IStudentDao;
import cqust.hjj.spring_boot_plc_draggable.dbconnpool.ConnectionPool;
import cqust.hjj.spring_boot_plc_draggable.model.Student;

@Repository
public class StudentDaoImpl implements IStudentDao{

	private ConnectionPool connectionpool;
	private Connection conn;
	
	@Override
	public Student loginStudent(Student student) throws SQLException {
		// TODO Auto-generated method stub
		
		conn = connectionpool.getConnection();
		
		if (conn!=null) {
			System.out.println("进入学生");
			String SQL = "SELECT * FROM Student WHERE sno = ? AND spassword = ?";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, student.getSno());
			pm.setString(2, student.getSpassword());
			ResultSet rs = pm.executeQuery();
			Student student2;
			while(rs.next()) {
				student2=new Student();
				student2.setSid(rs.getInt("sid"));
				student2.setSno(rs.getInt("sno"));
				student2.setSusername(rs.getString("susername"));
				student2.setSpassword(rs.getString("spassword"));
				return student2;
			}
			
		}
		return null;
	}

}
