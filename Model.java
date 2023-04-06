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

@WebServlet("/Model")
public class Model extends HttpServlet{
	
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
		   
	    	String accno=req.getParameter("accno");
	        String ammount=req.getParameter("ammount");    
	        String op=req.getParameter("test");    
	        
	      //parsing
	      double Ammount=Double.parseDouble(ammount);
	      double NBalance=0.0;
		
		
		String query="select * from bank_info where accountnumber='"+accno+"'";
		try {
			ResultSet rs=null; 
			java.sql.Statement stmt=null;
		
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			while(rs.next())
			{
				   double Balance=rs.getDouble(4);
				   
				   
			if(accno.equals(rs.getString(3)))
			{
				   
				   
				if(op.equals("deposite"))
				{
					    NBalance=Balance+Ammount;
                        String query1="update bank_info set ammount='"+NBalance+"' where accountnumber='"+accno+"'";
					    stmt=con.createStatement();
					    stmt.executeUpdate(query1);
					    
						RequestDispatcher rd1=req.getRequestDispatcher("/a.html");
						resp.getWriter().print("<h1> Account Number: "+accno+" Price Deposite Successfully</h1>");
						rd1.include(req, resp);
					break;
				}
				else if(op.equals("withdraw")){
					if(Balance>Ammount)
					{
				     NBalance=Balance-Ammount;
					String query1="update bank_info set ammount='"+NBalance+"' where accountnumber='"+accno+"'";
					stmt=con.createStatement();
					stmt.executeUpdate(query1);
					PrintWriter pw=resp.getWriter();
					RequestDispatcher rd1=req.getRequestDispatcher("/a.html");
					resp.getWriter().print("<h1> Account Number: "+accno+" Price Withdraw Successfully</h1>");
					rd1.include(req, resp);
					break;
				    }else {
					PrintWriter pw=resp.getWriter();
					RequestDispatcher rd1=req.getRequestDispatcher("/a.html");
					resp.getWriter().print("<h1>Incficient Balance </h1>");
					rd1.include(req, resp);
					break;
				       }
			        }
				else
				{
					PrintWriter pw=resp.getWriter();
					RequestDispatcher rd1=req.getRequestDispatcher("/a.html");
					resp.getWriter().print("<h1>Account Number:"+accno+" of Price = "+Balance+"</h1>");
					rd1.include(req, resp);
					break;
				}
	       }else
			{
				PrintWriter pw=resp.getWriter();
				RequestDispatcher rd1=req.getRequestDispatcher("/a.html");
				resp.getWriter().print("<h1> Please Enter Valid Account Number</h1>");
				rd1.include(req, resp);
				break;
			}
				
			
					
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
