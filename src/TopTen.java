

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
 * Top Ten:
 * 	Lists up to 10 of the highest rated movies stored in database
 */
@WebServlet("/TopTen")
public class TopTen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopTen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8"); 
		final PrintWriter out= response.getWriter();
		String docType= "<!doctypehtml>\n";
		
		String DB_URL = "jdbc:mysql://localhost:3306/filmdb";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		int place = 1;
		
		String result = docType+"<html>\n"+
				 "<head><title>Top Ten</title>\n<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n</head>\n"+
				 "<body>\n<nav class=\"navbar navbar-inverse\">\n"+
				 	"<div class=\"container-fluid\">\n"+
				 		"<div class=\"navbar-header\">\n"+
				 			"<a class=\"navbar-brand\" href=\"#\">FilmDB</a>\n"+
				 		"</div>"+
				 		"<ul class=\"nav navbar-nav\">\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/home.html\">Home</a></li>\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/login.html\">Login</a></li>\n"+
				 		"<li><a href=\"Logout\">Logout</a></li>\n"+
				 		"<li><a href=\"http://18.221.171.83/FilmDB/register.html\">Register</a></li>\n"+
				 		"<li><a href=\"Favorites\">Favorites</a></li>\n"+
				 		"<li class=\"active\"><a href=\"TopTen\">Top Films</a></li>"+
				 		"<li><a href=\"CurrentlyShowing\">Currently Showing</a></li>"+
				 		"</ul>\n"+
				 	"</div>\n"+
				 	"</nav><div class=\"container\">\n<table class=\"table\">\n<thead><tr>\n<th></th><th>Movie</th>\n<th>Rating</th>\n</tr>\n</thead>\n<tbody>\n";

		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 
			 sql="SELECT id,name, rating FROM movies ORDER BY rating DESC LIMIT 10";
			 
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 
			 			 
			 while(rs.next()){
				 String temp1 = "<tr>\n<td>"+place+"</td>\n<td>"+rs.getString("name")+"</td>\n<td> "+rs.getFloat("rating")+
						 "</td>\n<td><form action=\"Summary\" method=\"post\"><input type=\"hidden\" name=\"movie\" value="+rs.getInt("id")+"></input><button type=\"submit\">Show Summary</button></form></td></tr>\n";
				 result+=temp1;
				 place++;
			 }
			 result+="</tbody></table>\n</div></body>\n</html>";
			 out.println(result);
			 conn.close();
			 stmt.close();
			 
		}catch(ClassNotFoundException | SQLException e){}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
