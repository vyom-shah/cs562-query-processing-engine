package project;

import java.util.HashMap;

public class Main_class {

	public static HashMap<String, String> datatype = new HashMap<String, String>();
	private void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver connected successfully!");
			
		}catch(Exception e)
		{
			System.out.println("Driver loading Failed!");
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		Main_class code=new Main_class();
		code.connect();
		
	}
}