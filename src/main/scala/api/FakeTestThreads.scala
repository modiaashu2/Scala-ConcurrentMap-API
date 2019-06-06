package api

import java.util.Calendar
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

import requests.Response
import org.eclipse.jetty.util.thread.{ExecutorSizedThreadPool, QueuedThreadPool}
import org.junit.Test

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object FakeTestThreads extends App {
  def main() {
    println("Hello World!")
    var counter = 1
    print(Calendar.getInstance().getTime)
    val futures = new ListBuffer[Future[Response]]()
    val listOfKeys = new ListBuffer[Integer]()
    val threadPool = new ExecutorSizedThreadPool()

    while (counter != 5000) {

      for (index <- 0 to 19) {
        val y = new AtomicInteger(index * 5000 + counter)
        listOfKeys += (index * 5000 + counter)
        //        futures += Future {
        threadPool.execute(new Runnable {
          override def run(): Unit = {
            //        val url = s"http://ec2-18-188-49-219.us-east-2.compute.amazonaws.com:8090/put?key=$y&value=$y"
            val url = s"http://ec2-13-127-190-236.ap-south-1.compute.amazonaws.com:8090/put?key=$y&value=$y"
            //          val url = s"http://0.0.0.0:8090/put?key=$y&value=$y"
            //        val url = s"http://dani.serveo.net/put?key=$y&value=$y"

            val response = requests.get(url)
           // println(response.text())
          }
        })
      }
      threadPool.join()
      counter = counter + 1
    }
    print(Calendar.getInstance().getTime)
  }

  main()
}
