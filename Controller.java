package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.Statement;
@WebServlet("/formlink")
public class Controller extends HttpServlet{
	

	Connection con;
	public void init() throws ServletException{
		  try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/spring","root","Ashish@6060");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
      @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String name=req.getParameter("name");
    	String accno=req.getParameter("accno");
        String ammount=req.getParameter("ammount");    
        String deposite1=req.getParameter("deposite");    
        
      //parsing
      double Ammount=Double.parseDouble(ammount);
      
//     req.setAttribute("Ammount",Ammount);
//     req.setAttribute("accno", accno);
//     req.setAttribute("deposite", deposite1);
//     RequestDispatcher rd=req.getRequestDispatcher("Model");
//     rd.include(req, resp);
      
      
      
      
      
      
      PreparedStatement pstmt=null;
	  String query="insert into bank_info values(0,?,?,?)";
	  
	try {
		pstmt=con.prepareStatement(query);
		
		pstmt.setString(1,name);
		pstmt.setString(2, accno);
		pstmt.setDouble(3,Ammount);
	    int count=pstmt.executeUpdate();
	
	        PrintWriter pw=resp.getWriter();
			RequestDispatcher rd1=req.getRequestDispatcher("/a.html");
			resp.getWriter().print("<h1>"+count+" Record Inserted Successfully</h1>");
			rd1.include(req, resp);
			
			

	}
	catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	   
	
      
    	  
    }
      
    
}
