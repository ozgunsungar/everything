// Client2 class that
// sends data and receives also

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Client {
    static int m;
    public static void main(String args[]) throws Exception {
        ArrayList<String> wordList = new ArrayList<>();
        Socket s = new Socket("localhost", 888);
        if(s.isConnected()){
            System.out.println("You have 30 seconds to write a word, Please enter to 'basla' to play ");
        }
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        String str, str1;
        while (true) {
            long start = System.currentTimeMillis();
            int k = wordList.size();
            String control = "baba";
            if(k!=0){
                control = wordList.get(k-1);}
            m = control.length();
            do{
                str = kb.readLine();
                if(wordList.contains(str)){
                    System.out.println("try again");
                }
                else if((str.charAt(1)!= control.charAt(m-1))||(str.charAt(0)!= control.charAt(m-2))){
                    System.out.println("try again");
                }
                else{
                    wordList.add(str);
                    long end = System.currentTimeMillis();
                    if(end-start>30000){
                        System.out.println("time out! You lose");
                        dos.writeBytes("You win");
                        dos.close();
                        br.close();
                        kb.close();
                        s.close();
                        System.exit(0);
                    }
                }
            } while (wordList.size()==k);
            dos.writeBytes(str + "\n");
            str1 = br.readLine();
            if (str1.equals("You win")){
                System.out.println("Other player can not write a word within 30 seconds \nYou win");
                dos.close();
                br.close();
                kb.close();
                s.close();
                System.exit(0);
            }
            wordList.add(str1);
            System.out.println(str1);
        }
    }
}
