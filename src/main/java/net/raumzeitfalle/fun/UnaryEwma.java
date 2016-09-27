/*
 * 
 * FunWithLambdas: EWMA
 *
 * Copyright (C) 2016 Oliver Löffler
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package net.raumzeitfalle.fun;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Implementation of an exponentially weighted moving average as described in the NIST Handbook of Engineering Statistics. Please refer following description for more details: <a href="http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm">http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm</a>. 
 * @author Oliver Löffler
 *
 */
public class UnaryEwma implements UnaryOperator<Double> {

	private final double lambda;
	
	private final double initial;
	
	private double previous;
	
	private final BinaryOperator<Double> function;
	
	private UnaryEwma(final double lambda, double initial){
		this.lambda = lambda;
		this.previous = initial;
		this.initial = this.previous;
		this.function = BinaryEwma.withLambda(lambda);
	}
	
	/**
	 * Creates an EWMA function according to NIST Handbook of Engineering Statistics with an EWMA0 of 0.0.
	 * @param lambda describes the depth of memory ( 1 basically ignores the past, values close to 0 weight the past stronger than the present).
	 * @return {@link UnaryOperator} of type {@link Double}
	 */
	public static UnaryOperator<Double> withLambda(double lambda){
		return new UnaryEwma(lambda, 0.0);
	}
	
	/**
	 * Creates an EWMA function according to NIST Handbook of Engineering Statistics with a custom EWMA0 denoted by parameter initial.
	 * @param lambda describes the depth of memory ( 1 basically ignores the past, values close to 0 weight the past stronger than the present).
	 * @param initial EWMA0 value used for initialization, typical value is 0 or if known, the mean of all past values.
	 * @return {@link UnaryOperator} of type {@link Double}
	 */
	public static UnaryOperator<Double> buildWith(double lambda, double initial){
		return new UnaryEwma(lambda, initial);
	}
	
	@Override
	public Double apply(Double current) {
		this.previous = function.apply(current, this.previous);
		return Double.valueOf(this.previous);
	}
	
	/**
	 * @return the first value which was used to initialize the EWMA calculation.
	 */
	public double getEWMA0() {
		return this.initial;
	}
	
	/**
	 * @return The lambda denotes how strong the past value (n-1) is weighted compared to the preset value (n). A lambda of 1 gives all the weight to (n) whereas a lambda of 0.2 gives only 20% of the weight to (n) but 80% to (n-1).
	 */
	public double getLambda() {
		return this.lambda;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Exponentially weighted moving average (EWMA) function:")
				.append(System.lineSeparator())
				.append("Reference: http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm").append(System.lineSeparator())
				.append(" - with a lambda of: " + getLambda() ).append(System.lineSeparator())
				.append(" - with EWMA_0 of: " + getEWMA0() ).append(System.lineSeparator()).toString();
	}
	
}
