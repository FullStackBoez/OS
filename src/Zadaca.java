import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Zadaca {
    public static void main(String [] args){

        String path="izvor.txt";
        String despath="destinacija.txt";
        List<Integer> b=new ArrayList<>();
        try {
            InputStream reader = new FileInputStream(path);
            int by;
            while((by=reader.read())!=-1){
              b.add(by);
            }
            reader.close();
        }catch(Exception e){
            System.out.println("Error has occurred");
        }
        int i=0;
        while(i<b.size()) {
            System.out.println((char)b.get(i).intValue());
            i++;
        }
        System.out.println(b.size());
    }
}
