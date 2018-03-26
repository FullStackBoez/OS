import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zadaca5 {
    public static void main(String [] args){
        String path="rezultati.csv";
        List<Student> studenti=new ArrayList<>();
        try{
            BufferedReader bf=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            StringBuilder sb=new StringBuilder();
            String [] s=bf.readLine().split(",");

            while((line=bf.readLine())!=null){
                studenti.add(new Student(line.split(",")));
                System.out.println(studenti.get(studenti.size()-1));
            }
            double [] prosek=new double[s.length-1];
            for(int j=0;j<studenti.size();j++){
                for(int k=0;k<prosek.length;k++){
                    prosek[k]+=studenti.get(j).getOceni(k);
                }
            }
            for(int k=0;k<prosek.length;k++)
                System.out.println(s[k+1]+" : "+String.format("%.2f",(double)prosek[k]/studenti.size()));
            zapisi(s,studenti);
            bf.close();
        }catch (Exception e){
            System.out.println("Error!");
        }
    }
    private static void zapisi(String []p,List<Student>s) throws IOException{
        FileWriter fw=new FileWriter("rezultati.tsv");
        String pri=p[0];
        for(int i=0;i<p.length-1;i++)
            pri+="\t"+p[i+1];
        fw.write(pri+"\n");
        for(Student st:s)
            fw.write(st.writer()+"\n");
        fw.flush();
        fw.close();
    }
}
class Student{
    private String index;
    private String[] oceni;
    public Student(String [] i){
        index=i[0];
        oceni=new String[i.length-1];
        for(int j=0;j<i.length-1;j++) {
            oceni[j] = i[j + 1];
        }
    }

    public int getOceni( int i) {
        return Integer.parseInt(oceni[i]);
    }
    public String writer(){
        String str="";
        for(String se:oceni)
            str+="\t"+se;
        return index+str;
    }
    @Override
    public String toString(){
        int num=0;
        for(int i=0;i<oceni.length;i++)
            num+=Integer.parseInt(oceni[i]);
        return String.format("%s prosek: %.2f",index,(double)num/oceni.length);
    }
}
