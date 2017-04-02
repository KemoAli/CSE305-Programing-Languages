/**
 * Created by kemok on 3/7/2017.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
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
	    static int div(int a, int b){
	         return b/a;
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
	    public static String closure(ArrayList<String> fun, String arg,String funName,
									 Stack<String> stack, HashMap<String,String> name,
									 HashMap<String,String> names, ArrayList<String> nameOnly){

			Stack<String> letEnd = new Stack<>();
			Stack <String> closure =  new Stack();
			ArrayList<String> stringOnly = new ArrayList<>();
			HashMap<String, String> outSideLet =  new HashMap<>();
            HashMap<String, String> closureBinding =  new HashMap<>();

            ArrayList <String> namesOnly =  new ArrayList<>();


			String inst;
			String coms[];

            String funtionName = funName;

            int startIndex =  fun.indexOf(funtionName);
		//	System.out.print((funtionName));
           // int endIndex =  fun.indexOf("funEnd");
          // System.out.println(startIndex +" "+ endIndex);
			String actualArg = fun.get(startIndex+1);
		//	System.out.println("argument: " +actualArg);

			for (int i =startIndex; i< fun.size(); i++){
				if(fun.get(i).equals("funEnd")){
					break;
				}
				inst=fun.get(i);
				coms = inst.split("\\s");

				if (coms[0].equals("let")){
					letEnd.push("let");
				}if(coms[0].equals("end")){
					letEnd.pop();
				}

				if (coms[0].equals("push")){
					String val = coms[1];
/*
                    if (name.containsKey(val)){
                        closure.push(name.get(val));

                    }else if (isNumber(val)) {
						closure.push(val);
					}
					*/
                   if(val.equals(actualArg)) {
                        closure.push(arg);
                    }
                    else if (isValidName(val)){
						nameOnly.add(val);
						closure.push(val);
					}
					else if( coms.length>2){
						String newWord="";
						for (int j=1; j <coms.length;j++){
							newWord = newWord+" "+coms[j];

						}
						if(newWord.charAt(1)==34 && newWord.charAt(newWord.length()-1)==34){
							String str = newWord.substring(2,newWord.length()-1);
							stringOnly.add(str);
							closure.push(str);

						}
					}else if(coms[1].charAt(0)==34 && coms[1].charAt(coms[1].length()-1)==34){
						String tem = coms[1].substring(1,coms[1].length()-1);
						stringOnly.add(tem);
						closure.push(tem);
					}
					else if (coms[1].equals("-0")) {
						closure.push("0");
					}else {
						if (checkIsInt(coms[1])) {
							closure.push(coms[1]);
						}
						else {

							closure.push(":error:");
						}
					}

				}else if (coms[0].equals("bind")){
					createBinding(closure,letEnd,outSideLet,closureBinding,namesOnly, stringOnly);
					/*createBinding(Stack<String> local,Stack<String> letEnd,
							HashMap<String,String> outSideLet,
							HashMap<String,String> names,
							ArrayList<String> namesOnly,
							ArrayList<String> stringOnly)*/


				}
				else if (coms[0].equals("add")){
					int total;
					String first = closure.pop();
                    String second  =  closure.pop();
                  // System.out.println("-------------"+first);
                    if(name.containsKey(first) && !letEnd.isEmpty()){
                        first= name.get(first);
                    }
                    if (name.containsKey(second) && !letEnd.isEmpty()){
                        second = name.get(second);
                    }

                    if(name.containsKey(first) && letEnd.isEmpty()){
                        first= outSideLet.get(first);
                    }
                    if (name.containsKey(second) && letEnd.isEmpty()){
                        second = outSideLet.get(second);
                    }


                   // System.out.println(second);
					if (isNumber(first) && isNumber(second)) {
                  //      System.out.println(first +" + " + second);
						total = add(Integer.parseInt(first), Integer.parseInt(second));
						closure.push(String.valueOf(total));
					}
					else{
						closure.push(second);
						closure.push(first);
                        //System.out.println("not numberrrrrrrrr");
						closure.push(":error:");
					}
				}
				else if(coms[0].equals(":false:") || coms[0].equals(":true:")){
					closure.push(coms[0]);
				}
				else if(coms[0].equals("pop")){
					closure.pop();
				}
				else if (coms[0].equals("rem")){
					int total;
					String first = closure.pop();
					String second  =  closure.pop();
					if (isNumber(first) && isNumber(second)) {
						total = rem(Integer.parseInt(first), Integer.parseInt(second));
						closure.push(String.valueOf(total));
					}
					else{
						closure.push(second);
						closure.push(first);
						closure.push(":error");
					}
				}
				else if (coms[0].equals("mul")) {
					int total;
					//System.out.println("calling multiply!!!!!!!!!!");

					String first = closure.pop();
                    if (closureBinding.containsKey(first)){
                        first = closureBinding.get(first);
                    }
					String second = closure.pop();
                    if (closureBinding.containsKey(second)){
                        second = closureBinding.get(second);
                    }
                    System.out.println(first);
                    System.out.println(second);
                    if (isNumber(first) && isNumber(second)) {
						System.out.println(first+ " mul "+second);
						total = mul(Integer.parseInt(first), Integer.parseInt(second));
						closure.push(String.valueOf(total));
					} else {
						closure.push(second);
						closure.push(first);
						closure.push(":error:");
                        System.out.print("none numbers");
					}
				}
				else if (coms[0].equals("div")){
					int total;
					String first = closure.pop();
					String second  =  closure.pop();
					if (isNumber(first) && isNumber(second)) {
						total = div(Integer.parseInt(first), Integer.parseInt(second));
						closure.push(String.valueOf(total));
					}
					else{
						closure.push(second);
						closure.push(first);
						closure.push(":error");
					}
				}
				else if (coms[0].equals("equal")){
				//	System.out.println("Calling equal in closure!!!!");
					String first = closure.pop();
					String second  =  closure.pop();
					if (isNumber(first) && isNumber(second)) {
						if (Integer.parseInt(first) ==Integer.parseInt(second)) {
							closure.push(":true:");
						}
						else {
				//			System.out.println("not equalll");
							closure.push(":false:");
						}
					}
					else{
						closure.push(second);
						closure.push(first);
						closure.push(":error:");
					}
				}
				else if (coms[0].equals("if")){
				//	System.out.println("calling iff in closure");
					String first;
					String second;
					String third;

					if (closure.isEmpty()){
						closure.push(":error:");
						continue;
					}
					if (closure.size() <3){
				//		System.out.println("closure is less than 3");
						closure.push(":error:");
						continue;
					}
					else {
						first = closure.pop();
						second = closure.pop();
						third = closure.pop();
						/*if (name.containsKey(first)){
							first =name.get(first);
						}
						if (name.containsKey(second)){
							second =name.get(second);
						}
						if (name.containsKey(third)){
							third =name.get(third);
						}
*/

						if (third.equals(":false:") || third.equals(":true:")) {
							//	System.out.println("third is boolean!!!");
							if (third.equals(":true:")){
								closure.push(first);

							}else if (third.equals(":false:")){
								closure.push(second);
							}

						}else {
							closure.push(third);
							closure.push(second);
							closure.push(first);
							closure.push(":error:");
						}
					}
				}
				else if (coms[0].equals("call")){
					String argument;
			//		System.out.println("calling call in closure!!!!");

                    if (closure.isEmpty() || closure.size() <2){
                        closure.push(":error:");
                        continue;
                    }
                    else {
                        funName =closure.pop();
					 // System.out.println("Fuun anme is" + funName);
                        argument= closure.pop();
                      //  System.out.println(argument);
                    }

                    if (fun.contains(funName)){
                      //  closure.push(":unit:");//   System.out.println("calling closure");
                        if (arg.equals(":error:")) {
							closure.push(arg);
							closure.push(funName);
							closure.push(":error:");
							continue;
						}
						if (fun.contains(funName)) {
                            closure.push(argument);
                            closure.push(funName);
                           String ret= funName(closure, names, fun, name, nameOnly);

						}

                    }else {
                      //  System.out.print("fun name does not exist");
				        closure.push(":error:");
                        continue;
                    }

				}
				else if (coms[0].equals("sub")){
					int total;
					String first = closure.pop();
					String second  =  closure.pop();
					total =  sub(Integer.parseInt(second),Integer.parseInt(first));
					closure.push(String.valueOf(total));
				}else if (coms[0].equals("lessThan")){

					int first = Integer.parseInt(stack.pop());
					int second  =  Integer.parseInt(stack.pop());
					if (second < first)
						closure.push(":true:");
					else
					closure.push(":false:");
				}
				else if (coms[0].equals("end") || coms[0].equals("let")){

					if (coms[0].equals("end")){
						String last =null;
						if (closure.size() >1) {
							last = closure.pop();
							String next = last;
							while (!next.equals("let") && !closure.isEmpty()) {
								next = closure.pop();
							}

						}
						closure.push(last);

					}else if (coms[0].equals("let")){
                //        System.out.println("pushing lettttt");
						closure.push("let");
					}
				}
			}
            String ret = closure.pop();
            closure.push(ret);
			return ret;
		}
	    public static void interpreter(String input, String output){

	        FileReader fr;
	        BufferedReader br;

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
			HashMap<String, String> funMap =  new HashMap<>();
			ArrayList<String> stringOnly = new ArrayList<>();
			HashMap<String, String> outSideLet =  new HashMap<>();
			ArrayList <String> namesOnly =  new ArrayList<>();

            ArrayList <String> functions =  new ArrayList<>();
            ArrayList <String> closurefunc =  new ArrayList<>();
			Stack<String> letEnd = new Stack<>();

	        try {
	            fr = new FileReader(input);
	            br = new BufferedReader(fr);

	            String line;
	            boolean isValid;// inScope=false;

	            while ((line = br.readLine())!=  null) {
	                String[] words=line.split("\\s");

                    if (words[0].equals("fun")){
						local.push(":unit:");
						if (letEnd.size() >=1)
						    closurefunc.add(words[1]); //fun name
                        if(letEnd.size()<1){
                            functions.add(words[1]);

                        }
						functions.add(words[2]); // args
                        closurefunc.add(words[2]);
                        line = br.readLine();

                        while (!line.equals("funEnd")) {

                            functions.add(line);
                            closurefunc.add(line);
                            line = br.readLine();


                        }
                        functions.add(line);
                        closurefunc.add(line);
					//	System.out.println();

						Set c = names.keySet();
						Iterator <String> keys= c.iterator();

						while (keys.hasNext()){
							String key =keys.next();
							funMap.put(key, names.get(key));

						}
                    }
                    if(line.equals("funEnd")){
 						continue;
					}
					if (words[0].equals("let")){
						letEnd.push("let");
					}if(words[0].equals("end")){
						letEnd.pop();
					}


	                if(commands.contains(words[0])  && local.size()>1){

	                    int x=0, y=0;
						String first=local.pop();
						String second = local.pop();
						String name1 =first;
						String name2 = second;
						if(names.containsKey(first) && !letEnd.isEmpty()){
							first= names.get(first);
						}
						if (names.containsKey(second) && !letEnd.isEmpty()){
							second = names.get(second);
						}

						if(names.containsKey(first) && letEnd.isEmpty()){
							first= outSideLet.get(first);
						}
						if (names.containsKey(second) && letEnd.isEmpty()){
							second = outSideLet.get(second);
						}


	                    if(isNumber(first) && isNumber(second)) {
	                         x = Integer.parseInt(first);
	                         y = Integer.parseInt(second);
	                         isValid=true;

	                    }else {
							if(names.containsKey(name2)){
								second =name2;
								//local.push(name1);

							}
							if (names.containsKey(name1)){
								//local.push(name2);
								first = name1;
							}

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
									if(x==0){
										String yVal = String.valueOf(y);
										String xVal = String.valueOf(x);
										if(names.containsKey(name2)){
											yVal=name2;

										}
										if(names.containsKey(name1)){
											xVal=name1;

										}
										local.push(yVal);
										local.push(xVal);
										local.push(":error:");
									}
									else
										local.push(String.valueOf(y/x));

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
                        createBinding(local,letEnd,outSideLet,names,namesOnly, stringOnly);
	/*
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
								if (letEnd.isEmpty()){
									if (outSideLet.containsKey(first) ) {
										val = names.get(first);
										outSideLet.put(second, val);
									}else if(outSideLet.get(first)==null && !isNumber(first)){
										if (namesOnly.contains(first)) {

										}else {
											outSideLet.put(second, first);
											//local.push(":unit:");
										}
									}else {
										outSideLet.put(second, first);
										//local.push(":unit:");
									}

								}

								if (names.containsKey(first) ) {
									val = names.get(first);
									names.put(second, val);
									local.push(":unit:");
								}else if(names.get(first)==null && !isNumber(first)){
									if (namesOnly.contains(first) && !stringOnly.contains(first)) {
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
						}
*/
					}
	                else if (words[0].equals("swap")){
	                    String toArr;
	                    String first;
	                    String second;
	                    if(local.size()>1) {
	                        first = local.pop();
	                        second = local.pop();
	                        toArr = swap(first, second);
	                        String ret[] = toArr.split("\\s");
	                        local.push(ret[1]);
	                        local.push(ret[0]);
	                    }else {
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

							}
							local.push(last);

						}else if (words[0].equals("let")){
							local.push("let");
						}
					}
					//////////////////////////////////////////////////////////////////////////////////
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
								local.push(":error:");
							}else if (local.size() ==2){
								local.push(":error:");
							}
							continue;
						}
						else {
							first = local.pop();
							second = local.pop();
							third = local.pop();
							if (names.containsKey(first)){
								first =names.get(first);
							}
							if (names.containsKey(second)){
								second =names.get(second);
							}
							if (names.containsKey(third)){
								third =names.get(third);
							}


							if (third.equals(":false:") || third.equals(":true:")) {
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
							 String newWord="";
							 for (int i=1; i <words.length;i++){
								 newWord = newWord+" "+words[i];

							 }
							if(newWord.charAt(1)==34 && newWord.charAt(newWord.length()-1)==34){
								String tem = newWord.substring(2,newWord.length()-1);
								stringOnly.add(tem);
								local.push(tem);

							}
						}
						else if(words[1].charAt(0)==34 && words[1].charAt(words[1].length()-1)==34){
							String tem = words[1].substring(1,words[1].length()-1);
				     		 stringOnly.add(tem);
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
	                        local.push(":error:");
	                    }
	                    else
	                    local.pop();


	                }else if (words[0].equals("not")){
						if (!local.isEmpty()){
							String first = local.pop();

							if (names.containsKey(first)){
								first = names.get(first);
							}

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

							if(names.containsKey(second)){
								second = names.get(second);
							}
							if (names.containsKey(first)){
								first = names.get(first);
							}

							if (isValidBoolean(first, second)) {

								if(words[0].equals("and")){

									if(first.equals(":true:") && second.equals(":true:")){
										local.push(":true:");
									}else {
										local.push(":false:");

									}
								}else if(words[0].equals("or")){
									     if(first.equals(":false:") && second.equals(":false:")){
										local.push(":false:");

									}else {
										local.push(":true:");
									}
								}

							}else {
								//System.out.println("not validdddddddddd")
								local.push(second);
								local.push(first);
								local.push(":error:");
							}

						}
					}
	                else if(words[0].equals("neg")){

	                    if(!local.isEmpty()){
	                        String tem = local.pop();
							if (names.containsKey(tem)){
								tem = names.get(tem);
							}
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
////////////////////////////////////////////////////////////////////////////////////////////
                        /////////////////////////////////////////////////////////////////////////
	                }else if (words[0].equals("call")){
                        String funName="";
                        String arg="";
                        String retVal;
                        if (local.isEmpty() || local.size() <2){
                            local.push(":error:");

                        }
                        else {
                            funName = local.pop();
                       //     System.out.println("pushing lettttt: " + funName);

                            arg = local.pop();

                        }

                        if (names.containsKey(arg)){
                            arg= names.get(arg);
                            //   System.out.println("arg "+ arg);
                        }

                        if (functions.contains(funName)){
                         //   System.out.println("does contain " +funName);
                            if (arg.equals(":error:")){
                                local.push(arg);
                                local.push(funName);
                                local.push(":error:");
                            }else {
                                retVal = closure(functions, arg, funName, local, funMap, names, namesOnly);
                          //      System.out.println("ret= "+ retVal);
                                local.push(retVal);
                            }


                        }else if (closurefunc.contains(funName) && letEnd.size()>=1){
                            retVal = closure(closurefunc, arg, funName, local, funMap, names, namesOnly);
                            // System.out.println("ret= "+ funName);
                            local.push(retVal);
                        }
                        else {
                            //System.out.println("pushing lettttt");

                            local.push(arg);
                            local.push(funName);
                            local.push(":error:");

                        }

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
	                else {
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
static String inOutFun(){
	
}
	static void pushTrueOrFalse(Stack<String> local, String input){
		local.push(input);
	}
	static void pushError(Stack<String> local, String input){
		local.push(input);

	}
	static void createBinding(Stack<String> local,Stack<String> letEnd,
							  HashMap<String,String> outSideLet,
							  HashMap<String,String> names,
							  ArrayList<String> namesOnly,
							  ArrayList<String> stringOnly){
		String first;
		String second;
		if(local.isEmpty() || local.size() <2){
			local.push(":error:");
		}else {
			first=local.pop();
         //   System.out.println("arg first "+first);
			second = local.pop();
          //  System.out.println("arg second "+second);
			String val;


			if(isValidName(second) && !first.equals(":error:")){
				if (letEnd.isEmpty()){
					if (outSideLet.containsKey(first) ) {
						val = names.get(first);
						outSideLet.put(second, val);
					}else if(outSideLet.get(first)==null && !isNumber(first)){
						if (namesOnly.contains(first)) {

						}else {
							outSideLet.put(second, first);
							//local.push(":unit:");
						}
					}else {
						outSideLet.put(second, first);
						//local.push(":unit:");
					}

				}

				if (names.containsKey(first) ) {
					val = names.get(first);
					names.put(second, val);
					local.push(":unit:");
				}else if(names.get(first)==null && !isNumber(first)){
                    System.out.println(stringOnly.contains(first));
                    System.out.println(namesOnly.contains(first));
					if (namesOnly.contains(first) && !stringOnly.contains(first)) {
                        local.push(second);
						local.push(first);
						local.push(":error:");
                      //  System.out.println("pushing 000000");
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
		}

	}
	static String funName(Stack<String> local,HashMap<String,String> names, ArrayList<String> functions, HashMap<String,String> funMap,ArrayList<String> namesOnly){

		String funName="";
		String arg="";
		String retVal;
		if (local.isEmpty() || local.size() <2){
			local.push(":error:");

		}
		else {
			funName = local.pop();
			arg = local.pop();
		}

		if (names.containsKey(arg)){
			arg= names.get(arg);
         //   System.out.println("arg "+ arg);
		}

		if (functions.contains(funName)){
			//System.out.println("does contain " +funName);
			if (arg.equals(":error:")){
				local.push(arg);
				local.push(funName);
				local.push(":error:");
			}else {
				retVal = closure(functions, arg, funName, local, funMap, names, namesOnly);
				System.out.println("ret= "+ retVal);
                local.push(retVal);
			}


		}else {
			local.push(arg);
			local.push(funName);
			local.push(":error:");

		}

		return " ";
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


			System.out.println("the stack is:");


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

	public  static void main(String args[]) {
		interpreter in  =  new interpreter();
	String input ="/home/kemo/Desktop/CSE305/CSE305-Programing-Languages/interpreter/TestCasesPart3/input_11.txt";
	String output="/home/kemo/Desktop/CSE305/CSE305-Programing-Languages/interpreter/TestCasesPart3/output_1.txt";
	in.interpreter(input,output);
	}


}

