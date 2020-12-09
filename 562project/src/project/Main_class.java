package project;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main_class {

	public static HashMap<String, String> dataType = new HashMap<String, String>();
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
		File input;
		Main_class code=new Main_class();
		code.connect();
		dataType=Schema.getInformationSchema();
		
		System.out.println("Please enter MF of EMF depending on what type of query you want to run");
		Scanner in= new Scanner(System.in);
		String query=in.nextLine();
		
		if(query.equals("MF"))
		{
			input=new File("/Users/vyom/git/cs562/562project/Inputs/MFQuery1.txt");
			System.out.println("Genereration Successful");
		}
		else if(query.equals("MF")) {
			
		}
		else
		{
			System.out.println("Please enter a valid option");
		}
	}
}