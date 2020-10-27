package kr.co.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.co.DTO.BDto;

public class BDao
{
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public BDao()
	{
		try
		{
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
			con = ds.getConnection();

			System.out.println("연결되었습니다.");
		} catch (Exception e)
		{
			System.out.println("연결에 실패하였습니다.");
			e.printStackTrace();
		}
	}
	
	
	// 글목록
	public ArrayList<BDto> boardList()
	{
		ArrayList<BDto> boardList = new ArrayList<BDto>();
		
		String sql = "SELECT * FROM MVC_BOARD ORDER BY BGROUP DESC, BINDENT ASC";
		try
		{
			pstmt = con.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				BDto dto = new BDto();
				dto.setbId(rs.getInt("BID"));
				dto.setbName(rs.getString("BNAME"));
				dto.setbTitle(rs.getString("BTITLE"));
				dto.setbContent(rs.getString("BCONTENT"));
				dto.setbDate(rs.getTimestamp("BDATE"));
				dto.setbHit(rs.getInt("BHIT"));
				dto.setbGroup(rs.getInt("BGROUP"));
				dto.setbStep(rs.getInt("BSTEP"));
				dto.setbIndent(rs.getInt("BINDENT"));
				
				boardList.add(dto);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return boardList;
	}

	// 글쓰기 등록
	public void write(String bName, String bTitle, String bContent)
	{
		String sql = "INSERT INTO MVC_BOARD (BID, BNAME, BTITLE, BCONTENT, BHIT, BGROUP, BSTEP, BINDENT) VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, MVC_BOARD_SEQ.CURRVAL, 0, 0)";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.executeUpdate();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	// 조회수
	public void hit(int bId, int bHit)
	{
		String sql = "UPDATE MVC_BOARD SET BHIT = ?+1 WHERE BID = ?";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bHit);
			pstmt.setInt(2, bId);
			pstmt.executeUpdate();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 스탭
	public void step(int bId, int bStep)
	{
		String sql = "UPDATE MVC_BOARD SET BSTEP = ?+1 WHERE BID = ?";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bStep);
			pstmt.setInt(2, bId);
			pstmt.executeUpdate();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

	// 해당게시글 보기
	public BDto contentView(int bId)
	{
		BDto dto = new BDto();
		String sql = "SELECT * FROM MVC_BOARD WHERE BID = ?";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				dto.setbId(rs.getInt("BID"));
				dto.setbName(rs.getString("BNAME"));
				dto.setbTitle(rs.getString("BTITLE"));
				dto.setbContent(rs.getString("BCONTENT"));
				dto.setbDate(rs.getTimestamp("BDATE"));
				dto.setbHit(rs.getInt("BHIT"));
				dto.setbGroup(rs.getInt("BGROUP"));
				dto.setbStep(rs.getInt("BSTEP"));
				dto.setbIndent(rs.getInt("BINDENT"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dto;
	}

	// 게시글 수정
	public void modify(int bId, String bName, String bTitle, String bContent)
	{
		String sql = "UPDATE MVC_BOARD SET BNAME=?, BTITLE=?, BCONTENT=? WHERE BID = ?";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, bId);
			pstmt.executeUpdate();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		

	}

	// 글 삭제
	public void delete(int bId)
	{
		String sql = "DELETE FROM MVC_BOARD WHERE BID = ?";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.executeUpdate();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 댓글 작업
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent)
	{
		String sql = "INSERT INTO MVC_BOARD (BID, BNAME, BTITLE, BCONTENT, BHIT, BGROUP, BSTEP, BINDENT) VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, 0, ?, ?, ?)";
		
		System.out.println(bId);
		try
		{
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, bGroup);
			pstmt.setInt(5, bStep);
			pstmt.setInt(6, bIndent+1);
			
			pstmt.executeUpdate();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 해당 댓글 조회
	public BDto reply_view(int bId)
	{
		BDto dto = new BDto();
		
		String sql = "SELECT * FROM MVC_BOARD WHERE BID = ?";
		
		try
		{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				dto.setbId(rs.getInt("BID"));
				dto.setbName(rs.getString("BNAME"));
				dto.setbTitle(rs.getString("BTITLE"));
				dto.setbContent(rs.getString("BCONTENT"));
				dto.setbDate(rs.getTimestamp("BDATE"));
				dto.setbHit(rs.getInt("BHIT"));
				dto.setbGroup(rs.getInt("BGROUP"));
				dto.setbStep(rs.getInt("BSTEP"));
				dto.setbIndent(rs.getInt("BINDENT"));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return dto;
	}

}
