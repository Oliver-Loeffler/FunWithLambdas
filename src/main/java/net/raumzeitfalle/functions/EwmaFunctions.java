package net.raumzeitfalle.functions;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Implementation of an exponentially weighted moving average (EWMA) function.
 * @see  <a href="http://www.itl.nist.gov/div898/handbook/index.htm">NIST Handbook of Engineering Statistics</a>
 * 
 * @see  <a href="http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm">EWMA Control Charts</a>
 * @author Oliver Loeffler
 *
 */
public class EwmaFunctions {
	
	/**
	 * A stateless EWMA function accepting two {@link Double} values.
	 * @param lambda weight of first over second value (e.g. 0.2 gives 20% weight to the first value and 80% weight to the second value). With a weight of 0.5 the average of both values is returned by this function. 
	 * @return {@link BiFunction} which takes and returns {@link Double} values
	 */
	public static BiFunction<Double, Double, Double> getStatelessBiFunction(final double lambda) {
		throwExceptionAtInvalidLambda(lambda);
		return (a,b) -> lambda * a + (1-lambda) * b;
	}
	
	/**
	 * @return An EWMA function with EWMA_0 = 0.0 and a lambda of 0.2.
	 */
	public static Function<Double, Double> get() {
		return withLambdaAndInitial(0.2, 0.0);
	}
	
	/**
	 * An exponentially weighted moving average (EWMA) function supporting one parameter. The result of previous calculations (n-1) is remembered and used for EWMA calculation at (n). Consequently EWMA at (n) serves as input parameter for EWMA at (n+1).<br>
	 * <br>
	 * The processing order of elements is of fundamental importance for this functions, thus using parallel streams and unsorted collections must be avoided as for these cases the processing order of elements is undetermined. In this case, EWMA results may be wrong.
     * @param lambda denotes how strong the past value (n-1) is weighted compared to the preset value (n). A lambda of 1 gives all the weight to (n) whereas a lambda of 0.2 gives only 20% of the weight to (n) but 80% to (n-1).
	 * @param ewma0 start value for EWMA calculation. E.g. for a stream of Doubles this means, when the expected result is EWMA_n (at index n) then ewma0 parameter denotes the EWMA at index n-1.
	 * @return A {@link Function} which takes and returns {@link Double} values
	 */
	public static Function<Double, Double> withLambdaAndInitial(final double lambda, final double ewma0) {
		BiFunction<Double, Double, Double> function = getStatelessBiFunction(lambda);
		return new Function<Double, Double>() {
			private final double initial = ewma0;
			private final double weight = lambda;
			private Double predecessor = Double.valueOf(ewma0);
			@Override
			public Double apply(Double t) {
				return predecessor = function.apply(t, predecessor);
			}
			@Override
			public String toString() {
			return new StringBuilder("EWMA_n = " + weight + "n + " + (1 - weight) +"(n-1) with EWMA_0 = " + initial + " and (n-1) = " + predecessor ).toString();}
		};
	}

	private static void throwExceptionAtInvalidLambda(final double lambda) {
		if (!Double.isFinite(lambda) || lambda <= 0.0 || lambda > 1.0) {
			throw new IllegalArgumentException("Lambda must be greater 0 and less than or equal to 1.");
		}
	}
	
	/**
	 * Calculates the EWMA for a time series with an ewma0=timeSerias0 (first element of time series) and given lambda.
	 * @param lambda weight of first over second value (e.g. 0.2 gives 20% weight to the first value and 80% weight to the second value). With a weight of 0.5 the average of both values is returned by this function. 
	 * @param timeSeries in form of a double[]
	 * @return filtered time series in form of a double[] 
	 */
	public double[] applyWithLambda(double lambda, double[] timeSeries) {
		Function<Double, Double> function = withLambdaAndInitial(lambda, timeSeries[0]);
		double[] ewmaFiltered = new double[timeSeries.length];
		for (int i = 0; i < ewmaFiltered.length; i++) {
			ewmaFiltered[i] = function.apply(Double.valueOf(timeSeries[i])).doubleValue();
		}
		return ewmaFiltered;
	}
}
