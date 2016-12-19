import hello.bloomfilter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class falsePositives {
    //static bloomfilter bf = new bloomfilter(500000,10);
    private int m = 50000;
    private float fp[] = new float[100];
    private String[] names = new String[100]; 
        
    public void setNames() {
        System.out.println("Inside the setNames");
        Scanner scanner;
        int i = 0;
		try {
			scanner = new Scanner(new File("C:\\Users\\adars\\Documents\\CDN project\\CDN_BloomFilter\\data\\CSV_Database_of_First_Names.txt"));
	        while(scanner.hasNext()){
                System.out.println(i);
                if (i < 90) {
                    names[i] = scanner.next();
                    i += 1;
                }
                else {
                    break;
                }
	            //System.out.print(scanner.next()+'/');
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        names[90] = "Adarsh";
        names[91] = "Murthy";
        names[92] = "Venkatesh";
        names[93] = "Gopalakrishnan";
        names[94] = "Abhipraya";
        names[95] = "Krishnaswamy";
        names[96] = "Sreenivasamurthy";
        names[97] = "Madhusudana";
        names[98] = "Ananykumari";
        names[99] = "Kiraneshwar";
        System.out.println("Leaving the setNames function");
        
    }
    public void prepareBF(bloomfilter bf) {
        System.out.println("Inside the prepareBF function");
        Scanner scanner;
		try {
			scanner = new Scanner(new File("C:\\Users\\adars\\Documents\\CDN project\\CDN_BloomFilter\\data\\CSV_Database_of_First_Names.txt"));
	        while(scanner.hasNext()){
	        	int hashed = scanner.next().hashCode();
	        	bf.add(hashed);
	            //System.out.print(scanner.next()+'/');
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		
        }
    }
    public void varyK() {
        System.out.println("Inside the varyK function");
        for(int k=1; k<=50; k++) {
            bloomfilter bf = new bloomfilter(m,k);
            prepareBF(bf);
            fp[k-1] = checkFalsePositives(bf);
        }
        for(int i=0; i<50; i++) {
            System.out.println(fp[i]);
        }
        //graphPvK();
}
    
    public float checkFalsePositives(bloomfilter bf) {
        System.out.println("Inside the checkFalsePositives function");
        int count = 0;
        for(int i=0; i<100; i++) {
            int hashed = names[i].hashCode();
            if(bf.check(hashed)) {
                count += 1;
            }
        }
        return ((float)count-90)/100;
    }
    /*
    public void graphPvK() {
        
    }
        
        */
    public static void main (String[] args) {
        falsePositives probFP = new falsePositives();
        probFP.setNames();
        probFP.varyK();
    }
}