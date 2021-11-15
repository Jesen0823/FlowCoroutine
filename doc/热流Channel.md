Channel实际上是一个并发安全的队列，它可以用来连接协程，实现不同协程的
通信。

```kotlin

fun know_channel() = runBlocking<Unit> { //this: CoroutineScope
    // 构建一个通道
	val channel = Channel<Int>()
	//生产者
	val producer = GlobalScope.launch { //this: CoroutineScope
	var i = 0
	while (true){
		delay (1000)
        // 生产者发送数据
		channel.send(++i)
		println("send $i")
    }
	//消费者接收数据
	val consumer = GlobalScope.launch { //this: CoroutineScope
		while (true){
			val element = channel.receive()
			println("receive $element")
   	 	}
    }
    joinAll(producer,consumer)
}
```

1. Channel的容量

   > Channel实际上就是一个队列，队列中一定存在缓冲区，那么一旦这个缓冲区满了，并且也一直没有人调用receive并取走函数，send就需要挂起。故意让接收端的节奏放慢，发现send总是会挂起，直到receive之后才会继续往下执行。

```kotlin
// 消费者3秒接收一个，生产者会挂起等待消费者
fun know_channel() = runBlocking<Unit> { //this: CoroutineScope
    // 构建一个通道
	val channel = Channel<Int>()
	//生产者
	val producer = GlobalScope.launch { //this: CoroutineScope
	var i = 0
	while (true){
		delay (1000)
        // 生产者发送数据
		channel.send(++i)
		println("send $i")
    }
	//消费者接收数据
	val consumer = GlobalScope.launch { //this: CoroutineScope
		while (true){
            delay(3000)
			val element = channel.receive()
			println("receive $element")
   	 	}
    }
    joinAll(producer,consumer)
}
```

2. Channel本身确实像序列，所以我们在读取的时候可以直接获取一个Channel的iterator。

   ```kotlin
   fun know_channel() = runBlocking<Unit> { //this: CoroutineScope
   	val channel = Channel<Int>(Channel.UNLIMITED)
   	val producer = GlobalScope.launch { //this: CoroutineScope
   		for (x in 1..5){
   			channel.send(element: x * x)
   			println("send ${x * x}")
       	}
   	}
   	val consumer = GlobalScope.launch { //this: CoroutineScope
   		val iterator = channel.iterator()
   		while (iterator.hasNext()){
   			val element = iterator.next ()
   			println("receive $element")
   			delay (2000)
       	}
   	}
       joinAll(producer,consumer)
   }
   ```

   

3. produce与actor

   > 用来构造生产者与消费者的便捷方法。
   > 我们可以通过produce方法启动一个生产者协程,并返回一个ReceiveChannel,其他协程就可以用这个Channel来接收数据了。反过来，我们可以用actor启动一个消费者协程。

```kotlin
fun fast_producer_channel() = runBlocking<Unit> { //this: CoroutineScope
	val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce { //this: ProducerScope<Int>
		repeat(100){
			delay(1000)
			send(it)
        }
    }
	val consumer = GlobalScope.launch { //this: CoroutineScope
		for (i in receiveChannel) {
			println("received: $i")
        }
    }

	consumer.join()
}

```



```kotlin
fun fast_consumer_channel() = runBlocking<Unit> { //this: CoroutineScope
	val sendChannel: SendChannel<Int> = GlobalScope.actor<Int> { //this: ActorScope<Int>
		while (true){
			val element = receive()
			println(element)
        }
    }

	val producer = GlobalScope.launch { //this:CoroutineScope
		for (i in 0..3){
			sendChannel.send(i)
        }
    }

	producer.join()
}
```



4. Channel的关闭

   > produce和actor返回的Channel都会随着对应的协程执行完毕而关闭,也正是这样,Channel才被称为热数据流。
   >
   > 对于一个Channel，如果我们调用了它的close方法，它会立即停止接收新元素，也就是说这时它的isClosedForSend会立即返回true。而由于Channel缓冲区的存在,这时候可能还有一些元素没有被处理完,因此要等所有的元素都被读取之后isClosedForReceive才会返回true。
   > Channel的生命周期最好由主导方来维护，建议由主导的一方实现关闭。

 

