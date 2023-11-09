package admin;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp2.PStmtKey;

/**
 * Servlet implementation class manageServlet
 */
@WebServlet("/manageServlet")
public class manageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public manageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//This Bussiness code is used to Edit the Person Details
		
		String user_id=request.getParameter("user_id");
		String user_name=request.getParameter("user_name");
		String role_name=request.getParameter("role_name");
		String dept=request.getParameter("dept");
		String rollno=request.getParameter("rollno");
//		int status=1;	
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3307/mentoringsystem","root","");
			Statement stmt=conn.createStatement();
			PreparedStatement pstm=conn.prepareStatement("update student_master set name=?,role=?,dept=?,rollno=? where email=?");
			pstm.setString(1,user_name);
			pstm.setString(2,role_name);
			pstm.setString(3,dept);
			pstm.setString(4,rollno);
			pstm.setString(5,user_id.trim());
			pstm.execute();
			
			PrintWriter out=response.getWriter();
			System.out.print("data inserted successfully");
			response.sendRedirect("Stlist.jsp");
			conn.close();
		}catch(Exception e) {
			response.getWriter().append(e.toString()).append(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//This Bussiness code is used to compare the button value from the list of the Students's
		
		String btn=request.getParameter("btn");
		String user_id=request.getParameter("user_id");
//		HttpSession session=request.getSession();
//		session.getAttribute("user_id");
		PrintWriter out=response.getWriter();
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3307/mentoringsystem","root","");
			Statement st=con.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(btn.equals("delete")) {
				try {
					String query="delete from student_master where email=?";
					PreparedStatement pst=con.prepareStatement(query);
					pst.setString(1, user_id);
					pst.execute();
					response.sendRedirect("admin.jsp");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(btn.equals("edit")) {
			try {
				String query="select * from student_master where email=?";
				PreparedStatement pst1=con.prepareStatement(query);
				pst1.setString(1, user_id);
				ResultSet rs1=pst1.executeQuery();
				if(rs1.next()) {
				response.sendRedirect("Stedit.jsp?user_id="+rs1.getString("email")+"&user_name="+rs1.getString("name")+"&role_name="+rs1.getString("role")+"&dept="+rs1.getString("dept")+"&rollno="+rs1.getString("rollno"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			response.sendRedirect("edit.jsp");
//			out.print("edit the code");
		}
		
		
		
	}

}
