package com.example.loginregister;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Check_login
 */
@WebServlet("/Check_login")
public class Check_login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check_login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dac?serverTimezone=UTC","root","");
			PreparedStatement ps=con.prepareStatement("select * from `loginregister-assignment` where username=(?) and pass=(?)");
		
			ps.setString(1, request.getParameter("username"));
			ps.setString(2, request.getParameter("pass"));
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				//request.getSession().setAttribute("UNAME",rs.getString(2));
				request.getSession().setAttribute("CHK_SESSION", "valid");
				PrintWriter pr=response.getWriter();
				pr.print(rs.getString(2));
				request.setAttribute("UNAME", rs.getString(2));
				
				request.getRequestDispatcher("/home.jsp").forward(request, response);
				//response.sendRedirect("home.jsp");
			}else {
			request.setAttribute("IS_WRONG", true);
			request.getRequestDispatcher("/index.html").forward(request, response);
			}
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
