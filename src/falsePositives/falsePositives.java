package falsePositives;

import hello.bloomfilter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

class falsePositives {
    private int m = 200000;
    private float fp[] = new float[100];
    private String[] names = new String[2000]; 
        
    public void setNames() {
        Scanner scanner;
        int i = 0;
		try {
			scanner = new Scanner(new File("data/CSV_Database_of_First_Names.txt"));
	        while(scanner.hasNext()){
                if (i < 1000) {
                    names[i] = scanner.next();
                    i += 1;
                }
                else {
                    break;
                }
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        for(int ii=0;ii<1000;ii++){
        	names[ii+1000]=Integer.toString(ii+1000);
        }
    }
    
    public void prepareBF(bloomfilter bf) {
        Scanner scanner;
		try {
			scanner = new Scanner(new File("data/CSV_Database_of_First_Names.txt"));
	        while(scanner.hasNext()){
	        	int hashed = scanner.next().hashCode();
	        	bf.add(hashed);
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
        }
    }
    
    public void varyK() {
        for(int k=1; k<=100; k++) {
            bloomfilter bf = new bloomfilter(m,k);
            prepareBF(bf);
            fp[k-1] = checkFalsePositives(bf);
        }
        try{
            PrintWriter writer = new PrintWriter("data/output.txt", "UTF-8");
            for(int i=0; i<100; i++) {
            	writer.println(fp[i]);
            }
            writer.close();
        } catch (IOException e) {
           System.out.println(e);
        }
}
    
    public float checkFalsePositives(bloomfilter bf) {
        int count = 0;
        for(int i=0; i<2000; i++) {
            int hashed = names[i].hashCode();
            if(bf.check(hashed)) {
                count += 1;
            }
        }
        return ((float)count-1000)/1000;
    }
    
    public static void main (String[] args) {
        falsePositives probFP = new falsePositives();
        probFP.setNames();
        probFP.varyK();
    }
}