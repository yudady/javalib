# Exp4j Java數學表達式計算工具


> http://www.objecthunter.net/exp4j/index.html

>Exp4j是一個簡單易用的開源Java數學表達式計算工具，由德國Java開源愛好者Frank發起並持續進行維護，旨在提供對數學表達式的計算功能。


```
<dependency>
    <groupId>net.objecthunter</groupId>
    <artifactId>exp4j</artifactId>
    <version>0.4.7</version>
</dependency>
```

---

```
Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
        .variables("x", "y")
        .build()
        .setVariable("x", 2.3)
        .setVariable("y", 3.14);
double result = e.evaluate();


```


---

```
ExecutorService exec = Executors.newFixedThreadPool(1);
Expression e = new ExpressionBuilder("3log(y)/(x+1)")
        .variables("x", "y")
        .build()
        .setVariable("x", 2.3)
        .setVariable("y", 3.14);
Future<Double> future = e.evaluateAsync(exec);
double result = future.get();


```


---

```
double result = new ExpressionBuilder("2cos(xy)")
        .variables("x","y")
        .build()
        .setVariable("x", 0.5d)
        .setVariable("y", 0.25d)
        .evaluate();
assertEquals(2d * Math.cos(0.5d * 0.25d), result, 0d);


```




