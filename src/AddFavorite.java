

import java.io.IOException;
//import java.io.PrintWriter;
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
 * Add Favorite:
 * 	Adds a movie to the user's favorite's list
 */
@WebServlet("/AddFavorite")
public class AddFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFavorite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8"); 
		//final PrintWriter out= response.getWriter(); 
		
		String DB_URL = "jdbc:mysql://localhost:3306/filmdb";

		// Database credentials
		String USER = "root";
		String PASS = "";

		Connection conn = null;
		Statement stmt = null;
		
		String movieName=request.getParameter("addFavorite");
		
		String[] sesVar = Functions.getSessionInfo();
		int movieId=1;
		int userId=Integer.parseInt(sesVar[0]);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 
			 sql="SELECT id FROM movies WHERE name = \""+movieName+"\"";
			 
			 ResultSet rs = (ResultSet) stmt.executeQuery(sql);
			 
			 if(rs.next()){
				 movieId = rs.getInt("id");
			 
			 
				 sql="INSERT INTO userFavorites (userId,movieId) VALUES ("+userId+","+movieId+")";
			 
				 stmt.executeUpdate(sql);
			 }
			 else{
				 
			 }
			 
			 conn.close();
			 stmt.close();
			 
			 response.sendRedirect("Favorites");
			 
		}catch(ClassNotFoundException | SQLException e){e.printStackTrace();}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
