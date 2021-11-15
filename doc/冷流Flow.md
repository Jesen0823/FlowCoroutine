### 异步流

```kotlin
suspend fun simpleFlow() = flow<Int> { //this: FlowCollector<Int>
	for (i in 1..3){
		delay (1000)
		emit(i) //发射，产生一个元素
    }
}

fun multiple_values_flow() = runBlocking<Unit> { //this: CoroutineScope
    // 收集元素
	simpleFlow().collect { value -> println(value) }
}

// flow{…}构建块中的代码可以挂起。
// 函数simpleFlow不再标有suspend修饰符。
// 流使用emit函数发射值。
// 流使用collect函数收集值。

```

1.  冷流
   Flow是一种类似于序列的冷流，flow构建器中的代码直到流被收集的时候才运行。

```kotlin
fun simpleFlow2()=flow<Int>{ //this:FlowCollector<Int>
	println("Flow started")
	for (i in 1..3) (
		delay (1000)
		emit(i)
    }
}

fun flow_cold() = runBlocking<Unit> { // this: CoroutineScope
	val flow = simpleFlow2()
	println("Calling collect..…")
	flow.collect { value -> println(value) }
	println("Calling collect again..")
	flow.collect { value -> println(value) }
}
```



2. 流的连续性

   * 流的每次单独收集都是按顺序执行的，除非使用特殊操作符。

   * 从上游到下游每个过渡操作符都会处理每个发射出的值，然后再交给末端操作符。

     ```kotlin
     fun flow_continuation() = runBlocking<Unit> ( // this: CoroutineScope
     	// 过滤属数并将其映射到字符审：
     	(1..5).asFlow().filter { 
     		println("Eilter Sit")
     		it % 2==0
     	}.map{
     		println("Map $it")
     		"string $it"
         }.collect{
     		printIn("Collect $it")
         }
     
     ```

