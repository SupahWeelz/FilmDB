

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
 * Favorites:
 * 	Lists user's favorite movies. Add and remove functionalities are also included.
 */
@WebServlet("/Favorites")
public class Favorites extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Favorites() {
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
		
		String[] sesVar = Functions.getSessionInfo();
		
		int userId = 1;
		if(sesVar[0] == null){
			response.sendRedirect("http://18.221.171.83/FilmDB/login.html");
		}
		try{
			userId = Integer.parseInt(sesVar[0]);
		}catch(NumberFormatException e){
		}
		
		String username = sesVar[1];
		
		String result = docType+"<html>\n"+
				 "<head><title>Favorites</title>\n<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n</head>\n"+
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
				 		"<li class=\"active\"><a href=\"Favorites\">Favorites</a></li>\n"+
				 		"<li><a href=\"TopTen\">Top Films</a></li>"+
				 		"<li><a href=\"CurrentlyShowing\">Currently Showing</a></li>"+
				 		"</ul>\n"+
				 	"</div>\n"+
				 	"</nav><div class=\"container-fluid\">\n<p>"+username+"'s favorite movies list</p><table class=\"table\">\n<thead><tr>\n<th>Movie</th>\n</tr>\n</thead>\n<tbody>\n";
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 
			 sql="SELECT movies.id,movies.name FROM userFavorites,movies WHERE userFavorites.userId="+userId+" AND movies.id=userFavorites.movieId";
			 
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 
			 while(rs.next()){
				 String temp1 = "<tr><td>"+rs.getString("name")+"</td>\n<td>"
				 		+ "<form action=\"RemoveFavorite\" method=\"post\"><input type=\"hidden\" name=\"movie\" value="+rs.getInt("movies.id")+"></input><button type=\"submit\">Remove</button></form></td></tr>\n";
				 result+=temp1;
			 }
			 result+="</tbody></table>\n";
			 result+="</div><hr><div class=\"well\"><form action=\"AddFavorite\">"+
					 "<label>Add favorite: <input name=\"addFavorite\" type=\"text\"></label><br>"+
					 "<button type=\"submit\">Add</button>"+
					 "</form></div></body>\n</html>";
			 out.println(result);
			 conn.close();
			 stmt.close();
			 
		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace(out);
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
