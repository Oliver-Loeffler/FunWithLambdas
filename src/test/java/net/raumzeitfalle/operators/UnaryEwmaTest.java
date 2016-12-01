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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.junit.Test;

public class UnaryEwmaTest {

    UnaryEwma functionUnderTest = (UnaryEwma) UnaryEwma.withLambda(0.2);

    private static final double TOLERANCE = 0.0001;

    @Test
    public void apply() {
	UnaryOperator<Double> ewma = UnaryEwma.buildWith(0.2, 0.0);

	Double result = ewma.apply(Double.valueOf(1.0));
	assertEquals(0.2, result.doubleValue(), TOLERANCE);

	result = ewma.apply(Double.valueOf(2.0));
	assertEquals(0.56, result.doubleValue(), TOLERANCE);

	result = ewma.apply(Double.valueOf(3.0));
	assertEquals(1.048, result.doubleValue(), TOLERANCE);
    }

    @Test
    public void useCaseWithDoubleStreams(){
	List<Double> values = new LinkedList<>();
	values.add(Double.valueOf(1.0));
	values.add(Double.valueOf(2.0));
	values.add(Double.valueOf(3.0));

	List<Double> ewma = values.stream().map( UnaryEwma.withLambda(0.2) ).collect(Collectors.toList());
	assertEquals( 1.048, ewma.get(2).doubleValue(), TOLERANCE);
    }

    @Test
    public void toStringMethod() {
	assertTrue(functionUnderTest.toString().contains("EWMA"));
    }

    @Test
    public void gettingLambda() {
	assertEquals(0.2,functionUnderTest.getLambda(), TOLERANCE);
    }

    @Test
    public void gettingStart() {
	assertEquals(0.0,functionUnderTest.getEWMA0(), TOLERANCE);
    }


}
