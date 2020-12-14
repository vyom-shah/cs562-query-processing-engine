package project;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MFCode {
	
	public static void codeMF(HashMap<String,String> dataType) {
		try {
//			File output=new File("/Users/vyom/eclipse-workspace/562project/src/project/MFOp.java");
			File output=new File("/Users/vyom/git/cs562/562project/src/project/MF.java");
			PrintWriter writer=new PrintWriter(output);
//-----------------------------------------------------------------------------------------			
			writer.print("package project;\n");
			writer.print("import java.sql.*;\n");
			writer.print("import java.util.*;\n");
			writer.print("import java.io.*;\n");
//-----------------------------------------------------------------------------------------			
			
			writer.print("public class MF {\n");
			writer.print("\t//Variables to connect to DB\n");
			writer.print("\tprivate static final String usr = \"postgres\";\n"
					+ "	private static final String pwd = \"1234\";\n"
					+ "	private static final String url = \"jdbc:postgresql://localhost/project\";\n");
			
			writer.print("\t//Variables to generate the output\n");
			writer.print("\tList<Result_Attributes> output_attributes = new ArrayList<Result_Attributes>();\n");
			writer.print("\tList<MF_Structure> mfStruct = new ArrayList<MF_Structure>();\n");
			writer.print("\n");

			// Generate DB structure
			writer.print("\t /** \n\t * This class contains the DB schema \n\t */ \n");
			
//-----------------------------------------------------------------------------------------	
			writer.print("\tpublic class DBStruct{\n");
			for (Map.Entry<String, String> entry : dataType.entrySet())
				writer.print("\t\t" + entry.getValue() + " " + entry.getKey() + ";\n");

			writer.print("\t}\n");
			
//-----------------------------------------------------------------------------------------	
			writer.print("\n\t /** \n\t * This class contains the selection attributes \n\t */ \n");
			Main_class mc = new Main_class();
			
			//Class Result_Attributes
			writer.print("\tpublic class Result_Attributes{\n");
			for (String str : mc.getSelect()) {
				if (dataType.get(str) != null) {
					writer.print("\t\t" + dataType.get(str) + " " + str + ";\n");
				} else {
					writer.print("\t\tint " + str + ";\n");
				}
			}
			writer.print("\t}\n");

//-----------------------------------------------------------------------------------------	
			writer.print("\n\t /** \n\t * This class contains all f-vect attributes \n\t * and group by attribues \n\t */ \n");
			List<String> added_elements = new ArrayList<String>();
			
			//Class MF_Structure
			writer.print("\tpublic class MF_Structure{\n");
			for (String str : mc.getGroupby()) {
				for (Map.Entry<String, String> entry : dataType.entrySet())
					if(str.equals(entry.getKey()))
					{
						writer.print("\t\t" + entry.getValue() + " " + entry.getKey() + ";\n");
					}
					
			}
			
			for (GroupVariable fa : mc.getFvect()) {
				
				/**
				 * Checking if the aggregate function is average or not.
				 * 
				 * If it is average divide it into sum and count to calculate average.
				 */
				
				if (fa.aggregate.equals("avg")) {
					String sum = "sum_" + fa.attribute + "_" + fa.index;
					String count = "count_" + fa.attribute + "_" + fa.index;
					if (!added_elements.contains(sum))
					{
						added_elements.add(sum);
						writer.print("\t\tint" + " sum_" + fa.attribute + "_" + fa.index + ";\n");
					}
					if(!added_elements.contains(count))
					{
						added_elements.add(count);
						writer.print("\t\tint" + " count_" + fa.attribute + "_" + fa.index + ";\n");
					}
					if(!added_elements.contains(fa.getString()))
					{
						writer.print("\t\tint " + fa.getString() + ";\n");
	                    added_elements.add(fa.getString());
					}
						
					} else {
						if(!added_elements.contains(fa.getString()))
						{
							writer.print("\t\tint " + fa.getString() + ";\n");
							added_elements.add(fa.getString());
						}
					
				}

			}
			writer.print("\t}\n");
//-----------------------------------------------------------------------------------------	
			//Writing the main method in the output file.
			writer.print("\n\t /** \n\t * Main - method \n\t * The program will start here. \n\t */ \n");
			writer.print("\tpublic static void main(String [] args){\n");

			writer.print("\n\t\tMF mf = new MF();\n");
			writer.print("\t\tmf.connect();\n");
			writer.print("\t\tmf.retrive();\n");
			writer.print("\t\tmf.addToOutput();\n");
			writer.print("\t\tmf.outputTable();\n");

			writer.print("\t}\n");

			// Create functions for connection
			writer.print("\n\t /** \n\t * This method will load the drivers for the DB. \n\t */ \n");
//-----------------------------------------------------------------------------------------	
			
			writer.print("\tpublic void connect(){\n");
	        writer.print("\t\ttry {\n");
	        writer.print("\t\tClass.forName(\"org.postgresql.Driver\");\n");
	        writer.print("\t\tSystem.out.println(\"Success loading Driver!\");\n");
	        writer.print("\t\t} catch(Exception exception) {\n");
	        writer.print("\t\texception.printStackTrace();\n");
	        writer.print("\t\t}\n\t}\n");
//-----------------------------------------------------------------------------------------	


	     // Main logic
			writer.print("\n\t /** \n\t * This method will create the data set required. \n\t */ \n");		
			writer.print("\tpublic void retrive(){\n");
			// Trying Connection
			writer.print("\t\ttry {\n");
			writer.print("\t\t\tConnection con = DriverManager.getConnection(url, usr, pwd);\n");

			// Declaring variables
			writer.print("\t\t\tResultSet result_set;\n");
			writer.print("\t\t\tboolean more;\n");
			writer.print("\t\t\tStatement st = con.createStatement();\n");
			writer.print("\t\t\tString query = \"select * from sales\";\n");
			writer.print("\n");
			
			
			
			
			outputWhileLoops(writer,mc, dataType);
			
			
			
			
			writer.print("\t\t}catch(Exception e) {\n");
			writer.print("\t\t\te.printStackTrace();\n");
			writer.print("\t\t}\n");
			writer.print("\t}\n");

			
//-----------------------------------------------------------------------------------------	

			//Create comapre Methods
			writer.print("\n\t /** \n\t * These are comapare methods to compare two string values orinteger values. \n\t * @return boolean true if same or else false. \n\t */ \n");
			// TODO Auto-generated method stub
			  //generate compare method
			writer.print("\tboolean compare(String str1, String str2){\n");
			writer.print("\t\treturn str1.equals(str2);\n\t}\n");
			writer.print("\tboolean compare(int num1, int num2){\n");
			writer.print("\t\treturn (num1 == num2);\n\t}\n"); 
			
//-----------------------------------------------------------------------------------------	
			
			//Create addToOutput to build result
			writer.print("\n\t /** \n\t * This method will filter output data if having conditions exist. \n\t */ \n");
			// TODO Auto-generated method stub
			writer.print("\tpublic void addToOutput(){\n");
			writer.print("\t\tfor(MF_Structure ms: mfStruct){\n");
			writer.print("\t\t\tResult_Attributes ra = new Result_Attributes();\n");
			
			for(String str: mc.getGroupby())
			{
				writer.print("\t\t\t\tra."+str+" = ms."+str+";\n");
			}
			
			
			writer.print("\t\t\tif(");
			
			boolean isSecondHaving = false;
			
			//Putting the having condition in the output file for filtering the output.
			
			if(mc.getSizeHaving()!= 0)
			{
				for(String str: mc.getHaving())
				{
					if(str.contains("sum") || str.contains("avg") || str.contains("max") || str.contains("min") || str.contains("count"))
					{
						str = str.replace("sum", "ms.sum");
						str = str.replace("avg", "ms.avg");
						str = str.replace("max", "ms.max");
						str = str.replace("min", "ms.min");
						str = str.replace("count","ms.count");
					}
					if(isSecondHaving == false)
					{
						writer.print("("+str+")");
						isSecondHaving = true;
					}
					else if(isSecondHaving == true)
					{
						writer.print(" && (" + str +")");
					}
				}
			}
			//If there is no having condition put "true".
			else
			{
				writer.print("true");
			}
			writer.print("){\n");
			for(String str: mc.getSelect())
			{
				for (GroupVariable fa: mc.getFvect())
				{
					if(str.equals(fa.getString()))
					{
						writer.print("\t\t\t\tra."+fa.getString()+" = ms."+fa.getString()+";\n");
					}
				}
				
			}
			
			writer.print("\t\t\t}\n");
			writer.print("\t\t\toutput_attributes.add(ra);\n");
			writer.print("\t\t}\n");
			writer.print("\t}\n");
			
//-----------------------------------------------------------------------------------------	
			
			// Generate method to print output
			writer.print("\n\t /** \n\t * This method will create format for outputting the data table. \n\t */ \n");
			// TODO Auto-generated method stub
			int length;
			writer.print("\tpublic void outputTable(){\n");
			
			for(String str: mc.getSelect())
			{
				length = str.length();
				writer.print("\t\tSystem.out.printf(\"%-"+length+"s\",\"" +str+"\\t\");\n");
			}
			writer.print("\t\tSystem.out.printf(\"\\n\");\n");
			writer.print("\t\tSystem.out.printf(\"");
			for(String str: mc.getSelect())
			{
				length = str.length();
				for(int i =0; i< length; i++)
				{
					writer.print("=");
				}
				writer.print("\\t");
			}
			writer.print(" \");\n");
			writer.print("\t\tfor(Result_Attributes ra: output_attributes){\n");
			writer.print("\t\t\tSystem.out.printf(\"\\n\");\n");
			for(String str: mc.getSelect())
			{
				for(String str1: mc.getGroupby())
				{
					if(str.equals(str1))
					{
						length = str.length();
						if(str.equals("month") || str.equals("year") || str.equals("days") || str.equals("quant"))
						{
							writer.print("\t\t\tSystem.out.printf(\"%"+length+"s\\t\", ra."+str+");\n");
						}
						else
						{
							writer.print("\t\t\tSystem.out.printf(\"%-"+length+"s\\t\", ra."+str+");\n");
						}
						
					}
				}
				for(GroupVariable fv : mc.getFvect())
				{
					if(str.equals(fv.getString()))
					{
						length = str.length();
						writer.print("\t\t\tSystem.out.printf(\"%"+length+"s\\t\", ra."+str+");\n");	
					}
				}
				
			}
			writer.print("\t\t}\n");
			writer.print("\t}\n");
			writer.print("}\n");
			
			
			writer.close();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void outputWhileLoops(PrintWriter writer, Main_class mc, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub
		List<String> added_elements = new ArrayList<String>();
		List<String> updated_elements = new ArrayList<String>();
		
		//Generating number of while loops equal to number of Grouping variables.
		writer.print("\n\t\t\t /** \n\t\t\t * Generating while loops for each grouping variable. \n\t\t\t */ \n");
		for (int i = 0; i < mc.getNumber(); i++) {
			writer.print("\n\t\t\t//While loop for grouping variable "+ (i+1) +".\n");
			writer.print("\t\t\tresult_set = st.executeQuery(query);\n");
			writer.print("\t\t\tmore = result_set.next();\n");
			writer.print("\t\t\twhile(more){\n");
			writer.print("\t\t\t\tDBStruct currentRow = new DBStruct();\n");
			for (Map.Entry<String, String> entry : key_dataType.entrySet()) {
				if (entry.getValue().equals("String")) {
					writer.print("\t\t\t\tcurrentRow." + entry.getKey() + " = result_set.getString(\"" + entry.getKey()
							+ "\");\n");
				} else if (entry.getValue().equals("int")) {
					writer.print("\t\t\t\tcurrentRow." + entry.getKey() + " = result_set.getInt(\"" + entry.getKey()
							+ "\");\n");
				}
			}

			boolean isSecondWhere = false;
			boolean isSecondSuchThat = false;
			boolean negative = false;
			// Filtering data if it has where conditions
			writer.print("\t\t\t\tif(");
			if (mc.getSizeWhere() != 0) {
				for (String str : mc.getWhere()) {
					str = str.replace(" ", "");
					if(str.contains("<=") || str.contains(">=") || str.contains(">") || str.contains("<") || str.contains("!="))
					{
						str = str;
					}
					else if(str.contains("="))
					{
						str = str.replace("=", "==");
					}
					
					if(str.contains("prod==") || str.contains("state==") || str.contains("cust=="))
					{						
						String [] nameVal = str.split("=");
						str = str.replace(str, nameVal[0]+".equals(\""+nameVal[2]+"\")");
					}
					if(str.contains("prod!=") || str.contains("state!=") || str.contains("cust!="))
					{	
						negative = true;					
						String [] nameVal = str.split("!=");
						str = str.replace(str, nameVal[0]+".equals(\""+nameVal[1]+"\")");
					}
					if (isSecondWhere == false) {
						if(negative == true)
						{
							writer.print("!currentRow." + str);
							isSecondWhere = true;
							negative = false;
						}
						else
						{
							writer.print("currentRow." + str);
							isSecondWhere = true;
						}
						
					} else if (isSecondWhere == true) {
						if(negative == true)
						{
							writer.print(" && !currentRow." + str);
							negative = false;
						}
						else
						{
							writer.print(" && currentRow." + str);
						}
						
					}

				}
			} else {
				writer.print(true);
			}
			writer.print("){\n");

			//Putting the such that conditions if any.
			negative = false;
			writer.print("\t\t\t\t\tif (");
			for (ST such_that : mc.getSuchthat()) 
			{
				negative = false;
				String str = such_that.getAttribute();
				str = str.replace(" ", "");
				if(str.contains("<=") || str.contains(">=") || str.contains(">") || str.contains("<"))
				{
					str = str;
				}
				else if(str.contains("="))
				{
					str = str.replace("=", "==");
				}
				if(str.contains("prod==") || str.contains("state==") || str.contains("cust=="))
				{						
					String [] nameVal = str.split("=");
					str = str.replace(str, nameVal[0]+".equals("+nameVal[2]+")");
				}
				if(str.contains("prod!=") || str.contains("state!=") || str.contains("cust!="))
				{	
					negative = true;					
					String [] nameVal = str.split("!==");
					str = str.replace(str, nameVal[0]+".equals(\""+nameVal[1]+"\")");
				}
				if (such_that.getIndex() == i + 1 && isSecondSuchThat == false) {
					if(negative == true)
					{
						isSecondSuchThat = true;
						writer.print("!currentRow." + str);
					}
					else
					{
						isSecondSuchThat = true;
						writer.print("currentRow." + str);
					}
					
				} else if (such_that.getIndex() == i + 1 && isSecondSuchThat == true) {
					if(negative == true)
					{
						writer.print(" && !currentRow." + str);
					}
					else
					{
						writer.print(" && currentRow." + str);
					}
					
				}
			}
			if (isSecondSuchThat == false) {
				writer.print("true");
			}
			writer.print("){\n");

			writer.print("\t\t\t\t\t\tboolean found = false;\n");
			writer.print("\t\t\t\t\t\tfor(MF_Structure row: mfStruct){\n");

			boolean isSecondGroupByVariable = false;
			writer.print("\t\t\t\t\t\t\tif(compare(row.");
			for(String str: mc.getGroupby())
			{
				if(isSecondGroupByVariable == false)
				{
					writer.print(str+",currentRow."+str+")");
					isSecondGroupByVariable = true;
				}
				else
				{
					writer.print(" && compare(row."+str+",currentRow."+str+")");
				}
			}
			writer.print("){\n");
			
			writer.print("\t\t\t\t\t\t\t\tfound = true;\n");
			
			
			//Outputting the aggregate functions if record is added already.
			for(GroupVariable fa: mc.getFvect())
			{
				 if (Integer.parseInt(fa.index) == i+1){
	                    if (fa.aggregate.equals("avg")){
	                    	String sum = "sum_" + fa.attribute + "_" + fa.index;
							String count = "count_" + fa.attribute + "_" + fa.index;
							if (!updated_elements.contains(sum))
							{
								updated_elements.add(sum);
							    writer.print("\t\t\t\t\t\t\t\trow."+sum+" += currentRow."+fa.attribute+";\n");			                       
							}
							if(!updated_elements.contains(count))
							{
								updated_elements.add(count);
								writer.print("\t\t\t\t\t\t\t\trow."+count+" ++;\n");
							}
							if(!updated_elements.contains(fa.getString()))
							{
								updated_elements.add(fa.getString());
								writer.print("\t\t\t\t\t\t\t\tif(row."+count+" !=0){\n");
								writer.print("\t\t\t\t\t\t\t\t\trow."+fa.getString()+" = row."+sum+"/row."+count+";\n");
								writer.print("\t\t\t\t\t\t\t\t}\n");
							}
						
							
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("sum")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+" += currentRow."+fa.attribute+";\n");
	                        updated_elements.add(fa.getString());
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("max")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+" = (row."+fa.getString()+"< currentRow."+fa.attribute+") ? currentRow."+fa.attribute +" :row."+fa.getString()+";\n");
	                        updated_elements.add(fa.getString());
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("min")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+" = (row."+fa.getString()+"> currentRow."+fa.attribute+") ? currentRow."+fa.attribute +" :row."+fa.getString()+";\n");
	                        updated_elements.add(fa.getString());
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("count")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+"++;\n");
	                        updated_elements.add(fa.getString());
	                    }
	                }
			}
			
			writer.print("\t\t\t\t\t\t\t}\n");
			writer.print("\t\t\t\t\t\t}\n");
			
			//If record is found for the first time the following lines will called.
			writer.print("\t\t\t\t\t\tif(found == false){\n");
			writer.print("\t\t\t\t\t\t\tMF_Structure addCurrentRow = new MF_Structure();\n");
			for(String str: mc.getGroupby())
			{
				writer.print("\t\t\t\t\t\t\taddCurrentRow."+str+" = currentRow."+str+";\n");
			}
			for(GroupVariable fa: mc.getFvect())
			{	
				if(Integer.parseInt(fa.index) == i+1)
				{
					if (fa.aggregate.equals("avg")) {
						String sum = "sum_" + fa.attribute + "_" + fa.index;
						String count = "count_" + fa.attribute + "_" + fa.index;
						if (!added_elements.contains(sum))
						{
							added_elements.add(sum);
							writer.print("\t\t\t\t\t\t\taddCurrentRow." + "sum_" + fa.attribute + "_" + fa.index + " = currentRow."+fa.attribute+";\n");
						}
						if(!added_elements.contains(count))
						{
							added_elements.add(count);
							writer.print("\t\t\t\t\t\t\taddCurrentRow." + "count_" + fa.attribute + "_" + fa.index + "++;\n");
						}
						if(!added_elements.contains(fa.getString()))
						{
							added_elements.add(fa.getString());
							writer.print("\t\t\t\t\t\t\tif(addCurrentRow."+count+" !=0){\n");
							writer.print("\t\t\t\t\t\t\t\taddCurrentRow."+fa.getString()+" = addCurrentRow."+sum+"/addCurrentRow."+count+";\n");
							writer.print("\t\t\t\t\t\t\t}\n");
						}
							
						} else {
							if(!added_elements.contains(fa.getString()))
							{
								if(fa.aggregate.equals("count"))
								{
									writer.print("\t\t\t\t\t\t\taddCurrentRow." + "count_" + fa.attribute + "_" + fa.index + "++;\n");
								}
								else
								{
									writer.print("\t\t\t\t\t\t\taddCurrentRow." + fa.getString() + " = currentRow."+fa.attribute+";\n");
								}
								added_elements.add(fa.getString());
							}
						
					}
				}
				
			}
			writer.print("\t\t\t\t\t\t\tmfStruct.add(addCurrentRow);\n");		
			writer.print("\t\t\t\t\t\t}\n");
			writer.print("\t\t\t\t\t}\n");
			writer.print("\t\t\t\t}\n");
			writer.print("\t\t\t\tmore = result_set.next();\n");
			writer.print("\t\t\t}\n");

		}
	}
	
	
}
