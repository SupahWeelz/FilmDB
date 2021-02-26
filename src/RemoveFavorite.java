

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Remove Favorite:
 * 	Removes favorite from user's favorites list
 */
@WebServlet("/RemoveFavorite")
public class RemoveFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFavorite() {
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
		
		int movieId=Integer.parseInt(request.getParameter("movie"));
		
		String[] sesVar = Functions.getSessionInfo();
		int userId=Integer.parseInt(sesVar[0]);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			 conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
			 stmt = (Statement) conn.createStatement();
			 String sql;
			 
			 sql="DELETE FROM userFavorites WHERE userId="+userId+" AND movieId="+movieId;
			 
			 stmt.executeUpdate(sql);
			 
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
		doGet(request,response);
	}

}
