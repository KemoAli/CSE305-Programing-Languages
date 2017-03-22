
fun last(xs)=
	
	case xs of 

			[] =>" "
		|
			("add"::y::xs)=>( "add")
		|
			("add"::_)=>(
						" "
					)
			(****
			(x::y::xs)=>(
				
					
				if x="push"
					then
						
						y^"\n"
					

				else if length xs=1

					then  
						if (List.all Char.isDigit (String.explode x) andalso
							List.all Char.isDigit (String.explode y)) (*check if both x and y are an int type*)
						
							then Int.toString (valOf(Int.fromString x) + valOf(Int.fromString y))   (*convert x and y to an int for arithmetic*)
							
								else ":error:"^"\n"^y^"\n"^x

				else if x= "sub"
					then 
						if (List.all Char.isDigit (String.explode x) andalso
							List.all Char.isDigit (String.explode y)) (*check if both x and y are an int type*)
						
							then Int.toString (valOf(Int.fromString x) - valOf(Int.fromString y)) (*convert x and y to an int for arithmetic*)
							
								else ":error:"^"\n"^y^"\n"^x

				else if x="mul"
					then 
						if (List.all Char.isDigit (String.explode x) andalso
							List.all Char.isDigit (String.explode y)) (*check if both x and y are an int type*)
						
							then   Int.toString (valOf(Int.fromString x) * valOf(Int.fromString y))
								
								  (*(valOf(Int.fromString x) + valOf(Int.fromString y)) convert x and y to an int for arithmetic*)
								 
								else ":error:"^"\n"^y^"\n"^x
						

				else if x="neg"
					then 
						if (List.all Char.isDigit (String.explode x))
							then "-"^x

							else	
								":error:"
				
				else if x="div"
					then 
						if (List.all Char.isDigit (String.explode x) andalso
							List.all Char.isDigit (String.explode y) andalso (valOf(Int.fromString x)) <> 0)  (*check if both x and y are an int type*)
						
							then Int.toString (valOf(Int.fromString x) div valOf(Int.fromString y))	(*valOf(Int.fromString x) x + Int.fromString x*) (*convert x and y to an int for arithmetic*)
							
								else ":error:"^"\n"^y^"\n"^x

				else if x="pop"
					then  
						if null xs
							then ":error:"
						else x
				else if x="rem"
					then 
						if (List.all Char.isDigit (String.explode x) andalso
							List.all Char.isDigit (String.explode y) andalso (valOf(Int.fromString x)) <> 0)  (*check if both x and y are an int type*)
						
							then Int.toString (valOf(Int.fromString x) mod valOf(Int.fromString y))	(*valOf(Int.fromString x) x + Int.fromString x*) (*convert x and y to an int for arithmetic*)
							
								else ":error:"^"\n"^y^"\n"^x

				else " "
			
				
			********)

		|	
				(_::xs)=>(

				""
				)

								
fun hw1(inFile : string, outFile : string)=

let
	val allChars = "abcdefghijklmnopqrstuvwxyz"
	val inStream = TextIO.openIn inFile
    val outStream = TextIO.openOut outFile
    val readLine = TextIO.inputLine inStream
	val commands = ["add","sub","div","push","pop","rem","neg"]

    fun helper(readLine : string option) =

      
	case readLine of
	    NONE => (TextIO.closeIn inStream; TextIO.closeOut outStream)
	  | 
		SOME(readLine) => (
					
					TextIO.output(outStream, last(String.tokens Char.isSpace(readLine)));
					
					helper(TextIO.inputLine inStream))
  in
    helper(readLine)
  end;

(*fun readFile filename =
    let val fd = TextIO.openIn filename
        val content = TextIO.inputAll fd handle e => (TextIO.closeIn fd; raise e)
        val _ = TextIO.closeIn fd
    in content end

fun writeFile filename content =
    let val fd = TextIO.openOut filename
        val _ = TextIO.output (fd, content) handle e => (TextIO.closeOut fd; raise e)
        val _ = TextIO.closeOut fd
    in () end*)
