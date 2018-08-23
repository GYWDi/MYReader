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
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
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
		request.setCharacterEncoding("utf-8");
        String name="";
        String password = "";
        name = request.getParameter("name");
        password = request.getParameter("pass");
        String usertablename="usertable";
        String result = "";
        boolean judge =false;
        Connection con = Oracconnect.getConnection();
        String userid=null;
        java.sql.Statement statement=null;
        try {
			statement = con.createStatement();
			String str = "select * from "+usertablename+" where username="+"'"+name+"'";
			ResultSet resultSet=null;
			resultSet = Oracconnect.getQuery(str,statement);
			while(resultSet.next()) {
				if(password.equals(resultSet.getString("password"))) {
					judge = true;
					userid = resultSet.getString("id");
				}
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
        HashMap<String, String> map = new HashMap<>();
        
        map.put("result", result);
        map.put("userid",userid);
        jsonObject.fluentPutAll(map);
        response.getWriter().print(jsonObject);
       // System.out.println(jsonObject);
        /*jsonObject.put("result", result);
        jsonObject.put("userid", userid);*/
      //System.out.println(jsonObject);
	}

}
