import javax.swing.*;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by kemok on 2/25/2017.
 */
public class interpreter {
    static Stack global;

    void createStack(){
      global =  new Stack();
    }

    int add(int a, int b){
      return a+b;
    }
    int sub(int a, int b){
        return a-b;
    }
    int mul(int a, int b){
        return a*b;
    }
    int div(int a, int b){
        return a/b;
    }
    int rem(int a, int b){
        return a%b;
    }
    int neg(int a){

        String input = String.valueOf(a);
        return Integer.parseInt("-"+input);
    }
    Object[] swap(Object a, Object b){
        Object arr[] = new Object[2];
        arr[0] =a;
        arr[1] =b;
        Object tem =0;

        tem =arr[0];
        arr[0]=arr[1];
        arr[1] =tem;

        return arr;
    }
    int quite(String q){
        return 0;
    }


    static boolean numberOrNot(String input)
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





    public  void interpreter(Object obj){

        int input1 =0;
        int input2=0;
        if (global.size() > 1 ) {
            input1 = (int) global.pop();//;Integer.parseInt(stack.pop());
            input2 = (int) global.pop();

        }

        // add operation
        if(obj.equals("add")) {
            if (global.size() > 1 ) {
                int total = add(input1, input2);
                global.push(total);
            }else {
                input2 = (int) global.pop();
                global.push("error");
                global.push(input2);

            }
        }
        // multiply operation
        else if (obj.equals("mul")){
            input1 = (int) global.pop();//;Integer.parseInt(stack.pop());
            input2 = (int) global.pop();
            int total = mul(input1, input2);
            global.push(total);

        }
        //substration operation
        else if (obj.equals("sub")){
            input1 = (int) global.pop();//;Integer.parseInt(stack.pop());
            input2 = (int) global.pop();
            int total = sub(input1, input2);
            global.push(total);

        }
        // remainder operation
        else if (obj.equals("rem")){
            input1 = (int) global.pop();//;Integer.parseInt(stack.pop());
            input2 = (int) global.pop();
            int total = mul(input1, input2);
            global.push(total);

        }
        //division operation
        else if (obj.equals("div")){
            input1 = (int) global.pop();//;Integer.parseInt(stack.pop());
            input2 = (int) global.pop();
            if(input2 ==0){
                global.push("error");
                return;
            }
            int total = div(input1, input2);
            global.push(total);

        }else {
            global.push(obj);
        }

    }
    public static void main(String args[]){
        System.out.print("hello world");


    }

    private static boolean isInt( int n) {

        if(n==Math.round(n)){
          global.push(n);
            return true;

        }else{
            global.push("error");
            return false;
        }

    }
    private void string(String string){
        global.push(string);

    }
    private void name(Object name){
        global.push(name);
    }
    private void bool(boolean bool){
        global.push(bool);
    }
}
