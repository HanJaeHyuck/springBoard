package com.yydh.www.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yydh.www.vo.UserVO;

public class MemberDAO {
	public static MemberDAO instance = new MemberDAO();
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String cString = "jdbc:mysql://gondr.asuscomm.com/yy_20122?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
			String id = "yy_20122";
			String password = "han1232";
			conn = DriverManager.getConnection(cString, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB Connection Failed");
		}
		return conn;
	}
	



	//register column
	public boolean register(String name, String id, String password) {
		boolean result = false;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "INSERT INTO users(id, name, password) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, password);

			int n = pstmt.executeUpdate();
			
			if (n > 0) result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}
		return result;
	}

	//login column
	public UserVO login(String id, String password) {
		boolean result = false;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "SELECT * FROM users WHERE id = ? AND password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				return null;
			} else {
				UserVO user = new UserVO();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null) conn.close(); } catch (SQLException e) {}
		}
		return null;
	}


}
