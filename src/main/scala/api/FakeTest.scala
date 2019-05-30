package api

import java.util.Calendar

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FakeTest {
  def main(args : Array[String]) {
    println( "Hello World!" )
    var x: Int = 1
    print(Calendar.getInstance().getTime)

    while(x != 100000) {
      val url = s"http://localhost:8090/get?key=$x&value=$x"
      x += 1
      Future {
        requests.get(url)
      }
    }
    print(Calendar.getInstance().getTime)

    //    print(http_response.body)
  }

}
