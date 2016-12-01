# Fun with Streams and Lambdas
Discovering the Java8 Stream API, Lambda features and aspects of functional programming.

[![Build Status](https://travis-ci.org/Oliver-Loeffler/BootstrapPrefs.svg?branch=master)](https://travis-ci.org/Oliver-Loeffler/FunWithLambdas) [![codecov](https://codecov.io/gh/Oliver-Loeffler/BootstrapPrefs/branch/master/graph/badge.svg)](https://codecov.io/gh/Oliver-Loeffler/FunWithLambdas)  

## EWMA (exponentially weighted moving average, as a function)
As a first example of programming functions I've chosen a so called EWMA function.
See NIST Handbook of Engineering Statistics for details:

http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm

In environments where run-to-run control processes are used or as a tool in statistical process control (SPC) EWMA functions are often used to smooth steep changes in time series data or to separate process noise from systematic process variations.
I am sure there are better ways of implementation as this place here is just kind of an experimentation environment.

Example code:
https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/src/main/java/net/raumzeitfalle/timeseries/EwmaFunctions.java

The default EWMA function initializes with a lambda of 0.2 and a starting value of 0.0.
A lambda of 0.2 means, that in a time series the present has a weight of 20% whereas the history is weighted by 80%. This effectively dampes the response to steep changes.

#### EWMA with Lambda of 0.2
![Chart showing random data and EWMA smoothed data](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/EwmaWithLambda0.2.png)

#### EWMA with Lambda of 0.1
![Chart showing random data and EWMA smoothed data](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/EwmaWithLambda0.1.png)


#### EWMA with Lambda of 0.05
![Chart showing random data and EWMA smoothed data](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/EwmaWithLambda0.05.png)



## Benchmarks using Streams and Loops

Just because of interest, I ran a little benchmark test comparing a for-each loop with various parallel and sequential stream constructs using slightly different lambdas. 

https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/src/main/java/net/raumzeitfalle/streams/FindLargestNumberBenchmark.java

### Benchmark Setup:
* JVM: JavaSE8 1.8_102
* System: MacBook Pro Mitte'2015 (2.5 Ghz Core i7, 16 GByte RAM)

### Code snippets:
* Loop (for-loop)
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
		      .reduce( (a,b) -> {if (a.intValue() > b.intValue()) return a; else return b;} )
		      .get()
		      .intValue();
```

* Lambda2 (Map-Reduce, parallel, auto-unboxing)
```java
	int max = ints.stream()
		      .parallel()
		      .reduce( (a,b) -> a > b ? a : b )
		      .get()
		      .intValue(); 
```

* Lambda3 (Map-Reduce, parallel, Math.max(), auto-unboxing)
```java
   	int max = ints.stream()
	   	      .parallel()
		      .reduce( (a,b) -> Math.max(a, b) )
		      .get()
		      .intValue(); 
```

* Sequential (Map-Reduce, sequential, auto-unboxing)
```java
	int max = ints.stream()
		      .sequential()
		      .reduce( (a,b) -> a > b ? a : b )
		      .get()
		      .intValue();
```

### Benchmark Results

#### JVM with default settings

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarkDefaultJvm.png)


#### JVM with interpreter mode (compiler disabled, -Djava.compiler=NONE)

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarksInterpretedJvm.png)


#### JVM with inline optimizations disabled (-Xint)

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarkNoOptsJvm.png)
