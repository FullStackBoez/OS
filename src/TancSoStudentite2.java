import java.util.*;
import java.util.concurrent.Semaphore;

/**
 *
 * @author ristes + Bani57
 */
public class TancSoStudentite {
    //TODO: Definicija na globalni promenlivi i semafori
    private Semaphore sem1;
    private Semaphore sem2;
    private Semaphore sem3;
    private Semaphore parovi;
    private Semaphore stop;
    private Semaphore lockM;
    private Semaphore lockZ;
    private Semaphore lockD;
    private Semaphore lock;
    private static int maski;
   // private static int zenski;
    private static boolean flag;

    public void init() {
        //TODO: da se implementira
        sem1=new Semaphore(10);
        sem2=new Semaphore(10);
        sem3=new Semaphore(10);
        parovi=new Semaphore(3);
        stop=new Semaphore(0);
        lockM=new Semaphore(1);
        lockZ=new Semaphore(1);
        lockD=new Semaphore(1);
        lock=new Semaphore(0);
        maski=0;
       // zenski=0;
        flag=false;
    }

    class Masko extends Thread {
        //TODO: Definicija  na promenlivi za sostojbata

        public void ucestvo() throws InterruptedException {
            //TODO: Kod za mashkite
            sem1.acquire();
            show.presobleci();
            lockM.acquire();
            maski++;
            lockM.release();
            sem1.release();

            stop.acquire();
            parovi.acquire();
            lockM.acquire();
            maski--;
           /* if(lockD.availablePermits()==0){
                lockD.release();
            }*/
            lockM.release();
            show.tancuvaj();
            parovi.release();
        }

        @Override
        public void run() {
            try {
                ucestvo();
            } catch (InterruptedException e) {
                // Do nothing
            } catch (Exception e) {
                exception = e;
                hasException = true;
            }
        }

        @Override
        public String toString() {
            return String.format("m\t%d", getId());
        }
        public Exception exception = null;
    }

    class Zensko extends Thread {
        //TODO: Definicija  na promenlivi za sostojbata

        public void ucestvo() throws InterruptedException {
            //TODO: Kod za zenskite
            sem2.acquire();
            show.presobleci();
           // lockZ.acquire();
           // zenski++;
           // lockZ.release();
            sem2.release();

           // parovi.acquire();
          //  lockZ.acquire();
          //  zenski--;
          //  lockZ.release();
          //  if(flag){
             //   flag=false;
            //    lock.release();
            //}
           // else
                stop.release();
        }

        @Override
        public void run() {
            try {
                ucestvo();
            } catch (InterruptedException e) {
                // Do nothing
            } catch (Exception e) {
                exception = e;
                hasException = true;
            }
        }

        @Override
        public String toString() {
            return String.format("z\t%d", getId());
        }
        public Exception exception = null;
    }

    class Dete extends Thread {
        //TODO: Definicija  na promenlivi za sostojbata

        public void ucestvo() throws InterruptedException {
            //TODO: Kod za decata
            sem3.acquire();
            show.presobleci();
            sem3.release();

          //  lockD.acquire();
            if(maski>0){
              //  parovi.acquire();
                // lockD.release();
                stop.release();
            }
            else{
               // flag=true;
               // parovi.release();
               // lock.acquire();
              //  lockD.release();
                parovi.acquire();
                stop.acquire();
                show.tancuvaj();
                parovi.release();
                //lockD.release();
            }
        }

        @Override
        public void run() {
            try {
                ucestvo();
            } catch (InterruptedException e) {
                // Do nothing
            } catch (Exception e) {
                exception = e;
                hasException = true;
            }
        }

        @Override
        public String toString() {
            return String.format("t\t%d", getId());
        }
        public Exception exception = null;
    }

