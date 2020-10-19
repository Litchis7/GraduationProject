package cqust.hjj.spring_boot_plc_draggable.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.ITeacherBiz;
import cqust.hjj.spring_boot_plc_draggable.model.Student;
import cqust.hjj.spring_boot_plc_draggable.model.Teacher;

@CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/Teacher")
public class TeacherController {
	
	@Autowired
	private ITeacherBiz biz;

	@RequestMapping(value = "/login")
	public ResponseEntity<?> loginTea(String tn,String tpwd) throws SQLException{
		Teacher teacher = new Teacher();
		Integer tn2 = Integer.valueOf(tn.toString());
		teacher.setTno(tn2);
		teacher.setTpassword(tpwd);
		//ITeacherBiz iTeacherBiz = new TeacherBizImpl();
		Teacher teacher2 = biz.loginTeacher(teacher);
		Map<String, Object> map = new HashMap<String,Object>();
		if (teacher2!=null) {
			map.put("code", 0);
			map.put("info", teacher2);
			
		}else {
			map.put("code", -1);
			map.put("message", "账号或者密码错误！");
		}
		return ResponseEntity.ok(map);
	}
	
	//文件下载
	@RequestMapping("/download")
	public ResponseEntity<?> download(HttpServletRequest request,HttpServletResponse response) {
		biz.downloadXlsx(response);
		return ResponseEntity.ok("success");
	}
	
	//插入单个学生
	@RequestMapping("/insert_one")
	public ResponseEntity<?> insertStu(String sno,String susername,String spassword) throws SQLException{
		
		Integer sno2 = Integer.valueOf(sno.toString());
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "插入单个学生成功！");
		biz.insertStudent(sno2, susername, spassword);
		return ResponseEntity.ok(map);
		
	}
	
	//查询所有学生
	@RequestMapping(value = "/queryStudent")
	public ResponseEntity<?> queryall() throws SQLException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Student> list = biz.queryStudent();
		map.put("data", list);
		return ResponseEntity.ok(map);
	}
	
	//xlsx表格上传
	 @RequestMapping("/impotusers")
	   public ResponseEntity<?> importusertexcel(@RequestParam("file") MultipartFile file) throws IOException, SQLException{


	       //定义list集合用来封装存储数据
	       List<Student> list = new ArrayList<Student>();
	       //关联excle表,创建excel操作对象
	       XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	       //拿到excel表中的sheet
	       XSSFSheet sheetAt = workbook.getSheetAt(0);
	       // 循环遍历sheet,拿到表中的每一行
	       for (Row row : sheetAt) {
	           //去掉标题行
	           if(row.getRowNum()==0){
	               continue;
	           }
	           //拿到每一行的所有单元格并封装成对象,注意拿到的都是Sting类型
	           //第一列的id不要
	           if(row.getCell(0)!=null)
	           {
	               row.getCell(0).setCellType(CellType.STRING);
	           }
	           String num= row.getCell(0).getStringCellValue();

	           if(row.getCell(1)!=null)
	           {
	               row.getCell(1).setCellType(CellType.STRING);
	           }
	           String name = row.getCell(1).getStringCellValue();
	           if(row.getCell(2)!=null)
	           {
	               row.getCell(2).setCellType(CellType.STRING);
	           }
	            String passwords=row.getCell(2).getStringCellValue();
	           System.out.println(" "+num+" "+name+" "+passwords);
	           //封装成对象
			  Student student2=new Student();
			  Integer sno = Integer.valueOf(num.toString());
			  student2.setSno(sno);
			  student2.setSusername(name);
			  student2.setSpassword(passwords);
	           //存储到集合
	            list.add(student2);
	       } 
	       Map<String, Object> map = new HashMap<String, Object>();
	       map.put("success", "导入xlsx文件成功！");
	       //插入数据
	       biz.insert(list);
	       
	       return ResponseEntity.ok(map);
	   }
	 	//删除
	 
	 @RequestMapping(value = "/delete")
		public ResponseEntity<?> delete(String sid) throws SQLException{
		 System.out.println("进入删除");
		 Map<String, Object> map = new HashMap<String, Object>();
		 Integer sid2 = Integer.valueOf(sid.toString());
		 biz.delete(sid2);
		 map.put("success", "删除成功！");
		 return ResponseEntity.ok(map);
		 
	 }
}
