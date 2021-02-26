import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Functions {
	public static String[] getSessionInfo(){
		try {
			  String[] retArr = new String[2];
		      File myObj = new File("C:/Users/Administrator/workspace/src/session.txt");
		      Scanner myReader = new Scanner(myObj);
		      int i = 0;
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        retArr[i] = data;
		        i++;
		      }
		      myReader.close();
		      return retArr;
		    } catch (FileNotFoundException e) 
			{
		      System.out.println("File not found");
		      e.printStackTrace();
		    }
		return null;
	}
	
	public static void setSessionInfo(String id, String user){
		try {
		      FileWriter myWriter = new FileWriter("C:/Users/Administrator/workspace/src/session.txt");
		      myWriter.write(id);
		      myWriter.write("\n");
		      myWriter.write(user);
		      myWriter.close();
		    } catch (IOException e)
			{
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public static void deleteSessionInfo(){
		try {
		      FileWriter myWriter = new FileWriter("C:/Users/Administrator/workspace/src/session.txt");
		      myWriter.write("");
		      myWriter.close();
		    } catch (IOException e)
			{
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
