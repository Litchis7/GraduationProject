package cqust.hjj.spring_boot_plc_draggable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PagesPerMinute;

import org.springframework.stereotype.Repository;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.ITeacherDao;
import cqust.hjj.spring_boot_plc_draggable.dbconnpool.ConnectionPool;
import cqust.hjj.spring_boot_plc_draggable.model.Student;
import cqust.hjj.spring_boot_plc_draggable.model.Teacher;

@Repository
public class TeacherDaoImpl implements ITeacherDao{
	
	private ConnectionPool connectionpool;
	private Connection conn;
	
	@Override
	public Teacher loginTeacher(Teacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		
		conn = connectionpool.getConnection();
		if (conn!=null) {
			System.out.println("进入教师");
			String SQL = "SELECT * FROM Teacher WHERE tno = ? AND tpassword = ?";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, teacher.getTno());
			pm.setString(2, teacher.getTpassword());
			ResultSet rs = pm.executeQuery();
			Teacher teacher2;
			while (rs.next()) {
				teacher2 = new Teacher();
				teacher2.setTid(rs.getInt("tid"));
				teacher2.setTno(rs.getInt("tno"));
				teacher2.setTusername(rs.getString("tusername"));
				teacher2.setTpassword(rs.getString("tpassword"));
				return teacher2;
			}
		}
		
		
		return null;
		
	}

//	单个插入
	@Override
	public void insertStudent(Integer sno, String susername, String spassword) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			
			String SQL = "insert into Student(sno,susername,spassword) values (?,?,?)";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, sno);
			pm.setString(2, susername);
			pm.setString(3, spassword);
			pm.execute();
		}
		System.out.println("插入学生成功！");
	}

	//查询所有学生
	@Override
	public List<Student> queryall() throws SQLException {
		// TODO Auto-generated method stub
		List<Student> list = new ArrayList<Student>();
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "SELECT * FROM Student";
			PreparedStatement pm = conn.prepareStatement(SQL);
			ResultSet rs = pm.executeQuery();
			while (rs.next()) {
				Student student = new Student();
				student.setSid(rs.getInt("sid"));
				student.setSno(rs.getInt("sno"));
				student.setSusername(rs.getString("susername"));
				student.setSpassword(rs.getString("spassword"));
				list.add(student);
			}
		}
		return list;
	}
//导入
	@Override
	public void insert(List<Student> list) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "insert into Student(sno,susername,spassword) values(?,?,?)";
			PreparedStatement pm = null;
			for (int i = 0; i < list.size(); i++) {
				pm = conn.prepareStatement(SQL);
				pm.setInt(1, list.get(i).getSno());
				pm.setString(2, list.get(i).getSusername());
				pm.setString(3, list.get(i).getSpassword());
				pm.execute();
			}
			
		}
	}

	//删除
	@Override
	public void delete(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "DELETE FROM Student WHERE sid = ?";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, sid);
			pm.execute();
		}
		System.out.println("删除学生成功！");
	}
	

}
