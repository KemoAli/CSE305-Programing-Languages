/**
 * Created by kemok on 3/7/2017.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
			commands.add("lessThan");
			commands.add("equal");

			HashMap<String, String> names =  new HashMap<>(); //<key,value>
			ArrayList <String> namesOnly =  new ArrayList<>();


	        try {
	            fr = new FileReader(input);
	            br = new BufferedReader(fr);
	            String line;
	            boolean isValid, inScope=false;
	            while ((line = br.readLine()) !=  null) {

	                String[] words=line.split("\\s");

				/*	if (words[0].equals("let")){
						inScope =false;
						//letEnd.push("let");
					}if(words[0].equals("end")){
						inScope=true;
						//letEnd.pop();
					}
*/

	                if(commands.contains(words[0])  && local.size()>1){

	                    int x=0, y=0;
						String first=local.pop();
						String second = local.pop();
						if(names.containsKey(first)){
							first= names.get(first);
						}
						if (names.containsKey(second)){
							second = names.get(second);
						}


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
								case "equal":
									if(x==y){
										local.push(":true:");
									}else {
										local.push(":false:");
									}
									break;
								case "lessThan":
									if(y < x){
									local.push(":true:");
									}
									else {
										local.push(":false:");
									}
									break;
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
	                else if(words[0].equals("bind")){
						String first;
						String second;
						if(local.isEmpty() || local.size() <2){
							local.push(":error:");
							continue;
						}else {
							first=local.pop();
							second = local.pop();
							String val;

							if(isValidName(second) && !first.equals(":error:")){
								if (names.containsKey(first)) {
									val = names.get(first);
									names.put(second, val);
									local.push(":unit:");
								}else if(names.get(first)==null && !isNumber(first)){
									if (namesOnly.contains(first)) {
										local.push(second);
										local.push(first);
										local.push(":error:");
									}else {
										names.put(second, first);
										local.push(":unit:");
									}
								}
								else {
									names.put(second, first);
									local.push(":unit:");
								}

							}else {
								local.push(second);
								local.push(first);
								local.push(":error:");
							}

							/*if(first.equals(":error:")){
								local.push(second);
								local.push(first);
								local.push(":error:");
								continue;
							}else {
								if (names.containsKey(first)) {
									val = names.get(first);
									names.put(second, val);
								} else {
									names.put(second, first);
								}
								local.push(":unit:");
							}*/
						}
						/*if (first.equals(":true:") || first.equals(":false:") || isNumber(first)
								|| isValidName(first)) {
							names.put(second,first);
							local.push(":unit:");
						}*/

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
	                else if (words[0].equals("end") || words[0].equals("let")){

						if (words[0].equals("end")){
							String last =null;
							if (local.size() >1) {
								last = local.pop();
								String next = last;
								while (!next.equals("let") && !local.isEmpty()) {
									next = local.pop();
								}
								//local.pop(); // remove let
							}
							local.push(last);

						}else if (words[0].equals("let")){
							local.push("let");
						}
					}
	                else if (words[0].equals("if")){
						String first;
						String second;
						String third;
						if (local.isEmpty()){
							local.push(":error:");
							continue;
						}
						if (local.size() <3){
							if(local.size() ==1){
								//first =  local.pop();
								local.push("::error");
							}else if (local.size() ==2){
								//first = local.pop();
								//second = local.pop();
								local.push(":error:");
							}
							continue;
						}
						else {
							first = local.pop();
							second = local.pop();
							third = local.pop();
							if (third.equals(":false:") || third.equals(":true:")) {
							//	System.out.println("third is boolean!!!");
								if (third.equals(":true:")){
									local.push(first);

								}else if (third.equals(":false:")){
									local.push(second);
								}

							}else {
								local.push(third);
								local.push(second);
								local.push(first);
								local.push(":error:");
							}
						}
					}
	                else if (words.length>=2 && (words[0].equals("push"))) {

						 if(isValidName(words[1])){
							 namesOnly.add(words[1]);
								 local.push(words[1]);

						}
						else if( words.length>2){
							//System.out.println("----------len");

							if(words[1].charAt(0)==34 && words[2].charAt(words[2].length()-1)==34){
								//System.out.println("----------inner len");
								String tem = words[1].substring(1,words[1].length()) +" "+ words[2].substring(0,words[2].length()-1);
								local.push(tem);

							}
						}
						else if(words[1].charAt(0)==34 && words[1].charAt(words[1].length()-1)==34){
							String tem = words[1].substring(1,words[1].length()-1);
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
								//System.out.print("errrrrrrrrrrrrro");
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


	                }else if (words[0].equals("not")){
						if (!local.isEmpty()){
							String first = local.pop();

							if(isValidBoolean(first,null) ) {
								if (first.equals(":false:")) {
									local.push(":true:");
								} else if (first.equals(":true:")) {
									local.push(":false:");
								}
							}else {
								local.push(first);
								local.push(":error:");
							}
						}else {
							//local.push(first);
							local.push(":error:");
						}
					}
	                else if (words[0].equals("or") || words[0].equals("and")){
						if(local.isEmpty()){
							local.push(":error:");
						    continue;

						}else  if (local.size()==1){
							local.push(":error:");
						    continue;
						}
						else {
							String first = local.pop();
							String second = local.pop();

							if (isValidBoolean(first, second)) {

								if(words[0].equals("and")){

									if(first.equals(":true:") && second.equals(":true:")){
										local.push(":true:");
									}else {
										local.push(":false:");

									}
								}else if(words[0].equals("or")){
									     if((first.equals(":true:") || second.equals(":false:")) ||
											(first.equals(":false:") || second.equals(":true:")) ||
											(first.equals(":true:") && second.equals(":true:"))){
										local.push(":true:");

									}else {
										local.push(":false:");
									}
								}

							}else {
								System.out.println("not validdddddddddd");
								System.out.print(first + "  ");
								System.out.print(second);
								local.push(second);
								local.push(first);
								local.push(":error:");
							}

						}
					}
	                else if(words[0].equals("neg")){

	                    if(!local.isEmpty()){
	                        String tem = local.pop();
	                        if(isNumber(tem)) {
								String ret = neg(tem);
								local.push(ret);
							}
	                        else {
								local.push(tem);
								local.push(":error:");
							}

	                    }else
	                    	local.push(":error:");

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
	    static  boolean isValidName(String name){
			String regex = "^[a-zA-Z0-9]+$";

			if (name.matches(regex)) {
				return true;
			}
			return false;

		}
	static  boolean isValidBoolean(String first,  String second){
		if (second==null){
			if (first.equals(":true:") || (first.equals(":false:"))){
				return true;
			}else {
				return false;
			}
		}
		else{
			if ((first.equals(":true:") || first.equals(":false:")) && (second.equals(":false:") || second.equals(":true:"))) {
				//System.out.println ("one is not boolean valid");

				return true;
			}
		}
		return false;

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


		/*while (!l.isEmpty()){
			System.out.println(l.pop()+ " ");
		}*/

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
			System.out.println(writeTo);
	        pr.println(writeTo);
	    }
	    pr.close();
	}

	/*public  static void main(String args[]){
		interpreter in = new interpreter();
		String input ="C:/Users/kemok/Desktop/Spring2017/CSE 305/home_work/CSE305-Programing-Languages/cse305/input_2.txt";
	    String out ="C:/Users/kemok/Desktop/Spring2017/CSE 305/home_work/CSE305-Programing-Languages/cse305/output.txt";

		in.interpreter(input,out);
	}*/



}