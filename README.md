# FunWithLambdas
Discovering the Java8 Stream API and aspects of functional programming.

[![Build Status](https://travis-ci.org/Oliver-Loeffler/BootstrapPrefs.svg?branch=master)](https://travis-ci.org/Oliver-Loeffler/FunWithLambdas) [![codecov](https://codecov.io/gh/Oliver-Loeffler/BootstrapPrefs/branch/master/graph/badge.svg)](https://codecov.io/gh/Oliver-Loeffler/FunWithLambdas)  

As a first example of programming functions I've chosen a so called EWMA function.
See NIST Handbook of Engineering Statistics for details:

http://www.itl.nist.gov/div898/handbook/pmc/section3/pmc324.htm

# Benchmarks using Streams and Loops

Just because of interest, I ran a little benchmark test comparing a for-each loop with various parallel and sequential stream constructs using slightly different lambdas. 

Following test setup:

![JVM with default settings started out of Eclipse](https://github.com/Oliver-Loeffler/FunWithLambdas/blob/master/pages/BenchmarkDefaultJvm.png)
