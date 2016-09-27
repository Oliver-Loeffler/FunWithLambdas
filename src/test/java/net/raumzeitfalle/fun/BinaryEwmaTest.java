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

import static org.junit.Assert.assertEquals;

import java.util.function.BinaryOperator;

import org.junit.Test;

public class BinaryEwmaTest {

    BinaryOperator<Double> functionUnderTest = BinaryEwma.get();

    private static final double TOLERANCE = 0.0001;

    @Test
    public void apply() {
	assertEquals(0.2, functionUnderTest.apply( Double.valueOf(1.0), Double.valueOf(0.0)).doubleValue(), TOLERANCE);
	assertEquals(0.56, functionUnderTest.apply( Double.valueOf(2.0), Double.valueOf(0.2)).doubleValue(), TOLERANCE);
	assertEquals(1.048, functionUnderTest.apply( Double.valueOf(3.0), Double.valueOf(0.56)).doubleValue(), TOLERANCE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaWithNaNValue() {
	BinaryEwma.withLambda(Double.NaN);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaWithInfinity() {
	BinaryEwma.withLambda(Double.POSITIVE_INFINITY);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaGreaterOne() {
	BinaryEwma.withLambda(10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaEqualsZero() {
	BinaryEwma.withLambda(0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void lambdaLessThanZero() {
	BinaryEwma.withLambda(-1);
    }

}
