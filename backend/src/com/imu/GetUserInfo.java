package com.imu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userid = request.getParameter("userid");
		
		Connection connection = Oracconnect.getConnection();
		HashMap<String, User> map = new HashMap<String, User>();
		java.sql.Statement statement=null;
		int sum=0;
		try {
			statement = connection.createStatement();
			String sql = "SELECT a.username username,b.* FROM usertable a,userinfo b WHERE (a.id=b.userid) and (a.id='"+userid+"')";
			ResultSet resultSet=null;
			resultSet = Oracconnect.getQuery(sql,statement);
			if(resultSet ==null){
			}
			while(resultSet.next()) {
				User user = new User();
				user.setUsername(resultSet.getString("username"));
				user.setAge(resultSet.getInt("age"));
				user.setBirth(resultSet.getDate("birth"));
				user.setUserid(resultSet.getInt("userid"));
				String count = null;
				count = sum+"";
				map.put(count, user);
			}
			
			JSONObject jsonObject = new JSONObject();
	        jsonObject.putAll(map);
	        response.getWriter().print(jsonObject);
	        System.out.println("jsonObject:"+jsonObject);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				System.out.println("数据库异常");
			e.printStackTrace();
		}
		
	}

}
