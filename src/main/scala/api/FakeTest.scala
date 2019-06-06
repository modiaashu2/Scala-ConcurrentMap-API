package api

import java.util.Calendar
import java.util.concurrent.atomic.AtomicInteger

import requests.Response

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object FakeTest {
  def main(args: Array[String]) {
    println("Hello World!")
    var counter = 1
    print(Calendar.getInstance().getTime)
    val futures = new ListBuffer[Future[Response]]()
    val listOfKeys = new ListBuffer[Integer]()

    while (counter != 50) {

      for(index <- 0 to 19) {
        val y = new AtomicInteger(index*5000 + counter)
        listOfKeys += (index*5000 + counter)
        futures += Future {
          //        val url = s"http://ec2-18-188-49-219.us-east-2.compute.amazonaws.com:8090/put?key=$y&value=$y"
                  val url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=$y&value=$y"
//          val url = s"http://0.0.0.0:8090/put?key=$y&value=$y"
          //        val url = s"http://dani.serveo.net/put?key=$y&value=$y"

          val response = requests.get(url)
          //        println("hi")
          response
        }
      }
      counter = counter + 1
    }

    counter = 1
    var y = counter + 1

//    while (counter != 10000) {
//      for(index <- 0 to 9) {
//        val y = new AtomicInteger(index*10000 + counter)
//        Await.result(futures(y.get() - 1), Duration.Inf)
//        //      val url = s"http://ec2-18-188-49-219.us-east-2.compute.amazonaws.com:8090/get?key=$y"
//        //      val url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=$y&value=$y"
//        val url = s"http://0.0.0.0:8090/get?key=$y&value=$y"
//        //      val url = s"http://dani.serveo.net/get?key=$y&value=$y"
//        val r = requests.get(url)
//        val text = r.text()
//        //      println(s"<H2>getVal TriggeredSome($counter)" + "...." + text)
//        if (!s"<H2>getVal TriggeredSome($counter)\n".equals(text)) {
//          print(text)
//        }
//      }
//      counter = counter + 1
//    }
    var j = 0
    for (i <- listOfKeys) {
      Await.result(futures(j), Duration.Inf)
//      val url = s"http://0.0.0.0:8090/get?key=$i&value=$i"
//      val url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/get?key=$i&value=$i"
//      //      val url = s"http://dani.serveo.net/get?key=$y&value=$y"
//      val r = requests.get(url)
//      val text = r.text()
//      //      println(s"<H2>getVal TriggeredSome($counter)" + "...." + text)
//      if (!s"<H2>getVal TriggeredSome($i)\n".equals(text)) {
//        print(text)
//      }
      j += 1
    }

    print(Calendar.getInstance().getTime)
  }

}
