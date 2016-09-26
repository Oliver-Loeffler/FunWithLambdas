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

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.junit.Test;

import net.raumzeitfalle.fun.Ewma.EwmaBuilder;

public class EwmaTest {
	
	Ewma classUnderTest = (Ewma) EwmaBuilder.withLambda(0.2);
	
	@Test
	public void test() {
		EwmaBuilder builder = new EwmaBuilder();
		UnaryOperator<Double> ewma = builder.buildWith(0.2, 0.0);
		
		Double result = ewma.apply(Double.valueOf(1.0));
		assertEquals(0.2, result.doubleValue(), 0.01);
		
		result = ewma.apply(Double.valueOf(2.0));
		assertEquals(0.56, result.doubleValue(), 0.01);
		
		result = ewma.apply(Double.valueOf(3.0));
		assertEquals(1.048, result.doubleValue(), 0.01);
	}
	
	@Test
	public void streamTest(){
		List<Double> values = new LinkedList<Double>();
		values.add(Double.valueOf(1.0));
		values.add(Double.valueOf(2.0));
		values.add(Double.valueOf(3.0));
		
		List<Double> ewma = values.stream().map( EwmaBuilder.withLambda(0.2) ).collect(Collectors.toList());
		assertEquals( 1.048, ewma.get(2), 0.0001);
	}
	
	@Test
	public void toStringMethod() {
		assertTrue(classUnderTest.toString().contains("EWMA"));
	}
	
	@Test
	public void gettingLambda() {
		assertEquals(0.2,classUnderTest.getLambda(), 0.001);
	}
	
	@Test
	public void gettingStart() {
		assertEquals(0.0,classUnderTest.getStart(), 0.001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void lambdaWithNaNValue() {
		EwmaBuilder.withLambda(Double.NaN);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void lambdaWithInfinity() {
		EwmaBuilder.withLambda(Double.POSITIVE_INFINITY);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void lambdaGreaterOne() {
		EwmaBuilder.withLambda(10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void lambdaEqualsZero() {
		EwmaBuilder.withLambda(0);
	}

	
}
