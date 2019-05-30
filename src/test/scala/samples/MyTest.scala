package samples

import org.junit._
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._
import Getsetapi.Example
import java.util.concurrent.ConcurrentHashMap


@RunWith(classOf[JUnitRunner])
class GetNoneTest extends Specification {

  var m = new ConcurrentHashMap[String, String]
  "getval when the instance is created" should {
    "return none" in {
      var x = new Example.Mymap.getServlet()
      x.getkey("5") === None
    }

    "return expected value" in {
      var x = new Example.Mymap.getServlet()
      Example.Mymap.map.put("6", "Hi")
      x.getkey("6") === Some("Hi")
    }
  }

  "putkeyval" should {
    "return 'SUCCESS'" in {
      var x = new Example.Mymap.putServlet()
      x.putkeyval("7", "Hello") === "SUCCESS"

    }
    "return 'Key Already Present'" in {
      var x = new Example.Mymap.putServlet()
      Example.Mymap.map.put("7", "Hello")
      x.putkeyval("7", "Hello") === "Key Already Present"
    }
  }

  "updatekey" should {
    "return None" in {
      var x = new Example.Mymap.updateServlet()
      x.updatekey("8", "Hey") === None
    }
    "return expected value" in {
      var x = new Example.Mymap.updateServlet()
      Example.Mymap.map.put("8", "Hey")
      x.updatekey("8", "Hola") === Some("Hey")
    }

  }

  "deletekey" should {
    "return None" in {
      var x = new Example.Mymap.deleteServlet()
      x.deletekey("9") === None
    }
    "return expected value" in {
      var x = new Example.Mymap.deleteServlet()
      Example.Mymap.map.put("10", "Bonjour")
      x.deletekey("10") === Some("Bonjour")
    }
  }


}

