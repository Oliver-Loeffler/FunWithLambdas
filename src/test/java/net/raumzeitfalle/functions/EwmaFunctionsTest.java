package net.raumzeitfalle.functions;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import net.raumzeitfalle.functions.EwmaFunctions;

public class EwmaFunctionsTest {

	@Test
	public void getBiFunction() {
		BiFunction<Double, Double, Double> ewma = EwmaFunctions.getStatelessBiFunction(0.2);
		assertEquals( 0.56, ewma.apply(2.0, 0.2), 0.01);
	}
	
	@Test
	public void getFunction() {
		Function<Double,Double> ewma = EwmaFunctions.withLambdaAndInitial(0.2,0.0);
		assertEquals( 0.2, ewma.apply(1.0), 0.01);
		assertEquals( 0.56, ewma.apply(2.0), 0.01);
		assertEquals( 1.048, ewma.apply(3.0), 0.01);
	}
	
	@Test
	public void applyFunctionOnStream() {
		LinkedList<Double> doubles = createLinkedListOfDoubles();
		List<Double> results = doubles.stream().map( EwmaFunctions.get() ).collect( Collectors.toList() );
		assertEquals( 0.0, results.get(0), 0.01);
		assertEquals( 0.2, results.get(1), 0.01);
		assertEquals( 0.56, results.get(2), 0.01);
		assertEquals( 1.048, results.get(3), 0.01);
	}
	
	@Test
	public void applyToArrayOfDoublePrimitives() {
		double[] values = new double[]{0.0,1.0,2.0,3.0};
		double[] result = new EwmaFunctions().applyWithLambda(0.2, values);
		double[] expect = new double[]{0.0,0.2,0.56,1.048};
		assertArrayEquals(expect, result, 0.0001);
	}

	private LinkedList<Double> createLinkedListOfDoubles() {
		LinkedList<Double> doubles = new LinkedList<Double>();
		doubles.add( Double.valueOf(0.0) );
		doubles.add( Double.valueOf(1.0) );
		doubles.add( Double.valueOf(2.0) );
		doubles.add( Double.valueOf(3.0) );
		return doubles;
	}
	
	@Test(expected=IllegalArgumentException.class)
    public void lambdaWithNaNValue() {
		EwmaFunctions.getStatelessBiFunction(Double.NaN);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaWithInfinity() {
    	EwmaFunctions.getStatelessBiFunction(Double.POSITIVE_INFINITY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaGreaterOne() {
    	EwmaFunctions.getStatelessBiFunction(10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaEqualsZero() {
    	EwmaFunctions.getStatelessBiFunction(0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaLessThanZero() {
    	EwmaFunctions.getStatelessBiFunction(-1);
    }
    
    @Test
    public void stringRepresentation(){
    	assertEquals("EWMA_n = 0.35n + 0.65(n-1) with EWMA_0 = 2.0 and (n-1) = 2.0", EwmaFunctions.withLambdaAndInitial(0.35, 2.0).toString());
    }
    
    @Test
    public void instantiation() {
    	assertNotNull( new EwmaFunctions() );
    }

}
