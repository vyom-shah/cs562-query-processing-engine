package project;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

public class MFCode {
	
	public static void codeMF(HashMap<String,String> dataType) {
		try {
			File output=new File("/Users/vyom/eclipse-workspace/final_project/src/final_project/MFOp.java");
			PrintWriter writer=new PrintWriter(output);
			
			writer.print("package project;\n");
			writer.print("import java.sql.*;\n");
			writer.print("import java.util.*;\n");
			writer.print("import java.io.*;\n");
			writer.print("\n");
			
			writer.close();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
