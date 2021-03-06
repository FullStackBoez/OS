import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scheduler extends Thread{
    public static Random random = new Random();
    static List<Process> scheduled = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // TODO: kreirajte 100 Process thread-ovi i registrirajte gi
        Process [] niza =new Process[100];
        for(int i=0;i<100;i++){
            niza[i]=new Process();
            register(niza[i]);
        }
        // TODO: kreirajte Scheduler i startuvajte go negovoto pozadinsko izvrsuvanje
        Scheduler sc=new Scheduler();
        sc.start();
        // TODO: Cekajte 20000ms za Scheduler-ot da zavrsi
        sc.join(2000);
        if(sc.isAlive()){
            sc.interrupt();
            System.out.println("Terminated");
        }else System.out.println("done");
        // TODO: ispisete go statusot od izvrsuvanjeto
    }

    public static void register(Process process) {
        scheduled.add(process);
    }

    public Process next() {
        if (!scheduled.isEmpty()) {
            return scheduled.remove(0);
        }
        return null;
    }

    public void run() {
        try {
            while (!scheduled.isEmpty()) {
                Thread.sleep(100);
                System.out.print(".");

                // TODO: zemete go naredniot proces
                Process p=next();
                // TODO: povikajte go negoviot execute() metod
                if(p!=null){
                    p.execute();
                    p.join();
                }
                // TODO: cekajte dodeka ne zavrsi negovoto pozadinsko izvrsuvanje

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done scheduling!");
    }
}
class Process extends Thread{

    public Integer duration;

    public Process() throws InterruptedException {
        this.duration = Scheduler.random.nextInt(1000);
    }
    public void execute() {
        System.out.println("Executing[" + this + "]: " + duration);
        // TODO: startuvajte go pozadinskoto izvrsuvanje
        this.run();
    }

    @Override
    public void run() {
        try{
            Thread.sleep(duration);
        }catch (Exception e){}
    }
}