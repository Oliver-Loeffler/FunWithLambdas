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

import java.util.function.UnaryOperator;

public class Ewma implements UnaryOperator<Double> {

	private final double lambda;
	
	private final double initial;
	
	private double previous = 0.0;
	
	private Ewma(final double lambda, double initial){
		this.lambda = lambda;
		this.previous = initial;
		this.initial = this.previous;
	}
	
	public static class EwmaBuilder { 
		public static UnaryOperator<Double> withLambda(double lambda){
			return new Ewma(lambda, 0.0);
		}
		
		public UnaryOperator<Double> buildWith(double lambda, double initial){
			return new Ewma(lambda, initial);
		}
	}
	
	@Override
	public Double apply(Double t) {
		Double result = Double.valueOf( lambda * t.doubleValue() + (1-lambda)* this.previous);
		this.previous = result.doubleValue();
		return result;
	}
	
	public double getStart() {
		return this.initial;
	}
	
	public double getLambda() {
		return this.lambda;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Exponentially weighted moving average (EWMA) function:")
				.append(System.lineSeparator())
				.append("Reference: http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm").append(System.lineSeparator())
				.append(" - with a lambda of: " + getLambda() ).append(System.lineSeparator())
				.append(" - with EWMA_0 of: " + getStart() ).append(System.lineSeparator()).toString();
		
	}
	
}
