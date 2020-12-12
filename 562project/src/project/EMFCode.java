package project;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

public class EMFCode {
	 public static void codeEMF(HashMap<String, String> dataType) {
		 try {
			 File output=new File("/Users/devilabakrania/eclipse-workspace/final_project/src/final_project/EMFOp.java");
				PrintWriter writer=new PrintWriter(output);
				
				writer.print("package project;\n");
				writer.print("import java.sql.*;\n");
				writer.print("import java.util.*;\n");
				writer.print("import java.io.*;\n");
				writer.print("\n");
				
				writer.close();			
				
			} catch(Exception e) {
				System.out.println("Error occurred:" +e);
				e.printStackTrace();
			}
		}
	}
