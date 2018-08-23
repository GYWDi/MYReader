package com.imu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class ReturnBookContext
 */
@WebServlet("/ReturnBookContext")
public class ReturnBookContext extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnBookContext() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String bookid = request.getParameter("bookid");
		String chapterno = request.getParameter("chapterno");
		Connection con = Oracconnect.getConnection();
		String sql = "SELECT CHAPTERNO,CONTENT from CHAPTER WHERE bookid='"+bookid+"' and chapterno='"+chapterno+"'";
		try {
			Statement statement=con.createStatement();
			ResultSet result = null;
			result = Oracconnect.getQuery(sql, statement);
			HashMap<String, String> map = new HashMap<>();
			JSONObject jsonObject  =new JSONObject();
			while(result.next()) {
			
				map.put(result.getString("chapterno"), result.getString("content"));
				
			}
			jsonObject.putAll(map);
			response.getWriter().print(jsonObject);
	        System.out.println("jsonObject:"+jsonObject);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
