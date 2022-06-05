package loginRegistration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail= request.getParameter("username");
		String upwd = request.getParameter("password");
		Connection con=null;
		RequestDispatcher dispatcher = null;
		HttpSession session=request.getSession();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false","root","12345");
			PreparedStatement pst=con.prepareStatement("select *from users where uemail =? and upwd=?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
			
			ResultSet rs= pst.executeQuery();
			if(rs.next()) {
				session.setAttribute("name",rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status","failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