```kotlin
fun close_channel () = runBlocking<Unit> { // this: CoroutineScope
	val channel = Channel<Int>(3)
	//生产者
	val producer = GlobalScope.launch { //this: CoroutineScope
		List(3) {
			channel.send(it)
			println("send $it")
    	}
		channel.close()
		println("""close channel.
		| - ClosedForsend: ${channel.isClosedrorsend}
		| - ClosedForReceive: ${channel.isClosedForReceive}""".trimMargin())
    }
	//消费者
	val consumer = GlobalScope.launch { //this: CoroutineScope
		for (element in channel){
			println("receive $element")
			delay(1000)
        }

		println("""After Consuming.
		- ClosedForSend: ${channel.isClosedForSend}
		- ClosedForReceive: ${channel.isClosedForReceive}""".trimMargin())
    }
    joinAll(producer,consumer)
}
```



5. BroadcastChannel

   > 发送端和接收端在Channel中存在一对多的情形，从数据处理本身来
   > 讲，虽然有多个接收端，但是同一个元素只会被一个接收端读到。广播则不然，多个接收端不存在互斥行为。

```kotlin
fun test_broadcast() = runBlocking<Unit> { //this: CoroutineScope
	val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)
	val producer = GlobalScope.launch { //this: CoroutineScope
		List(size: 3){
			delay (timeMillis: 100)
			broadcastChannel.send(it)
        }
		broadcastChannel.close()
    }

    List( size: 3) { index –>
		GlobalScope.launch { //this: CoroutineScope
			val receiveChannel = broadcastChannel.openSubscription()
			for (i in receiveChannel){
				println("[#$index] received:$i")
            }
        }
	}.joinAl1()
}

```

6. 多路复用

   > 数据通信系统或计算机网络系统中，传输媒体的带宽或容量往往会大于传输单一信号的需求，为了有效地利用通信线路，希望一个信道同时传输多路信号，这就是所谓的多路复用技术（Multiplexing)。

```kotlin
private val cachePath = "C://test.cache"
private val gson = Gson ()

data class Response<T>(val value: T, val isLocal: Boolean)

// 模拟从缓存拿取数据
suspend fun Coroutinescope.getUserFromLocal (name: String) = async(Dispatchers.IO) { //this: Coroutines
	delay(1000) //故意的延迟
	File(cachePath).readText().let { gson.fromJson(it, User::class.java) }
}

// 从网络获取数据
suspend fun Coroutinescope.getUserFromRemote(name: String) = async(Dispatchers.I0){ //this: Coroutines
	userServiceApi.getUser(name)
}


fun select_await_main() = runBlocking<Unit> { //this: CoroutineScope
	GlobalScope.launch { //this: CoroutineScope
		val localRequest = getUserFromLocal("xxx")
		val remoteRequest = getUserFromRemote("yyy")
		// it就是User, onWait调用的是await方法，返回一个Response<User>,select会选择最先返回的本地/服务端结果
        val userResponse = select<Response<User>>{ //this: SelectBuilder<Response<User>>
			localRequest.onAwait{(Response(it, true)}
			remoteRequest.onAwait{ Response(it, false)}
         }
        userResponse.value?.let { println(it) }
	}.join()
}
```



