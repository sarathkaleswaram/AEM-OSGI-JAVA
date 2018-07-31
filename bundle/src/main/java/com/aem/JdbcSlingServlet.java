package com.aem;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;


@SlingServlet(paths = "/bin/company/jdbc", methods = "GET")
public class JdbcSlingServlet extends SlingSafeMethodsServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8377541227981036938L;
	
	String name="Hi";
	public void getMessage(String str){
		name = str;
	}
	
	
	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		final String db="jdbc:mysql://localhost/shar";
		final String us="root";
		final String ps="root";
		response.setHeader("Content-Type", "application/json");
		response.getWriter().print("{\"coming\" : \"soon\"}");
		response.setContentType("text/html");
	     PrintWriter out = response.getWriter();
	     String n = name;
	     try {
	    	 Class.forName("com.mysql.jdbc.Driver");   
	    	 Connection con = DriverManager.getConnection(db,us,ps);
	    	 PreparedStatement st = con.prepareStatement("select * from register;");
				ResultSet rs = st.executeQuery();	
				while(rs.next()){
				out.println(rs.getString(1));
				out.println("<br/>");
				}
	    	 PreparedStatement ps1 = con.prepareStatement("insert into register values(?);");
	    	 
	    	 ps1.setString(1, n);
	    	 
	    	 int i = ps1.executeUpdate();
	    	 
	    	 if(i>0)
	    		 out.println("You are successfully registered");   	 
	    	 
				rs.close();
				st.close();
				ps1.close();
				con.close(); 
	     }catch(Exception e){
	    	 System.out.println(e);
	    	
	     out.close();
	     }
	}
}
