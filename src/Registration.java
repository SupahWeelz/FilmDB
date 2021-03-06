import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * User registration: 
 * 	The request will contain 3 fields: an e-mail address, a username, and a password.
 * 	The program will check if the e-mail address is already in use.
 * 		If so, tell the user.
 * 		Otherwise, create the new entry in the database table and tell the user.
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8"); 
		final PrintWriter out= response.getWriter();
		String docType= "<!doctypehtml>\n";
		
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String DB_URL = "jdbc:mysql://localhost:3306/filmdb";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		String result = docType+"<html>\n"+
				 "<head><title>User Registration</title>\n<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n</head>\n"+
				 "<body>\n<nav class=\"navbar navbar-inverse\">\n"+
				 	"<div class=\"container-fluid\">\n"+
				 		"<div class=\"navbar-header\">\n"+
				 			"<a class=\"navbar-brand\" href=\"#\">FilmDB</a>\n"+
				 		"</div>"+
				 		"<ul class=\"nav navbar-nav\">\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/home.html\">Home</a></li>\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/login.html\">Login</a></li>\n"+
				 		"<li><a href=\"Logout\">Logout</a></li>\n"+
				 		"<li class=\"active\"><a href=\"http://18.221.171.83/FilmDB/register.html\">Register</a></li>\n"+
				 		"<li><a href=\"Favorites\">Favorites</a></li>\n"+
				 		"<li><a href=\"TopTen\">Top Films</a></li>"+
				 		"<li><a href=\"CurrentlyShowing\">Currently Showing</a></li>"+
				 		"</ul>\n"+
				 	"</div>\n"+
				 	"</nav>\n<div class=\"container-fluid\">\n";
		
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 sql = "SELECT email FROM users WHERE email=\""+email+"\"";
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 if(rs.next()){
				 // Tell user that email is already in use
				 out.println(result+
						 "<p> That e-mail address is already in use!</p>"+
						 "</body></html>");
			 }
			 else{
				 sql = "INSERT INTO users (email, username, password) VALUES (\""+email+"\",\""+username+"\",\""+password+"\")";
				 stmt.executeUpdate(sql);
				 out.println(result+
						 "<p> Your account has been created successfully! Please login to continue.</p>"+
						 "</body></html>");
			 }
			 conn.close();
			 stmt.close();
		 
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println(result+
					 "<p> An error has occurred.</p>"+
					 "</body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
