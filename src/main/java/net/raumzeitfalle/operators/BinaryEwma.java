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
package net.raumzeitfalle.operators;

import java.util.function.BinaryOperator;

/**
 * Implementation of an exponentially weighted moving average (EWMA) according to NIST proposal.
 * @see <a href="http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm">NIST Handbook of Engineering Statistics</a>
 * @author Oliver Löffler
 *
 */
public class BinaryEwma implements BinaryOperator<Double>{

    private final double lambda;

    /**
     * @return an EWMA function with default lambda of 0.2.
     */
    public static BinaryOperator<Double> get(){
	return new BinaryEwma(0.2);
    }

    /**
     * @param Lambda denotes how strong the past value (n-1) is weighted compared to the preset value (n). A lambda of 1 gives all the weight to (n) whereas a lambda of 0.2 gives only 20% of the weight to (n) but 80% to (n-1).
     * @return an EWMA function with default lambda of 0.2.
     */
    public static BinaryOperator<Double> withLambda(double lambda){
	return new BinaryEwma(lambda);
    }

    private BinaryEwma(double lambda){
	throwExceptionWhenLambdaIsInvalid(lambda);
	this.lambda = lambda;
    }

    @Override
    public Double apply(Double t, Double u) {
	return Double.valueOf(lambda * t.doubleValue() + (1-lambda) * u.doubleValue());
    }

    private void throwExceptionWhenLambdaIsInvalid(final double lambda) {
	if (Double.isNaN(lambda) || Double.isInfinite(lambda) || lambda <= 0 || lambda > 1) {
	    throw new IllegalArgumentException("The constant lambda must be greater 0 and less than or eqal to 1. Given was a lambda of " + lambda +".");
	}
    }

}
