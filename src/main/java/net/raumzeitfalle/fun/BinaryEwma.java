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

public class BinaryEwma implements BinaryOperator<Double>{

	private final double lambda;
	
	public static BinaryOperator<Double> get(){
		return new BinaryEwma(0.2);
	}
	
	public static BinaryOperator<Double> withLambda(double lambda){
		return new BinaryEwma(lambda);
	}
	
	private BinaryEwma(double lambda){
		throwExceptionWhenLambdaIsInvalid(lambda);
		this.lambda = lambda;
	}
	
	@Override
	public Double apply(Double t, Double u) {
		return lambda * t.doubleValue() + (1-lambda) * u;
	}

	private void throwExceptionWhenLambdaIsInvalid(final double lambda) {
		if (Double.isNaN(lambda) || Double.isInfinite(lambda) || lambda <= 0 || lambda > 1) {
			throw new IllegalArgumentException("The constant lambda must be greater 0 and less than or eqal to 1. Given was a lambda of " + lambda +".");
		}
	}

}
