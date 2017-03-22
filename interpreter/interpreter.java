/**
 * Created by kemok on 3/7/2017.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class interpreter {


	    static int add(int a, int b){
	        return a+b;
	    }
	    static int sub(int a, int b){
	        return a-b;
	    }
	    static int mul(int a, int b){
	        return a*b;
	    }
	    static void div(int a, int b, Stack<String> stack){
	    	if(b==0){
	    	stack.push(String.valueOf(a));
	    	stack.push(String.valueOf(b));
	    	stack.push(":error:");
	    	}
	    	else
	        stack.push(String.valueOf(a/b));
	    	}
	   static int rem(int a, int b){

	        return b%a;
	    }
	   static String neg(String a){

	   	 if(a.charAt(0)=='-'){
			   String s = a.substring(1);
			   return s;
		   }
	      if(a.equals("0")){
	            return "0";
	        }
	        return ("-"+a);
	    }
	    static String  swap(String a, String b){

	        return b +" " +a;
	    }


	    static boolean isNumber(String input)
	    {
	        try
	        {
	            Integer.parseInt(input);
	        }
	        catch(NumberFormatException ex)
	        {
	            return false;
	        }
	        return true;
	    }
	    public static void interpreter(String input, String output){

	        FileReader fr = null;
	        BufferedReader br = null;

	        ArrayList<String> commands =  new<String> ArrayList();
	        Stack <String>local =  new Stack<String>();
	        commands.add("add");
	        commands.add("sub");
	        commands.add("div");
	        commands.add("mul");
	        commands.add("rem");

	        try {
	            fr = new FileReader(input);
	            br = new BufferedReader(fr);
	            String line;
	            boolean isValid;
	            while ((line = br.readLine()) !=  null) {

	                String[] words=line.split("\\s");


	                if(commands.contains(words[0])  && local.size()>1){

	                    int x=0, y=0;
	                    String first = local.pop();
	                    String second =  local.pop();

	                    if(isNumber(first) && isNumber(second)) {
	                         x = Integer.parseInt(first);
	                         y = Integer.parseInt(second);
	                         isValid=true;
	                    }else {
	                        local.push(second);
	                        local.push(first);
	                        local.push(":error:");
	                        isValid=false;

	                    }
	                    if(isValid) {
	                        int total;
	                        switch (words[0]) {
	                            case "add":
	                                total = add(x, y);
	                                local.push(String.valueOf(total));
	                                break;
	                            case "sub":
	                                total = sub(y, x);
	                                local.push(String.valueOf(total));
	                                break;
	                            case "mul":
	                                total = mul(x, y);
	                                local.push(String.valueOf(total));
	                                break;
	                            case "div":
	                                div(y, x, local);
	                                break;
	                            case "rem":
	                                total = rem(x, y);
	                                local.push(String.valueOf(total));
	                                break;
	                        }

	                    }

	                }else if(commands.contains(words[0])  && local.size()<=1){
	                    local.push(":error:");

	                }
	                else if (words[0].equals("swap")){
	                    String toArr;
	                    String first;
	                    String second;
	                    if(local.size()>1) {
	                        first = local.pop();
	                        second = local.pop();
	                       // System.out.println("before swap: " + first + " -- : " +second +"\n");
	                        toArr = swap(first, second);
	                        String ret[] = toArr.split("\\s");
	                        local.push(ret[1]);
	                        local.push(ret[0]);
	                    }else {
	                       // System.out.println("swaping one element stack");
	                       local.push(":error:");
	                    }
	                }
	                else if (words.length==2 && (words[0].equals("push"))) {
	                    		String regex = "^[a-zA-Z]+$";

						if (words[1].matches(regex)) {
							local.push(words[1]);
						}
						else if (words[1].charAt(0)=='"' && words[1].charAt(words[1].length()-1)=='"'){
							String tem = words[1].substring(1,words[0].length()+1);
							local.push(tem);
						}
						else if (words[1].equals("-0")) {
							local.push("0");
						}
						else {
							if (checkIsInt(words[1])) {
								local.push(words[1]);
							}
							else {
								local.push(":error:");
							}
						}
	                }else if(words[0].equals(":false:") || words[0].equals(":true:")){
	                    local.push(words[0]);

	                }else if(words[0].equals("pop")){
	                    if(local.isEmpty()){
	                      //  System.out.println("poping emty stack");
	                        local.push(":error:");
	                    }
	                    else
	                    local.pop();


	                }
	                else if(words[0].equals("neg")){

	                    if(!local.isEmpty()){
	                        String tem = local.pop();
	                        if(isNumber(tem))
	                    	    local.push(String.valueOf(neg(tem)));
	                        else{
	                            
	                            local.push(tem);
	                            local.push(":error:");
	                        }

	                    }else local.push(":error:");

	                }

	                else if(words[0].equals("quit")) {
	                    print(local, output);

	                    try {
	                        br.close();
	                        fr.close();


	                    } catch (IOException e) {

	                        e.printStackTrace();
	                    }
	                    System.exit(0);
	                }
	                else if(words[0].equals(":error:")){
	                   local.push(":error:");
	                }
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	  static  boolean checkIsInt(String str){
	    
			  try
			  {
				  double ret = Double.parseDouble(str);
				  Double d = new Double(ret);
				  int val = d.intValue();

				  if(d != val){
					  return false;
				  }
			  }
			  catch(NumberFormatException ex)
			  {
				  return false;
			  }
			  return true;


	    }
	static void print(Stack<String> l, String out){

	    File file;
	    PrintWriter pr = null;

	    file = new File(out);

	    try {
	        if(!file.exists())
	            file.createNewFile();
	        pr = new PrintWriter(file);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();

	    }catch (IOException e){
	        e.printStackTrace();

	    }

	    while(!l.isEmpty()){
	        String writeTo = l.pop();
	        pr.println(writeTo);
	    }
	    pr.close();
	}
}
/*
	public  static void main(String args[]){
		interpreter in = new interpreter();
		String input ="C:/Users/kemok/Desktop/Spring2017/CSE 305/home_work/CSE305-Programing-Languages/cse305/sample_input4.txt";
		String out ="C:/Users/kemok/Desktop/Spring2017/CSE 305/home_work/CSE305-Programing-Languages/cse305/output.txt";
		in.interpreter(input,out);
	}
*/

