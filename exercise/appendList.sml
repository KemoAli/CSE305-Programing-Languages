fun append(xs: int list, ys: int list)=

	if null xs
	then ys
	else (hd xs) :: append((tl xs),ys)
			      
fun sumList(xs: int list )=

	if null xs
	then 0
	else (hd xs) + sumList(tl xs)

fun timer(x : int)=
  if x=0
  then []
  else x:: timer(x-1)

fun sum_list_pairs(li: (int *int )list)=
  if null li
  then 0
  else #1 (hd li) + #2 (hd li) + sum_list_pairs(tl li)

fun first2s(li: (int *int )list)=
  if null li
  then []
  else
      (#1 (hd li))::  first2s(tl li)
			     
fun second2s(li: (int*int) list)=
  if null li
  then []
  else (#2 (hd li)) :: second2s(tl li)

fun sumimgThruAnotherFuntion(va: (int*int) list)=
  (sumList(first2s va)) + (sumList(second2s va))

fun letExpression(ex: int)=
	let
		val t = if ex > 0 then ex else 30
		val y = t + ex + 10
	in
		if t > y then t * 2 else y * y
	end

fun letExpressionEx2()=
	let 
		val f =1
	in 
		( let 	val f=2 in f+1 	end) +(let val y= f+2 in y+1 end)
	end
(*val append = fn : int list * int list -> int list
val sumList = fn : int list -> int
val timer = fn : int -> int list
val sum_list_pairs = fn : (int * int) list -> int
val first2s = fn : (int * int) list -> int list
val second2s = fn : (int * int) list -> int list
val sumimgThruAnotherFuntion = fn : (int * int) list -> int
val it = () : unit
- sumimgThruAnotherFuntion [(2,3),(4,9)];
val it = 18 : int
- sumimgThruAnotherFuntion [(2,3),(4,9),(30,12),(10,20)];
val it = 90 : int*)


(*/////////////////////////////////////////////////////////////////////////*)
(*	nested functions *)

fun count (from: int, to: int)=
if from=to
then to::[]
else from:: count(from+1,to)

fun countIt(num: int)=
  count(1,num)


(*////////////////////////////////////////////////////////////*)

 fun countIt2(num: int)=
	let
		fun count (from: int, to: int)=
			if from=to
			then to::[]
			else from:: count(from+1,to)

	in
	    count(1,num)
	end

(*////////////////////////////////////////////////////////////*)

fun countIt3(num: int)=
	let
		fun count (from: int)=
			if from=num
			then num::[]
			else from:: count(from+1)

	in
	    count(1)
	end

fun goodMax(xs:int list) =
	if null xs
	then 0
	else 
		if null (tl xs)
		then hd xs
		else
			let
				val tail =  goodMax(tl xs)
			in
				if hd xs > tail
				then hd xs else tail

			end

