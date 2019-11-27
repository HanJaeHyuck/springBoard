package com.yydh.www.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yydh.www.vo.BoardVO;

public class BoardDAO {
	public static BoardDAO instance = new BoardDAO();

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

	// write column
	public int write(BoardVO data) {
		String sql = "INSERT INTO boards (title, content, writer, files) VALUES(?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTitle());
			pstmt.setString(2, data.getContent());
			pstmt.setString(3, data.getWriter());
			pstmt.setString(4, data.getFiles());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}

	// view column
	public BoardVO view(int id) {
		String sql = "SELECT * FROM boards WHERE id = ?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				BoardVO data = new BoardVO();
				data.setId(rs.getInt("id"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setWriter(rs.getString("writer"));
				data.setFiles(rs.getString("files"));
				System.out.println(data);
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

		return null;
	}

	// board column
	public List<BoardVO> getList(int page) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<BoardVO> list = new ArrayList<BoardVO>();

		try {
			String sql = "SELECT * FROM boards ORDER BY id DESC LIMIT ?, 10";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page - 1) * 10);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO temp = new BoardVO();
				temp.setId(rs.getInt("id"));
				temp.setTitle(rs.getString("title"));
				temp.setWriter(rs.getString("writer"));
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return list;

	}

	// delete column
	public int delete(int id) {
		String sql = "DELETE FROM boards WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int rs = pstmt.executeUpdate();

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

	}

	// selectboard modify column
	public BoardVO selectBoard(int id) {
			String sql = "SELECT id, title, content, writer, files FROM boards WHERE id =? ";
			Connection conn = null;
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					BoardVO data = new BoardVO();
					data.setId(rs.getInt("id"));
					data.setTitle(rs.getString("title"));
					data.setContent(rs.getString("content"));
					data.setWriter(rs.getString("writer"));
					data.setFiles(rs.getString("files"));
					
					return data;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
				try { if (rs != null) rs.close(); } catch (SQLException e) {}
				try { if (conn != null)	conn.close(); } catch (SQLException e) {}
			}
			return null;
		}

	public int modify(BoardVO data) {
		String sql = "UPDATE boards SET title = ? ,content = ?, files = ? WHERE id = ?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTitle());
			pstmt.setString(2, data.getContent());
			pstmt.setString(3, data.getFiles());
			pstmt.setInt(4, data.getId());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}
		
	}
}
