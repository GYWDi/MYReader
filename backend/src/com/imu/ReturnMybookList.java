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
 * Servlet implementation class ReturnMybookList
 */
@WebServlet("/ReturnMybookList")
public class ReturnMybookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnMybookList() {
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
		HashMap<String, Book> map = new HashMap<String,Book>();
		String userid = request.getParameter("userid");
		response.setContentType("text/html;charset=utf-8");
		Connection con = Oracconnect.getConnection();
        java.sql.Statement statement=null;
        try {
			statement = con.createStatement();
			String str = "select b.* from COLLECTION a,BOOKLIST b WHERE (a.bookid=b.id)and a.userid='"+userid+"' order by a.id";
			ResultSet resultSet=null;
			resultSet = Oracconnect.getQuery(str,statement);
			int sum=1;
			while(resultSet.next()) {
				Book book = new Book();
				book.setBookname(resultSet.getString("bookname"));
				book.setWriter(resultSet.getString("writer"));
				book.setId(resultSet.getString("id"));
				book.setISBN(resultSet.getString("ISBN"));
				book.setPublish(resultSet.getString("publish"));
				book.setPublishdate(resultSet.getDate("publishtime"));
				String sumString = sum+"";
				map.put(sumString, book);
				sum++;
				System.out.println("booktostring:"+book.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        response.getWriter().print(jsonObject);
        System.out.println("jsonObject:"+jsonObject);
        /*try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
