package cqust.hjj.spring_boot_plc_draggable.biz.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.ITeacherBiz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.TeacherDaoImpl;
import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.ITeacherDao;
import cqust.hjj.spring_boot_plc_draggable.model.Student;
import cqust.hjj.spring_boot_plc_draggable.model.Teacher;

@Service
public class TeacherBizImpl implements ITeacherBiz {

	@Override
	public Teacher loginTeacher(Teacher teacher) throws SQLException {
		// TODO Auto-generated method stub
		ITeacherDao iTeacherDao = new TeacherDaoImpl();
		Teacher teacher2 = iTeacherDao.loginTeacher(teacher);
		return teacher2;
	}

	@Override
	public void downloadXlsx(HttpServletResponse response) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			File file = ResourceUtils.getFile("classpath:student.xlsx");
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开            
				response.addHeader("Content-Disposition", "attachment;fileName=" + "studentinfo.xlsx");
				byte[] buffer = new byte[1024];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream outputStream = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					outputStream.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			}else {
				System.out.print("文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	//插入单个学生
	@Override
	public void insertStudent(Integer sno, String susername, String spassword) throws SQLException {
		// TODO Auto-generated method stub
		ITeacherDao iTeacherDao = new TeacherDaoImpl();
		iTeacherDao.insertStudent(sno, susername, spassword);
	}
	//查询所有学生
	@Override
	public List<Student> queryStudent() throws SQLException {
		// TODO Auto-generated method stub
		ITeacherDao iTeacherDao = new TeacherDaoImpl();
		List<Student> list = iTeacherDao.queryall();
		return list;
	}
	//批量插入
	@Override
	public void insert(List<Student> list) throws SQLException {
		// TODO Auto-generated method stub
		
		ITeacherDao iTeacherDao = new TeacherDaoImpl();
		iTeacherDao.insert(list);
	}
	//删除
	@Override
	public void delete(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		ITeacherDao iTeacherDao = new TeacherDaoImpl();
		iTeacherDao.delete(sid);
	}
	

}
