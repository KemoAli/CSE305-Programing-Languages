
structure hashtable = HashTableFn(struct
	type hash_key = string
	val hashVal = HashString.hashString
	val sameKey = (op=) : string * string -> bool
	end);

 val table : string hashtable.hash_table = hashtable.mkTable(10, Fail "Not found");

fun lst(inputs)=

		let 
	
		

		fun processList(inputLines)=
				case  String.tokens Char.isSpace (hd inputLines) of 
				
					("push"::y::input)=>(if Char.contains y #"." then ":error:"::processList(tl inputLines)
										else if (Char.contains y #"-" andalso List.all Char.isDigit (String.explode (substring(y,1,(size y)-1))) andalso valOf(Int.fromString y)=0)  then "0"::processList(tl inputLines)
										else if (Char.contains y #"-" andalso List.all Char.isDigit (String.explode (substring(y,1,(size y)-1))))  then "~"^(substring(y,1,(size y)-1))::processList(tl inputLines)
								
										else if Char.contains y (chr 34) then (
																		 if (null input) then (substring(y,1, (size y)-2)::processList(tl inputLines))
																		else if length input >= 2 
																			then(
																			let
																			fun concatInput (ac:string) (input):string=
																				if null input then y^ac
																				else concatInput (ac^" "^hd(input)) (tl input)
																			in

																				concatInput ("") (input)::processList(tl inputLines)
																			end
																		
																			)
																		 
																		else (substring(y^" "^hd (input),1, (size(y^" "^hd (input))-2))::processList(tl inputLines))
																	)
										else if ((Char.contains y (#"-") orelse  List.all Char.isDigit (String.explode (substring(y,0,1)))) andalso List.all Char.isDigit (String.explode y)=false)
												then ":error:"::processList(tl inputLines)							 
										
										else y::processList(tl inputLines)
										
							)
				|
					([":false:"])=>( ":false:"::processList(tl inputLines)
										
							)
				|
					(["pop"])=>("pop"::processList(tl inputLines)
										
							)
				|
					([":true:"])=>( ":true:"::processList(tl inputLines)
										
							)
				|
					([":error:"])=>( ":error:"::processList(tl inputLines)
										
							)
				|
					(["add"])=>( 
									"add"::processList(tl inputLines)
							)
				|
					(["sub"])=>( 
									"sub"::processList(tl inputLines)
							)
				|	(["mul"])=>( 
									"mul"::processList(tl inputLines)
							)
				|	(["div"])=>( 
									"div"::processList(tl inputLines)
							)
				|	(["mod"])=>( 
									"mod"::processList(tl inputLines)
							)
				|	(["rem"])=>( 
									"rem"::processList(tl inputLines)
							)
				|

					(["neg"])=>( 
									"neg"::processList(tl inputLines)
							)
				|
					(["swap"])=>( 
									"swap"::processList(tl inputLines)
							)
				|
				
					(["if"])=>( 
									"if"::processList(tl inputLines)
							)
				|
					(["and"])=>( 
									"and"::processList(tl inputLines)
							)
				|
				
				
					(["lessThan"])=>( 
									"lessThan"::processList(tl inputLines)
							)
				|
				
					(["equal"])=>( 
									"equal"::processList(tl inputLines)
							)
				|
				
					(["not"])=>( 
									"not"::processList(tl inputLines)
							)
				|
				
					(["or"])=>( 
									"or"::processList(tl inputLines)
							)
				|
					(["bind"])=>( 
									"bind"::processList(tl inputLines)
							)
				|
					(["let"])=>( 
									"let"::processList(tl inputLines)
							)
				|
					(["end"])=>( 
									"end"::processList(tl inputLines)
							)
				|
				
				
					(["quit"])=>(print("quiting");[])
										
							
				|
				
					_ =>(
							processList(tl inputLines)						
							)
			
			fun processList2(x::y::"add"::li)= (	
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");
															
													
			
															
													in
															print ("map val for x: "^x1);
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) + valOf(Int.fromString y1))::[] 
													 )
											else if(x1="" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x) + valOf(Int.fromString y1))::[] 
													 )
											else if(y1="" andalso x1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) + valOf(Int.fromString y))::[] 
													 )
										    else(
													 
													 if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x) + valOf(Int.fromString y))::[] 
													 )
								
													end
													)
													|

				processList2(x::y::"sub"::li)= (
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
													in
															print ("map val: "^y1);
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) - valOf(Int.fromString y1))::[] 
													 )	
											else if(x1="" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x) - valOf(Int.fromString y1))::[] 
													 )
											else if(y1="" andalso x1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) - valOf(Int.fromString y))::[] 
													 )
				
												else (
														if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
															then  ":error:"::x::y::[] 
														else Int.toString (valOf(Int.fromString y) - valOf(Int.fromString x))::[]
														)
														end
														)
														|

				processList2(x::y::"mul"::li)= (
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
													in
															print ("map val: "^y1);
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) * valOf(Int.fromString y1))::[] 
													 )
											else if(x1="" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x) * valOf(Int.fromString y1))::[] 
													 )
											else if(y1="" andalso x1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) * valOf(Int.fromString y))::[] 
													 )
				
											else (if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
														then  ":error:"::x::y::[]   
														else Int.toString (valOf(Int.fromString y) * valOf(Int.fromString x))::[]
													)  
													end 
													)
													|

				processList2(x::y::"div"::li)= (
												let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
													in
															print ("map val: "^y1);
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) div valOf(Int.fromString y1))::[] 
													 )	
											else if(x1="" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x) div valOf(Int.fromString y1))::[] 
													 )
											else if(y1="" andalso x1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) div valOf(Int.fromString y))::[] 
													 )
											else (if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false)
															then ":error:"::x::y::[] 
														else if valOf(Int.fromString x) =0 
															then ":error:"::x::y::[]
													
														else
															Int.toString (valOf(Int.fromString y) div valOf(Int.fromString x))::[]
															
													) 
												end 
												)
												|

				processList2(x::y::"rem"::li)=( 
												
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
													in
															print ("map val: "^y1);
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) mod valOf(Int.fromString y1))::[] 
													 )
											else if(x1="" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x) mod valOf(Int.fromString y1))::[] 
													 )
											else if(y1="" andalso x1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else Int.toString (valOf(Int.fromString x1) mod valOf(Int.fromString y))::[] 
													 )
											else(if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false)
													then ":error:"::x::y::[] 
												else if valOf(Int.fromString x) =0 
													then ":error:"::x::y::[]
												else Int.toString (valOf(Int.fromString y) mod valOf(Int.fromString x))::[])
												end
												)|

				processList2(y::"neg"::li)= (let
															val y1 = Option.getOpt((hashtable.find table y), "");
															
															
											in
												if y1<> ""
												then(
													if (List.all Char.isDigit (String.explode y1))=false andalso (Char.contains y1 #"~")=false 
														then  ":error:"::y::[]  
													else if (Char.contains y1 #"~") then substring(y1,1,size(y1)-1)::[]
													else if valOf(Int.fromString y1)= 0
																then y1::[]
													else "~"^y1::[] 
													)
												else(
													if (List.all Char.isDigit (String.explode y))=false andalso (Char.contains y #"~")=false 
														then  ":error:"::y::[]  
													else if (Char.contains y #"~") then substring(y,1,size(y)-1)::[]
													else if valOf(Int.fromString y)= 0
																then y::[]
													else "~"^y::[]
												)
												
											end
											)   |


				processList2(x::y::"swap"::li)= (print("swapping  "^ x ^" and "^ y^"\n"); y::x::[] )   |
				
				processList2(x::y::"and"::li) =(let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
												in
													if (x1 <> "" andalso y1 <> "") 
														then (
																if ((x1 =":false:" orelse x1 = ":true:")  andalso (y1 = ":true:" orelse y1 = ":false:")) 
															 
																  then (if (x1 = ":true:" andalso y1=":true:") then ":true:"::[] 
																		else ":false:"::[])
																else 
																":error:"::x::y::[]
															)
															
												else (if ((x =":false:" orelse x = ":true:")  andalso (y = ":true:" orelse y = ":false:")) 
															then 
																if (x = ":true:" andalso y=":true:") then ":true:"::[] else ":false:"::[]
														else 
															":error:"::x::y::[]
														)
												end
													)
												|
												
				processList2(x::y::"or"::li) =(let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
												in
													if (x1 <> "" andalso y1 <> "") 
															then (
																	if ((x1 =":false:" orelse x1 = ":true:")  andalso (y1 = ":true:" orelse y1 = ":false:")) 
																 
																	  then (if (x1 = ":false:" andalso y1=":false:") then ":false:"::[] 
																			else ":true:"::[])
																	else 
																	":error:"::x::y::[]
																)
													
													else (if (x <> ":false:" orelse x <> ":true:")  andalso (y <> ":true:" orelse y <> ":false:") 
													then 
														if (x = ":false:" andalso y=":false:") then ":false:"::[] else ":true:"::[]
													else 
														":error:"::[]
														)
												end
												)
												|
			
				processList2(x::"not"::li) =(
											let
															val x1 = Option.getOpt((hashtable.find table x), "");
																
															
											in
													if (x1 <> "") 
															then (
																	if (x1 =":false:" orelse x1 = ":true:")
																 
																	  then (if (x1 = ":true:") then ":false:"::[] 
																			else ":true:"::[])
																	else 
																	":error:"::x::[]
																)
				
												else (if (x = ":false:" orelse x =":true:") 
												then 
													if (x = ":false:") then ":true:"::[] else ":false:"::[]
												else 
													":error:"::[]
													)
											end
											)
												|
				processList2(x::y::"equal"::li)= (
												
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
													in
															
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else(
													if (valOf(Int.fromString x1) = valOf(Int.fromString y1)) then ":true:"::[] else ":false:"::[])
													 )
											else if(x1 =""  andalso y1<>"")then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else(
													if (valOf(Int.fromString x) = valOf(Int.fromString y1)) then ":true:"::[] else ":false:"::[])
													 )
											else if(y1 =""  andalso x1<>"")then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else(
													if (valOf(Int.fromString x1) = valOf(Int.fromString y)) then ":true:"::[] else ":false:"::[])
													 )
											
											else(  
													if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else 
														if (valOf(Int.fromString x) = valOf(Int.fromString y)) then ":true:"::[]
														 else ":false:"::[] ) 
															
														end
														)|
				processList2(x::y::"lessThan"::li)= (
												
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");	
															
													in
															
													
											if (x1<> "" andalso y1 <> "") then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else(
													if (valOf(Int.fromString x1) < valOf(Int.fromString y1)) then ":true:"::[] else ":false:"::[])
													 )
											else if(x1 =""  andalso y1<>"")then (
			
												if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y1))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else(
													if (valOf(Int.fromString x) = valOf(Int.fromString y1)) then ":true:"::[] else ":false:"::[])
													 )
											else if(y1 =""  andalso x1<>"")then (
			
												if(((List.all Char.isDigit (String.explode x1))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
													then  ":error:"::x::y::[] 
													else(
													if (valOf(Int.fromString x1) = valOf(Int.fromString y)) then ":true:"::[] else ":false:"::[])
													 )
											else(if(((List.all Char.isDigit (String.explode x))=false  orelse  (List.all Char.isDigit (String.explode y))=false) andalso (Char.contains y #"~")=false andalso (Char.contains x #"~")=false) 
														then  ":error:"::x::y::[] 
														else 
														if (valOf(Int.fromString x) > valOf(Int.fromString y)) then ":true:"::[]
														 else ":false:"::[] )
																
														end 
														)|
				processList2(x::y::z::"if"::li)= (
												
													let
															val x1 = Option.getOpt((hashtable.find table x), "");
															val y1 = Option.getOpt((hashtable.find table y), "");
															val z1 = Option.getOpt((hashtable.find table z), "");	
															
													in
															print ("map val: "^y1);
													
											if (z1<> "") then (
															if (z1 =":true:" orelse z1 = ":false:") 
															then  
																if z1=":true:" then x1::[] else y1::[]
													else ":error:"::[] 
			
													 )
											else(if (z =":true:" orelse z = ":false:") 
															then  
																if z=":true:" then x::[] else y::[]
													else ":error:"::[] 
													)
													end
													
													)  |
				processList2(x::y::"bind"::li)=(
													if x=":error:"
														then ":error:"::x::y::[]
													else if(y=":error:")
															then ":error:"::[]
													else(
														hashtable.insert table (y,x);
														":unit:"::[]
													)
												)
												|

				
				processList2(x::li)= (x::li) |
			

				processList2([])= ([] )
				


	    fun proc' (acc:string list) (lis: string list):string list=
		
				case (lis) of []=> acc
				| 
				 ("add"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"add"::acc)@List.drop(acc,2)) (lis))

				|
				 ("bind"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) 
							else( 
									
									let
										val value = hd(acc);
										val key = hd(tl acc)
									
									in
										
										proc'(processList2(value::key::"bind"::acc)@List.drop(acc,2)) (lis)
									end
									
								)
								)
				 
				|
				("let"::lis)=>(let
									fun procesLetEndExpr(ac)(i:int)(letList):string=
									
										if hd letList ="end" then(print("\n returiing in let "^(Int.toString(i)^hd ac)^"\n"); (Int.toString(i)^hd ac))
										else if (hd letList = "add") then procesLetEndExpr (hd (processList2(hd(ac)::(hd(tl ac))::"add"::ac))::ac) (i+1) (tl letList)
										else if (hd letList = "sub") then procesLetEndExpr (hd (processList2(hd(ac)::(hd(tl ac))::"sub"::ac))::ac) (i+1) (tl letList)
										else if (hd letList = "div") then procesLetEndExpr (hd (processList2(hd(ac)::(hd(tl ac))::"div"::ac))::ac) (i+1) (tl letList)
										else if (hd letList = "mul") then procesLetEndExpr (hd (processList2(hd(ac)::(hd(tl ac))::"mul"::ac))::ac) (i+1) (tl letList)
										else if (hd letList = "bind") then procesLetEndExpr (hd (processList2(hd(ac)::(hd(tl ac))::"bind"::ac))::ac) (i+1) (tl letList)
										else if (hd letList = "swap") then( procesLetEndExpr (processList2(hd(ac)::(hd(tl ac))::"swap"::ac)@ac) (i+1) (tl letList))
										
										else (
												print("\nhead in let is "^(hd letList)^"\n");
														procesLetEndExpr((hd letList)::ac)(i+1) (tl letList)
											)
											
								
								val ret = procesLetEndExpr(acc)(1)(lis);
								val count = Int.fromString(substring(ret, 0,1));
								val retVal = substring(ret,1,(size ret)-1)
			
								in
								  print("ret val "^ retVal);
								 proc'(retVal::[]) (List.drop(lis, Option.getOpt(count,0)))
								
								end
								)
				|
				
				 ("sub"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"sub"::acc)@List.drop(acc,2)) (lis))
				|
				 ("mul"::lis) => (if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"mul"::acc)@List.drop(acc,2)) (lis))
				|
				 ("div"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"div"::acc)@List.drop(acc,2)) (lis))
				|
				("swap"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"swap"::acc)@List.drop(acc,2)) (lis))
				|
				("rem"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"rem"::acc)@List.drop(acc,2)) (lis))
				|
				("and"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"and"::acc)@List.drop(acc,2)) (lis))
				|
				("or"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"or"::acc)@List.drop(acc,2)) (lis))
				|
				("if"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::(hd(tl(tl acc)))::"if"::acc)@List.drop(acc,3)) (lis))
				|
				("lessThan"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"lessThan"::acc)@List.drop(acc,2)) (lis))
				|
				("equal"::lis) =>(if (length acc) < 2 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::(hd(tl acc))::"equal"::acc)@List.drop(acc,2)) (lis))
				|
				("neg"::lis) =>(if (length acc) < 1 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::"neg"::acc)@List.drop(acc,1)) (lis))
				|
				("not"::lis) =>(if (length acc) < 1 then (proc'(":error:"::acc) (lis)) else proc'(processList2(hd(acc)::"not"::acc)@List.drop(acc,1)) (lis))
				|
				("pop"::lis) =>(if (length acc) < 1 then (proc'(":error:"::acc) (lis)) else proc'(List.drop(acc,1)) (lis))
				|
				  (x::lis)      =>(if x="end" then proc'(acc) (lis) else proc'(x::acc) (lis))
				
				
	
			
		in
			
			
			(proc'([])(processList(inputs)))
			
		end;


								

fun interpreter(inFile : string, outFile : string)= 
	
	let
	
		val inStream = TextIO.openIn inFile
		val outStream = TextIO.openOut outFile
		val readLine = TextIO.inputLine inStream
		val ret=[]
	
				  
		fun helper(readLine: string option) =
		  if readLine =NONE  then (TextIO.closeIn inStream; [])
			else
				(Option.getOpt(readLine, "")::helper(TextIO.inputLine inStream))
				
					
		fun helper2(ass) = if null ass then " "
							else if (Char.contains (hd(ass)) #"~") then (TextIO.output(outStream, ("-"^substring(hd(ass),1, size(hd(ass))-1)^"\n")); helper2(tl ass))
							else if Char.contains ass (chr 34)	then (TextIO.output(outStream, (substring(ass,1, size(ass)-1)^"\n"))); helper2(tl ass))
							else (TextIO.output(outStream, hd(ass)^"\n"); helper2(tl ass));

	


					
	  in

		helper2(lst(helper(readLine)));
	
		
		TextIO.closeOut outStream
		
	  end