3. 流的构建器

   * flowOf构建器定义了一个发射固定值集的流。

   * .asFlow(）扩展函数，可以将各种集合与序列转换为流。

     ```
     fun flow_builder() = runBlocking<Unit>{ //this:CoroutineScope
     	flowOf("one","two","three")
     	.onEach {
         	delay(1000) 
         }.collect {
         	value -> println(value) 
         }
     	(1..3).asFlow().collect { value -> println(value) }
     }
     ```

4. 流的上下文

   

   * 流的收集总是在调用协程的上下文中发生，流的该属性称为上下文保存。
   
     ```kotlin
     // 流的发射与收集在同一个协程
     fun simpleFlow3() = flow<Int> { // this:FlowCollector<Int>
     	println("Flow started ${Thread.currentThread().name}")
     	for (i in 1..3) {
     		delay(1000)
     		emit(i)
         }
     }
     
     fun flow_context() = runBlocking<Unit> { // this:CoroutineScope
     	simpleFlow3().collect { value -> println("Collected $value ${Thread.currentThread().name}") }
     }
     
     ```
   
     
   
   * flow{...}构建器中的代码必须遵循上下文保存属性，并且不允许从其他上下文中发射（emit）。
   
     ```kotlin
     // 错误！！
     fun simpleFlow4() =flow<Int>{ //this:FlowCollector<Int>
     	withContext(Dispatchers.IO){ //this:CoroutineScope
     		println("Flow started ${Thread.currentThread().name}")
     		for (i in 1..3){
     			delay (1000)
     			emit (i)
             }
         }
     }
     fun flow_context() = runBlocking<Unit> { // this:CoroutineScope
     	simpleFlow4().collect { value -> println("Collected $value ${Thread.currentThread().name}") }
     }
     ```
   
     
   
   * flowOn操作符，该函数用于更改流发射的上下文。

```kotlin
// 流的发射可以指定协程
fun simpleFlow5() = flow<Int> { // this:FlowCollector<Int>
	println("Flow started ${Thread.currentThread().name}")
	for (i in 1..3) {
		delay(1000)
		emit(i)
    }
}.flowOn(Dispatchers.Default)

fun flow_context() = runBlocking<Unit> { // this:CoroutineScope
	simpleFlow5().collect { value -> println("Collected $value ${Thread.currentThread().name}") }
}
```



5.  启动流
   使用launchln替换collect我们可以在单独的协程中启动流的收集。launchIn返回一个Job

```kotlin
fun flow_launch() = runBlocking<Unit> { //this: CoroutineScope
	val job = events ()
	.onEach { event -> println("Event: $event ${Thread.currentThread().name}") }
    //.collect /
}.launchIn (CoroutineScope(Dispatcherg.I0))
 ·join()

```



6. 流的取消
   流采用与协程同样的协作取消。当流在一个可取消的挂起函
   数（例如delay）中挂起的时候取消。

   ```kotlin
   fun simpleFlow3()=flow<Int>{ // this:FlowCollector<Int>
   	for (i in 1..3){
   		delay (1000)
   		println("Emitting $i")
   		emit(i)
       }
   }
   
   fun cancel_flow() = runBlocking<Unit> { // this: CoroutineScope
   	//流在超时的情况下取消并停止执行
   	withTimeoutorNul1 (2500) { //this: CoroutineScope
    		simpleFlow3().collect { value -> println(value) }
       }
   	println("Done")
   }
   ```

   * 为方便起见，流构建器对每个发射值执行附加的ensureActive检测以进行取消，即从flow{…}发出的事件是可以取消的，必须明确检测是否取消。可以通过cancellable操作符来执行此操作。

```kotlin
fun cancel_flow_check() = runBlocking<Unit> {// this: CoroutineScope
	(1..5).asFlow().cancellable().collect { value ->
		println(value)
		if (value == 3) cancel()
    }
}
```

  

7. 背压

* buffer(),并发运行流中发射元素的代码。
* conflate(),合并发射项,不对每个值进行处理。
* collectLatest(),取消并重新发射最后一个值。
  当必须更改CoroutineDispatcher时，flowOn操作符使用了相同的缓冲机制,但是buffer函数显式地请求缓冲而不改变执行上下文。

```kotlin
fun simpleFlow8() = flow<Int>{ // this:FlowCollector<Int>
	for (i in 1..3) {
		delay(100)
		emit(i)
		println("Emitting $i ${Thread.currentThread().name}")
    }
}


fun flow_back_pressure() = runBlocking<Unit> { //this:CoroutineScope
	val time = measureTimeMillis {
		simpleFlow8()
		.buffer(20)
        .collect { value ->
			//处理这个元素消耗300ms
			delay (300)
			println("Collected $value ${Thread.currentThread().name}")
        }
    }
	println("Collected in $time ms")
}
```

8. 操作符

   > 可以使用操作符转换流，就像使用集合与序列一样。
   > 过渡操作符应用于上游流，并返回下游流。
   > 这些操作符也是冷操作符，就像流一样。这类操作符本身不是挂起函数。
   > 它运行的速度很快，返回新的转换流的定义。

* 转换操作符

  ```kotlin
  suspend fun performRequest(request: Int): String {
  	delay (1000)
  	return "response $request"
  }
  
  // 将Int转换为字符串 "response $Int"
  fun transform_flow_operator() = runBlocking<Unit> { //this: CoroutineScope
  	(1..3).asFlow()
  		.map {request -> performRequest (request)}
  		.collect { value -> println(value} }
  }
  
  ```

* 限长操作符

```kotlin
fun numbers() = flow<Int>{ // this:FlowCollector<Int>
	try {
		emit (1)
		emit (2)
		println("This line will not execute")
		emit(3)
	} finally{
		println("Finally in numbers")
    }
}


fun limit_length_operator() = runBlocking<Unit> { //this: CoroutineScope
    // 只收集2个事件
	numbers().take(2).collect {value -> println (value) }
}
```

* 末端流操作符
  末端操作符是在流上用于启动流收集的挂起函数。collect是最基础的末端操作符，但是还有另外一些更方便使用的末端操作符：

  * 转化为各种集合，例如toList与toSet。
  * 获取第一个（first）值与确保流发射单个（single）值的操作符。
  * 使用reduce与fold将流规约到单个值

* 合并操作符

  ```kotlin
  // zip将两个流合并成一个流
  fun zip() = runBlocking<Unit>{  //this: CoroutineScope
  	val numbs =(1..3).asFlow()
  	val strs = flowOf("One","Two","Three")
  	numbs.zip(strs) { a, b -> "$a -> $b" }.collect { println(it) }
  }
  ```

* 展平操作符

  * flatMapConcat 连接模式,
  * flatMapMerge合并模式
  * flatMapLatest最新展平模式

* 流的完成

  ```kotlin
  fun simpleFlow2() = (1..3).asFlow()
  
  fun flow_complete() = runBlocking<Unit> {
  	try {
  		simpleFlow2().collect { println(it) }
      }finally {
  		println("Done")
      }
  }
  
  fun flow_complete_onCompletion() = runBlocking<Unit> {
  	simpleFlow2 ()
  		.onCompletion { println("Done") }
  		.collect { println(it) }
  }
  
  ```

  
