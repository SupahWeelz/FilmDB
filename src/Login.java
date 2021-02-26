
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
 * User Login:
 * 	The request will contain two fields: e-mail address and password.
 * 	The program will check if the e-mail address is registered.
 * 		If so, then the program will check if the given password matches the password stored in the database.
 * 			If so, go to home page.
 * 			Otherwise, tell the user.
 * 		Otherwise, tell the user.
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String password = request.getParameter("password");
		
		String[] sesVar = Functions.getSessionInfo();
		if(sesVar.length != 0){
			response.sendRedirect("http://18.221.171.83/FilmDB/home.html");
		}
		
		String DB_URL = "jdbc:mysql://localhost:3306/filmdb";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		String result = docType+"<html>\n"+
				 "<head><title>User Login</title>\n<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n</head>\n"+
				 "<body>\n<nav class=\"navbar navbar-inverse\">\n"+
				 	"<div class=\"container-fluid\">\n"+
				 		"<div class=\"navbar-header\">\n"+
				 			"<a class=\"navbar-brand\" href=\"#\">FilmDB</a>\n"+
				 		"</div>"+
				 		"<ul class=\"nav navbar-nav\">\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/home.html\">Home</a></li>\n"+
				 		"<li class=\"active\"><a href=\"http://18.221.171.83/FilmDB/login.html\">Login</a></li>\n"+
				 		"<li><a href=\"Logout\">Logout</a></li>\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/register.html\">Register</a></li>\n"+
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
			 // Check if e-mail is registered.
			 sql = "SELECT email FROM users WHERE email=\""+email+"\"";
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 // If so, get password
			 if(rs.next()){
				sql = "SELECT password,username,id FROM users WHERE email=\""+email+"\"";
				rs = (ResultSet) stmt.executeQuery(sql);
				// If passwords match, login successful
				if(rs.next()){
					if(rs.getString("password").equals(password)){
						
						Functions.setSessionInfo(Integer.toString(rs.getInt("id")),rs.getString("username"));
						result+="<p> Hello, "+rs.getString("username")+". You have successfully logged in!</p>";
						//response.sendRedirect("");
					}
					// Otherwise, tell user
					else{
						result+="<p> Password incorrect!</p>";
					}
				}
			 }
			 // Otherwise, tell user that there is no account with that email
			 else{
				 result+="<p> There is no account with that email address.</p>";
			 }
			 out.println(result+
					 "</div></body></html>");
			 conn.close();
			 stmt.close();
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println(result+
					 "<p> Login error!</p>"+
					 "</div></body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
