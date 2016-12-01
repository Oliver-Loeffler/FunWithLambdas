package net.raumzeitfalle.timeseries;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EwmaExample {
    /**
     * Simple EWMA Example    
     * @param args
     */
    public static void main(String[] args){
	System.out.println("Starting test...");
	Random random = new Random();
	List<Double> values = new LinkedList<>();
	double[] offsets = new double[]{0.5,-0.1,0.2};
	double offset = 0.0;
	for (int i = 0; i < 2_00; i++){
	    if (i > 75) offset = offsets[0];
	    if (i > 130) offset = offsets[1];
	    if (i > 150) offset = offsets[2];
	    values.add( Double.valueOf(random.nextDouble() + offset));
	}
	
	
	System.out.println("n\tValue\tEWMA applied");
	List<Double> ewma = EwmaFunctions.applyToStream(values.stream()).collect(Collectors.toList());
	int minSize = Math.min(values.size(), ewma.size());
	for (int i = 0; i < minSize; i++){
	    System.out.print( i + "\t" + values.get(i) + "\t");
	    System.out.println( ewma.get(i) + "\t");    
	}
	
    }
}
