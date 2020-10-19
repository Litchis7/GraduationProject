package cqust.hjj.spring_boot_plc_draggable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IPlcDao;
import cqust.hjj.spring_boot_plc_draggable.dbconnpool.ConnectionPool;
import cqust.hjj.spring_boot_plc_draggable.model.PLC;


@Repository
public class PlcDaoImpl implements IPlcDao{
	
	private ConnectionPool connectionpool;
	private Connection conn;
	

	@Override
	public void insert(String content,Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			System.out.println("连接数据库成功");
			String SQL = "insert into plc(content,sid) values(?,?)";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setString(1, content);
			pm.setInt(2, sid);
			pm.execute();
		}
		System.out.println("插入数据成功");
	}

	@Override
	public List<PLC> query(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		List<PLC> list = new ArrayList<PLC>();
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "SELECT content FROM plc WHERE sid = ? ORDER BY id DESC LIMIT 1";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, sid);
			ResultSet rs = pm.executeQuery();
			while (rs.next()) {
				PLC content = new PLC();
				content.setContent(rs.getString("content"));
				list.add(content);
			}
		}
		
		return list;
	}

}
