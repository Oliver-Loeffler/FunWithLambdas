# FunWithLambdas
Discovering the Java8 Stream API and aspects of functional programming.

[![Build Status](https://travis-ci.org/Oliver-Loeffler/BootstrapPrefs.svg?branch=master)](https://travis-ci.org/Oliver-Loeffler/FunWithLambdas) [![codecov](https://codecov.io/gh/Oliver-Loeffler/BootstrapPrefs/branch/master/graph/badge.svg)](https://codecov.io/gh/Oliver-Loeffler/FunWithLambdas)  

As a first example of programming functions I've chosen a so called EWMA function.
See NIST Handbook of Engineering Statistics for details:

http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm

# Benchmarks using Streams and Loops

Just because of interest, I ran a little benchmark test comparing a for-each loop with various parallel and sequential stream constructs using slightly different lambdas. 

## Benchmark Setup:
* JVM: JavaSE8 1.8_102
* System: MacBook Pro Mitte'2015 (2.5 Ghz Core i7, 16 GByte RAM)

## Code snippets:
* Loop (for-loop, eigentlich ein for-each)
```java
int max = ints.get(0).intValue();
	for (Integer i : ints){
	    if (i.intValue() > max){
		max = i.intValue();
	    }
	}
```

* Lambda1 (Map-Reduce, parallel, unboxing)
```java
	int max = ints.stream()
		      .parallel()
		      .reduce((a,b) -> {if (a.intValue() > b.intValue()) return a; else return b;})
		      .get()
		      .intValue();

```

* Lambda2 (Map-Reduce, parallel, auto-unboxing)
* Lambda3 (Map-Reduce, parallel, Math.max())
* Sequential (Map-Reduce, sequential, auto-unboxing)
	

**JVM with default settings**

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarkDefaultJvm.png)


**JVM with interpreter mode (compiler disabled, -Djava.compiler=NONE)**

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarksInterpretedJvm.png)


**JVM with inline optimizations disabled (-Xint)**

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarkNoOptsJvm.png)