    public static void main(String[] args) {
        try {
            TancSoStudentite environment = new TancSoStudentite();
            environment.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws Exception {
        show = new Show();
        init();
        HashSet<Thread> threads = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        int BROJ_INSTANCI = scanner.nextInt();
        scanner.close();
        for (int i = 0; i < BROJ_INSTANCI; i++) {
            Zensko z = new Zensko();
            Masko m = new Masko();
            threads.add(z);
            threads.add(m);
            Dete t=new Dete();
            threads.add(t);
            t=new Dete();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            if (!hasException) {
                t.join();
            } else {
                t.interrupt();
            }
        }
        show.printStatus();

    }


    public class Show {

        public static final int BROJ_GARDEROBA = 10;
        public static final int BROJ_TEREN = 3;
        public static final int TYPE_MASKO = 1;
        public static final int TYPE_ZENSKO = 2;
        public static final int TYPE_UNKNOWN = -1;

        public Show() {
        }
        public int brojMaskiGarderoba = 0;
        public int brojZenskiGarderoba = 0;
        public int brojDetskaGarderoba = 0;
        public int brojTancuvanja = 0;
        public int maxMaskiGarderoba = 0;
        public int maxZenskiGarderoba = 0;
        public int maxDetskaGarderoba = 0;
        public int maxTancuvanja = 0;

        public void presobleci() throws RuntimeException {
            log(null, "presobleci start");
            Thread t = Thread.currentThread();
            if (t instanceof Masko) {
                synchronized (RANDOM) {
                    brojMaskiGarderoba++;
                    if (brojMaskiGarderoba > 10) {
                        exception("Ne moze da ima poveke od 10 maski vo maskata garderoba.");
                    }
                    if (brojMaskiGarderoba > maxMaskiGarderoba) {
                        maxMaskiGarderoba = brojMaskiGarderoba;
                    }
                }
                waitRandom();
                synchronized (RANDOM) {
                    brojMaskiGarderoba--;
                }
            } else if (t instanceof Zensko){
                synchronized (RANDOM) {
                    brojZenskiGarderoba++;
                    if (brojZenskiGarderoba > 10) {
                        exception("Ne moze da ima poveke od 10 zenski vo zenskata garderoba.");
                    }
                    if (brojZenskiGarderoba > maxZenskiGarderoba) {
                        maxZenskiGarderoba = brojZenskiGarderoba;
                    }
                }
                waitRandom();
                synchronized (RANDOM) {
                    brojZenskiGarderoba--;
                }
            }
            else {
                synchronized (RANDOM) {
                    brojDetskaGarderoba++;
                    if (brojDetskaGarderoba > 10) {
                        exception("Ne moze da ima poveke od 10 deca vo detskata garderoba.");
                    }
                    if (brojDetskaGarderoba > maxDetskaGarderoba) {
                        maxDetskaGarderoba = brojDetskaGarderoba;
                    }
                }
                waitRandom();
                synchronized (RANDOM) {
                    brojDetskaGarderoba--;
                }
            }
            log(null, "presobleci kraj");
        }

        public void tancuvaj() throws RuntimeException {
            log(null, "tancuvaj start");
            synchronized (RANDOM) {
                brojTancuvanja++;
                if (brojTancuvanja > BROJ_TEREN) {
                    exception("Ne moze paralelno da tancuvaat poveke od 3 para.");
                }

                if (brojTancuvanja > maxTancuvanja) {
                    maxTancuvanja = brojTancuvanja;
                }
            }
            waitRandom();
            synchronized (RANDOM) {
                brojTancuvanja--;
            }
            log(null, "tancuvaj kraj");
        }

        private void waitRandom() {
            try {
                int r;
                synchronized (RANDOM) {
                    r = RANDOM.nextInt(RANDOM_RANGE);
                }
                Thread.sleep(r);
            } catch (Exception e) {
                //do nothing
            }
        }

        private void exception(String message) {
            RuntimeException e = new RuntimeException(message);
            log(e, null);
            hasError = true;
            throw e;
        }

        public int getType() {
            Thread t = Thread.currentThread();
            if (t instanceof Masko) {
                return TYPE_MASKO;
            } else if (t instanceof Zensko) {
                return TYPE_ZENSKO;
            } else {
                return TYPE_UNKNOWN;
            }
        }

        private synchronized void log(RuntimeException e, String action) {
            Thread t = Thread.currentThread();
            if (e == null) {
                actions.add(t.toString() + "\t(a): " + action);
            } else {
                actions.add(t.toString() + "\t(e): " + e.getMessage());
            }
        }

        public synchronized void printLog() {
            System.out.println("Poradi konkurentnosta za pristap za pecatenje, mozno e nekoja od porakite da ne e na soodvetnoto mesto.");
            System.out.println("Log na izvrsuvanje na akciite:");
            System.out.println("=========================");
            System.out.println("(tip m<=>Masko, tip z<=>Zensko)");
            System.out.println("tip\tid\takcija/error");
            System.out.println("=========================");
            for (String l : actions) {
                System.out.println(l);
            }
        }

        public void printStatus() {
            if (!hasError) {
                int poeni = 25;
                System.out.println("Procesot e uspesno sinhroniziran");
                if (show.maxMaskiGarderoba == 1 || show.maxZenskiGarderoba == 1 || show.maxDetskaGarderoba == 1) {
                    System.out.println("\t-no ima maksimum eden ucesnik vo garderobata.");
                    poeni -= 5;
                }
                if (show.maxTancuvanja == 1) {
                    System.out.println("\t-no ima maksimum edna proverka vo eden moment.");
                    poeni -= 5;
                }

                System.out.println("Osvoeni poeni: " + poeni);

            } else {
                System.out.println("Procesot ne e sinhroniziran spored uslovite na zadacata");
                show.printLog();
                System.out.println("Maksimum mozni poeni: 15");
            }

        }
        private List<String> actions = new ArrayList<>();
        private boolean hasError = false;
    }

    private static final Random RANDOM = new Random();
    private static final int RANDOM_RANGE = 3;
    // Instanca od bafferot
    private Show show;
    private boolean hasException = false;
}