7. 复用多个Channel

   > 跟await类似，会接收到最快的那个channel消息。

   ```kotlin
   // 返回最快的Channel
   fun select_channel() = runBlocking<Unit> { //this: CoroutineScope
   	val channels = listof(Channel<Int>(), Channel<Int>())
   	GlobalScope.launch { // this: CoroutineScope
   		delay (100)
   		channels[0].send(200)
       }
   	GlobalScope.launch { // this: CoroutineScope
   		delay(50)
   		channels[1].send(100)
       }
   	//跟await英似，会接收到景快的品个channel消息
   	val result = select<Int?> { //this:SelectBuilder<Int?>
   		channels.forEach { channel ->
   			channel.onReceive { it }
           }
       }
   	println(result)
   	delay(1000)
   }
   ```

   8. SelectClause

      > 怎么知道哪些事件可以被select呢?其实所有能够被select的事件都是SelectClauseN类型，包括：
      > ● SelectClause0：对应事件没有返回值，例如join没有返回值，那么onJoin就是SelectClauseN类型。使用时，onJoin的参数是一个无参函数。
      > ● SelectClause1：对应事件有返回值，前面的onAwait, onReceive都是此类情况。
      > ● SelectClause2：对应事件有返回值，此外还需要一个额外的参数，例如Channel.onSend有两个参数，第一个是Channel数据类型的值，表示即将发送的值；第二个是发送成功时的回调参数。
      >
      > 如果我们想要确认挂起函数是否支持select,只需要查看其是否存在对应的SelectClauseN类型可回调即可。

       

      ```kotlin
      fun selectClause2() = runBlocking<Unit> { // this: CoroutineScope
      	val channels = listoOf(Channel<Int>(), Channel<Int>()
      	println(channels)
      	launch (Dispatchers.IO) { //this: CoroutineScope
      		select<Unit?> { //this:SelectBuilder<Unit?>
      			launch { //this: CoroutineScope
      				delay(10)
      				channels[1].onSend(200) { sentChannel ->
      					println("sent on $sentChannel")
                      }
                  }
      
      			launch { // this: CoroutineScope
      				delay(100)
      				channels[0].onSend(100) { sentChannel ->
      					println("sent on $sentChannel")
                      }
                  }
              }
          }
      
      	GlobalScope.launch { //this:CoroutineScope
      		println(channels[0].receive())
          }
      	GlobalScope.launch { //this: CoroutineScope
      		println (channe]s [1].receive())
          }
      	delay ( timeMillis: 1000)
       }
      ```

   9. 使用Flow实现多路复用

      > 多数情况下，我们可以通过构造合适的Flow来实现多路复用的效果。

```kotlin

fun select_flow() = runBlocking<Unit> { //this:CoroutineScope
	val name = "guest"
	coroutineScope { //this:CoroutineScope
    	//两个函数
		listOf(::getUserFromLocal, ::getUserFromRemote)
		.map { function -> //遍历调用得到协程
			function.call(name)
		}.map { deferred -> // 协程得到flow
			flow { emit(deferred.await())}
		}.merge() //多个flow合并成一个flow
		.collect{ user -> //末端操作符
			println("Result: $user")
			println("collect")
     	}
    }
}
```

10. 协程并发问题

    ```kotlin
    fun no_safe_concurrent() = runBlocking<Unit> { //this:CoroutineScope
    	var count = 0
        // 开启1000个协程对数据做加法
    	List(1000) {
    		GlobalScope.launch { count++ }
    	}.joinAl1()
    	println(count) // 结果不是1000
    }
    ```

    

```kotlin
// 原子操作保证了协程处理并发的安全
fun safe_concurrent() = runBlocking<Unit> { //this:CoroutineScope
	var count = AtomicInteger (0)

    // 开启1000个协程对数据做加法
	List(1000) {
		GlobalScope.launch { count.incrementAndGet()}
	}.joinAl1()
	println(count.get()) // 结果不是1000
}
```

> **协程的并发工具:**
> 除了我们在线程中常用的解决并发问题的手段之外，协程框架也提供了一些并发安全的工具，包括：
>
> 1) Channel：并发安全的消息通道.
> 2) Mutex：轻量级锁，它的lock和unlock从语义上与线程锁比较类似，之所以轻量是因为它在获取不到锁时不会阻塞线程，而是挂起等待锁的释放。
> 3) Semaphore：轻量级信号量，信号量可以有多个，协程在获取到信号量后即可执行并发操作。当Semaphore的参数为1时，效果等价于Mutex。

```kotlin
// 协程提供的并发工具mutex
fun safe_concurrent_mutex() = runBlocking<Unit> { //this:CoroutineScope
	var count = 0
    val mutex = Mutex()
    // 开启1000个协程对数据做加法
	List(1000) {
		GlobalScope.launch {
            mutex.withLock{
            	count++ 
            }
        }
	}.joinAl1()
	println(count) // 结果是1000
}
```

```kotlin
// 协程提供的并发工具Semaphore
fun safe_concurrent_mutex() = runBlocking<Unit> { //this:CoroutineScope
	var count = 0
    val semaphore = Semaphore(1)

    // 开启1000个协程对数据做加法
	List(1000) {
		GlobalScope.launch {
            semaphore.withPermit{
            	count++ 
            }
        }
	}.joinAl1()
	println(count) // 结果是1000
}
```



```kotlin
// 避免并发的用法
fun access_outer_variable() = runBlocking<Unit> { //this: Coroutines
	var count = 0
	val result = count + List(1000){
		GlobalScope.async{ 1 }
	}.map { it.await() }.sum()
	println(result)
}
```

