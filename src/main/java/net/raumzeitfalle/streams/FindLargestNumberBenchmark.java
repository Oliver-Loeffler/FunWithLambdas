package net.raumzeitfalle.streams;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FindLargestNumberBenchmark {
    
    private static List<Integer> results = new LinkedList<>();
    
    public static void main(String[] args){
	System.out.println("Starting test...");
	Random random = new Random();
	List<Integer> ints = new LinkedList<>();
	for (int i = 0; i < 2_000_000_0; i++){
	    ints.add( Integer.valueOf(random.nextInt()) );
	}
	System.out.println( ints.size() + "\t Integers generated.");
	System.out.println("Loop:    for-each loop iterating over a list of Integers with manual unboxing");
	System.out.println("Lambda1 parallel;    (a,b) -> {if (a.intValue() > b.intValue()) return a; else return b;}");
	System.out.println("Lambda2 parallel:    (a,b) -> (a,b) -> a > b ? a : b");
	System.out.println("Lambda3 parallel:    (a,b) -> Math.max(a, b)");
	System.out.println("Sequential:          (a,b) -> a > b ? a : b");
	
	System.out.println("n\tLoop\tLambda1\tLambda2\tLambda3\tSequential");
	for (int i = 0; i < 50; i++){
	    System.out.print( i + "\t" + classicalLoop(ints) + "\t");
	    System.out.print( mapReduceLambda(ints) + "\t");
	    System.out.print( mapReduceSimplerLambda(ints) + "\t");
	    System.out.print( mapReduceWithMath(ints) + "\t");
	    System.out.println( mapReduceSimplerLambdaSeq(ints) + "\t");    
	}
	
	System.out.println("Results collected: " + results.size());
	
    }

    private static long classicalLoop(List<Integer> ints) {
	long start = System.currentTimeMillis();
	int max = ints.get(0).intValue();
	for (Integer i : ints){
	    if (i.intValue() > max){
		max = i.intValue();
	    }
	}
	results.add(max);
	return System.currentTimeMillis() - start;
    }
    
    private static long mapReduceLambda(List<Integer> ints) {
	long start = System.currentTimeMillis();
	int max = ints.stream().parallel().reduce((a,b) -> {if (a.intValue() > b.intValue()) return a; else return b;}).get().intValue();
	results.add(max);
	return System.currentTimeMillis() - start;
    }
    
    private static long mapReduceSimplerLambda(List<Integer> ints) {
	long start = System.currentTimeMillis();
	int max = ints.stream().parallel().reduce((a,b) -> a > b ? a : b).get().intValue(); 
	results.add(max);
	return System.currentTimeMillis() - start;
    }
    
    private static long mapReduceWithMath(List<Integer> ints) {
   	long start = System.currentTimeMillis();
   	int max = ints.stream().parallel().reduce((a,b) -> Math.max(a, b)).get().intValue(); 
   	results.add(max);
   	return System.currentTimeMillis() - start;
       }
    
    private static long mapReduceSimplerLambdaSeq(List<Integer> ints) {
	long start = System.currentTimeMillis();
	int max = ints.stream().sequential().reduce((a,b) -> a > b ? a : b).get().intValue();
	results.add(max);
	return System.currentTimeMillis() - start;
    }
}
