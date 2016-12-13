package hello;

import java.util.BitSet;
import java.util.Random;

//Ideas inspired from http://stackoverflow.com/questions/4546447/bloom-filter-implementation

public class bloomfilter{
	BitSet bf;
	int m;
	int k;
	int[] hashSeeds;
	
	public bloomfilter(int num_of_bits, int num_of_HF){
		bf = new BitSet(num_of_bits);
		m = num_of_bits;
		k = num_of_HF;
		Random r = new Random(System.currentTimeMillis());
        hashSeeds = new int[num_of_HF];
        for (int i=0; i<num_of_HF; ++i) {
            hashSeeds[i] = r.nextInt();
        }
	}
	
	public void add(int value) {
        byte [] b = new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
        for (int i=0; i<hashSeeds.length; ++i) {
            int h = murmur.hash32(b, 4, hashSeeds[i]);
            bf.set(Math.abs(h)%m, true);
        }
    }

    public boolean check(int value) {
        byte [] b = new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
        for (int i=0; i<hashSeeds.length; ++i) {
            int h = murmur.hash32(b, 4, hashSeeds[i]);
            if(!bf.get(Math.abs(h)%m)) {
                return false;
            }
        }
        return true;
    }
}
