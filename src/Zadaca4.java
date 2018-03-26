import java.io.*;
import java.util.Scanner;

public class Zadaca4 {
    public static void main(String []args){
        String path;
        String word;
        Scanner sc=new Scanner(System.in);
        System.out.println("Vnesete pat do fajlot: ");
        path=sc.nextLine();
        System.out.println("Vnesete go zborot sto go barate: ");
        word=sc.nextLine();
        try{
            countWord(path,word);
        }catch (Exception e){
            System.out.println("Error!");
        }
    }
    private static void countWord(String path,String word) throws FileNotFoundException,IOException{
        BufferedReader bf=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String read;
        int num=0;
        while((read=bf.readLine())!=null){
        num+=check(read,word);
        }
        bf.close();
        System.out.println(num);
    }
    private static int check(String l,String w){
        if(l.contains(w)){
            return check(l.replaceFirst(w,""),w)+1;
        }
        else return 0;
    }

}
