package com.imu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
        String name="";
        name = request.getParameter("name");
        String password = request.getParameter("pass");
        System.out.println(name+password);
        String usertablename="usertable";
        String result = "";
        boolean judge =false;
        Connection con = Oracconnect.getConnection();
        java.sql.Statement statement=null;
        try {
			statement = con.createStatement();
			String str = "select * from "+usertablename+" where username="+"'"+name+"'";
			String str2 = "insert into usertable(username,password) values("+"'"+name+"','"+password+"')";
			ResultSet resultSet=null;
			resultSet = Oracconnect.getQuery(str,statement);
			if(resultSet.next()) {
				
				judge = false;
				System.out.println("结果存在"+resultSet.getString("username"));
			}
			else {
				if(Oracconnect.getInsert(str2,statement)!=0) judge=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        //ģ�����ݿ��ȡ���ݲ��ж�
        if (judge) {
            
        	result="success";
        }else{
            result = "error";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        response.getWriter().print(jsonObject);
        System.out.println(jsonObject);
	}

}
