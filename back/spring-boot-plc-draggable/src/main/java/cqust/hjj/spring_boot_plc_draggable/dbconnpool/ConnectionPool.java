package cqust.hjj.spring_boot_plc_draggable.dbconnpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.stereotype.Service;

@Service
public class ConnectionPool {
	private static final String DB_URL ="jdbc:sqlite:C:/Users/Revoemag/Desktop/Project_Graduation/db/plc.db";
	private static final String DRIVER ="org.sqlite.JDBC";
	private static LinkedList<Connection> connPool = new LinkedList<Connection>();
	static {
		try {
			init(10);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException{
		for(int index = 0; index <connPool.size();index++)
		{
			Connection conn = connPool.get(index);
			if (conn.isValid(2000)) {
				return conn;
			}
		}
		return null;
	}
	
//	public ConnectionPool(int poolSize) throws ClassNotFoundException, SQLException {
//		init(poolSize);
//	}
	
	private static void init(int poolSize) throws ClassNotFoundException,SQLException{
		Connection conn = null;
		Class.forName(DRIVER);
		for(int i = 0; i< poolSize;i++)
		{
			conn = DriverManager.getConnection(DB_URL);
			connPool.add(conn);
		}
	}
	
	
//	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		//插入数据
//		ConnectionPool connectionPool = new ConnectionPool(10);
//		Connection conn = connectionPool.getConnection();
//		for (Connection connection : connPool) {
//			System.out.println(connection.toString());
//		}
//		
//		if (conn!=null) {
//		System.out.println("连接成功");
//		String SQL = "insert into test(zifu) values(?)";
//		PreparedStatement pm = conn.prepareStatement(SQL);
//		//pm.setDouble(1, 20.2);
//		pm.setString(1, "组件拖拽");
//		pm.execute();
//	}
//	}

}
