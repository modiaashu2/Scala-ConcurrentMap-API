package Getsetapi

import java.util.Calendar

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object FakeTest {
  def main(args : Array[String]) {
    println( "Hello World!" )
    var x = 1
    print(Calendar.getInstance().getTime())

    while(x != 100000) {
      var url = s"http://localhost:8090/get?key=$x&value=$x"
      x += 1
      var f = Future {
        val r = requests.get(url)
      }
    }
    print(Calendar.getInstance().getTime())

    //    print(http_response.body)
  }

}
