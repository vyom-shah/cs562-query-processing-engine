package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main_class {
	
	public static HashMap<String, String> dataType = new HashMap<String, String>();
	private static List<String> select = new ArrayList<String>();
	private static int number;
	private static List<String> groupby = new ArrayList<String>();;
	private static List<GroupVariable> fvect_variable = new ArrayList<GroupVariable>();;
	private static List<ST> suchthat = new ArrayList<ST>();;
	private static List<String> having = new ArrayList<String>();
	private static List<String> where_condition = new ArrayList<String>();

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
	
		public void addArguments(File input) {
		
			try {
				Scanner sc = new Scanner(input);
				
				String inputLine;
				String [] select_attributes = null;
				String [] grouping_atributes = null; 
				String [] fvect = null; 
				String [] select_condition = null; 
				String [] where = null; 
				String [] having_condition = null;
				int noGV = 0;
				while (sc.hasNextLine()) {
					inputLine = sc.nextLine();
					if(inputLine.contains("select_attribute"))
					{
						inputLine = inputLine.replaceAll(".+:","");
						select_attributes = inputLine.split(", ");
					}
					else if(inputLine.contains("no_gv"))
					{
						inputLine = inputLine.replaceAll(".+:","");
						noGV = Integer.parseInt(inputLine);
					}
					else if(inputLine.contains("grouping_attributes"))
					{
						inputLine = inputLine.replaceAll(".+:","");
						grouping_atributes = inputLine.split(", ");
					}
					else if(inputLine.contains("where"))
					{
						//If there is no where condition set it to null.
						inputLine = inputLine.replaceAll(".+:", "");
						if(inputLine.equals(""))
						{
							where = null;
						}
						else
						{
							where = inputLine.split(", ");
						}
						
					}
					else if(inputLine.contains("fvect"))
					{
						inputLine = inputLine.replaceAll(".+:","");
						fvect = inputLine.split(", ");
					}
					else if(inputLine.contains("select"))
					{
						inputLine = inputLine.replaceAll(".+:","");
						select_condition= inputLine.split(", ");
					}
					else if(inputLine.contains("having_condition"))
					{
						//If there is no having condition set it to null.
						inputLine = inputLine.replaceAll(".+:","");
						if(inputLine.equals(""))
						{
							having_condition = null;
						}
						else
						{
							having_condition= inputLine.split(", ");
						}
						
					}
					else 
					{
						continue;
					}
				}
				
				
			getArguments(select_attributes, grouping_atributes, fvect, select_condition, noGV, where, having_condition);
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/**
		 * This method will take all the operators and parse them in an appropriate manner 
		 * and add them to a list (where ever required), so that it becomes easy to use them in generator classes.
		 * 
		 * @param select_attributes
		 * @param grouping_atributes
		 * @param fvect
		 * @param select_condition
		 * @param noGV
		 * @param where
		 * @param having_condition
		 */
		
		private static void getArguments(String[] select_attributes, String[] grouping_atributes, String[] fvect,
				String[] select_condition, int noGV, String[] where, String[] having_condition) {
			// TODO Auto-generated method stub
			
			
			//Setting select variable
			for(String str: select_attributes)
			{
				if(str.contains("_"))
				{
					String [] value = str.split("_");
					GroupVariable gv = new GroupVariable(value[0], value[1], value[2]);
					select.add(gv.getString());

				}
				else
				{
					select.add(str);
				}
			}
			
			//Setting number  of grouping variables.
			number = noGV;
			for(String str: grouping_atributes)
			{
				groupby.add(str); 
			}
			
			//Setting where varibale
			if(where != null )
			{
				for(String str: where)
				{
					where_condition.add(str);
				}
			}
			
			//Setting FVect variable
			for(String str: fvect)
			{
				String [] value = str.split("_");
				GroupVariable gv = new GroupVariable(value[0], value[1], value[2]);
				fvect_variable.add(gv);
			}
			
			//Setting such that clause in select_condition variable
			for(String str: select_condition)
			{		
				String [] value = str.split("_");		
				ST pair = new ST( Integer.parseInt(value[0]), value[1]);
				suchthat.add(pair);
			}
			
			//Setting having_condition
			if (having_condition != null ) {
				for(String str: having_condition)
				{
					having.add(str);			
				}
			}
			
		}
		
		//Getter for variables and getter for sizes of where and having condition.
		public List<String> getSelect() {
			return select;
		}

		
		public int getNumber() {
			return number;
		}

		public List<String> getGroupby() {
			return groupby;
		}

		public List<GroupVariable> getFvect() {
			return fvect_variable;
		}

		public List<ST> getSuchthat() {
			return suchthat;
		}

		public List<String> getHaving() {
			return having;
		}
		public List<String> getWhere()
		{
			return where_condition;
		}
		
		public int getSizeWhere()
		{
			return where_condition.size();
		}

		public int getSizeHaving()
		{
			return having.size();
		}
	
	public static void main(String args[]) {
		File input;
		Main_class code=new Main_class();
		code.connect();
		
		
		dataType=Schema.getSchema();
		System.out.println(dataType);
		
//		group=GroupVariable
	
		
		System.out.println("Please enter MF of EMF depending on what type of query you want to run");
		Scanner in= new Scanner(System.in);
		String query=in.nextLine();
		query = query.replace(" ","");
		query = query.toUpperCase();
		
		if(query.equals("MF"))
		{
			input=new File("/Users/vyom/git/cs562/562project/Inputs/MFQuery1.txt");
//			input=new File("/Users/vyom/git/cs562/562project/Inputs/MFQuery2.txt");
//			input=new File("/Users/vyom/git/cs562/562project/Inputs/MFQuery3.txt");
//			input=new File("/Users/vyom/git/cs562/562project/Inputs/MFQuery4.txt");
//			input=new File("/Users/vyom/git/cs562/562project/Inputs/MFQuery5.txt");
			
			code.addArguments(input);
			
			System.out.println("Select");
			System.out.println(code.getSelect());
			
			System.out.println("Number");
			System.out.println(code.getNumber());
			System.out.println("GroupBy");
			System.out.println(code.getGroupby());
			System.out.println("Fvect");

			System.out.println(code.getFvect());
			System.out.println("SuchThat");

			System.out.println(code.getSuchthat());
			System.out.println("getHaving");

			System.out.println(code.getHaving());
			System.out.println("getWhere");

			System.out.println(code.getWhere());
			System.out.println("size where");

			System.out.println(code.getSizeWhere());
			System.out.println("getSize");

			System.out.println(code.getSizeHaving());
			
			MFCode.codeMF(dataType);
			System.out.println("Generation Successful");
			
			System.out.println("\n\n");
			System.out.println();
		}
		else if(query.equals("EMF")) {
			input=new File("/Users/devilabakrania/git/cs562/562project/Inputs/EMFQuery1.txt");
			code.addArguments(input);
			
			System.out.println("Select");
			System.out.println(code.getSelect());
			System.out.println("Number");
			System.out.println(code.getNumber());
			System.out.println("GroupBy");
			System.out.println(code.getGroupby());
			System.out.println("Fvect");

			System.out.print("[");
			for(GroupVariable ak:code.getFvect()) {
				System.out.print(ak.getString()+" ");
			}
			System.out.print("]\n");
			System.out.println("SuchThat");

			System.out.println(code.getSuchthat());
			
			for(ST ak:code.getSuchthat()) {
				System.out.print(ak.toString());
			}
			System.out.println("\n");
			
			System.out.println("getHaving");

			System.out.println(code.getHaving());
			System.out.println("getWhere");

			System.out.println(code.getWhere());
			System.out.println("size where");

			System.out.println(code.getSizeWhere());
			System.out.println("getSize");

			System.out.println(code.getSizeHaving());
			System.out.println("EMF Code Generated Successfully!");
			
			System.out.println("\n\n");
			System.out.println();
			
			//START CODE
			//GroupVariable gv = new GroupVariable();
			EMFCode.codeEMF(dataType);
			
			//END CODE
			
		}
		else
		{
			System.out.println("Please enter a valid option");
		}
	}
}

class GroupVariable {

	String aggregate, attribute, index;

	GroupVariable(String index, String aggregate, String attribute)
	{
		this.index = index;
		this.aggregate = aggregate;
		this.attribute = attribute;
	}
	public String getString()
	{
		return aggregate+"_"+attribute+"_"+index;
	}
}

class ST {
	
	public int index;
	public String attribute;
	
	public ST(int index, String attribute) {
		this.index = index;
		this.attribute = attribute;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
