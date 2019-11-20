package com.wanandroid.zhangtianzhu.kotlinproject.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.coroutines.CoroutineContext

fun main() {
//    val start = System.currentTimeMillis()
//    //创建协程运行在当前线程之上，阻塞当前线程直到运行结束
//    runBlocking {
//        val jobs = List(100000){
//            launch(Dispatchers.Default) {
//                //无阻塞阻塞1秒
//                delay(1000)
//                println("thread name= "+Thread.currentThread().name)
//            }
//        }
//        jobs.forEach { it.join() }
//    }
//    val spend = (System.currentTimeMillis()-start)/1000
//    println("Coroutines: spend= $spend s")

//    GlobalScope.launch {
//        val result1 = async {
//            delay(2000)
//            //这里返回一个Deferred对象，这是个返回结果为，这里为1
//            1
//        }
//
//        /**
//         * 当 start 参数使用 CoroutineStart.LAZY 值时，只有在返回的 Job 或 Deferred 对象显式调用 start()、
//         * join() 或 await() (只有 Deferred 才有 await() )时才会启动协程，这类似于懒加载。
//         */
//        val result2 = async (start = CoroutineStart.LAZY){
//            delay(1000)
//            2
//        }
//
//        val result = result1.await() + result2.await()
//        println("result = $result")
//    }
//    //让主线程睡 5秒，用来打印协程结果，否则协程结果无法打印出来
//    Thread.sleep(5000)

    /**
     * 如果您曾经用过消息队列，那么恭喜您因为 Kotlin 的 Channel 与 Java 的 BlockingQueue 类似。
     * BlockingQueue 的 put 和 take 操作，相当于 Channel 的 send 和 receive 操作，但是 BlockingQueue 是阻塞操作而 Channel 都是挂起操作。
     */
//    runBlocking {
//        //定义一个通道
//        val channel = Channel<Int>()
//        launch(Dispatchers.Default) {
//            repeat(5) { i ->
//                delay(200)
//                channel.send((i + 1) * (i + 1))
//                //channel 可以关闭，但BlockingQueue 不可以
//                if(i==2){
//                    channel.close()
//                }
//            }
//        }
//
//        launch(Dispatchers.Default) {
//            repeat(5) {
//                try {
//                    println(channel.receive())
//                }catch (e:ClosedReceiveChannelException){
//                    //channel 关闭打印异常，三次之后，关闭了channel，无法接收只能打印异常信息
//                    println("There is a ClosedReceiveChannelException")
//                }
//            }
//        }
//
//        delay(2000)
//        println("Receive Done")
//    }
//    runBlocking {
//        val numbers = produce1()
//        val squares = produce2(numbers)
//        val add = produce3(squares)
//
//        //consumeEach是ReceiveChannel的扩展函数，用来循环的处理消息
//        add.consumeEach(::println)
//
//        println("Receive Done")
//        //消息处理完，需要关闭所有produce
//        add.cancel()
//        squares.cancel()
//        numbers.cancel()
//    }
//     runBlocking {
//         //指定channel缓冲区大小
//         val channel = Channel<Int>(2)
//         launch(coroutineContext) {
//             repeat(6){
//                 //这里创建的 channel 缓冲区的大小是2，前面两个放在了缓冲区，尝试发送第三个的时候就挂起了协程
//                 //第一个协程无阻塞1秒后发送一个消息，而第二个协程无阻塞2秒，也就是等待第一个协程发送一个消息后再等1秒钟，就可以接受到消息
//                 //即使第二个协程延迟时间比第一个短，也会等待第一个协程发送完信息，才能接受到消息并进行处理
//                 delay(1000)
//                 println("Send $it")
//                 channel.send(it)
//             }
//         }
//
//         launch {
//             delay(2000)
//             repeat(6){
//                 println("Receive ${channel.receive()}")
//             }
//         }
//
//     }
    /**
     * Actor 本身就是一个协程，内部包含一个channel，通过channel与其他协程进行通信，值得注意的是，actor内部通过channel来接收消息
     */
//    runBlocking {
//        val summer = actor<Int>(coroutineContext) {
//            var sum = 0
//            // 不断接收channel中的数据，这个channel是ActorScope的变量
//            for(i in channel){
//                sum += i
//                println("Sum = $sum")
//            }
//        }
//        repeat(10){
//            summer.send(it+1)
//        }
//        summer.close()
//    }

    runBlocking {
        val ztz = produceSelect1(coroutineContext)
        val yif = produceSelect2(coroutineContext)
        repeat(10) {
            produceSelectProducts(ztz, yif)
        }
        //关闭子协程
        coroutineContext.cancelChildren()
    }
}

/**
 * 在类Unix操作系统（以及一些其他借用了这个设计的操作系统，如Windows）中，管道（英语：Pipeline）是一系列将标准输入输出链接起来的进程，
 * 其中每一个进程的输出被直接作为下一个进程的输入。 每一个链接都由匿名管道实现。管道中的组成元素也被称作过滤程序。

Channel 也参考了这个概念设计了 Pipelines 的功能。下面的代码中 produce1()、produce2()、produce3() 都相当于是一个管道，
produce2()、produce3() 的输入值，分别是前一个管道的输出值。
 */
fun produce1() = GlobalScope.produce(Dispatchers.Default) {
    //发送 1-4
    repeat(5) { i ->
        send(i)
    }
}

fun produce2(numbers: ReceiveChannel<Int>) = GlobalScope.produce(Dispatchers.Default) {
    for (i in numbers) {
        send((i * i))
    }
}

fun produce3(numbers: ReceiveChannel<Int>) = GlobalScope.produce(Dispatchers.Default) {
    for (i in numbers) {
        send(i + 1)
    }
}

fun produceSelect1(context: CoroutineContext) = GlobalScope.produce<String>(context) {
    while (true) {
        delay(400)
        send("ztz")
    }
}

fun produceSelect2(context: CoroutineContext) = GlobalScope.produce<String>(context) {
    while (true) {
        delay(200)
        send("yif")
    }
}

/**
 * Select 表达式能够同时等待多个 suspending function，然后选择第一个可用的结果。
 */
suspend fun produceSelectProducts(channel1: ReceiveChannel<String>, channel2: ReceiveChannel<String>) {
    select<Unit> {
        channel1.onReceive {
            println("This is $it")
        }

        channel2.onReceive {
            println("This is $it")
        }
    }
}
