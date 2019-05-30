package samples

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._
import java.util.concurrent.ConcurrentHashMap

import api.MyMap._
import api._


@RunWith(classOf[JUnitRunner])
class APIUnitTests extends Specification {

  var m = new ConcurrentHashMap[String, String]
  "getval when the instance is created" should {
    "return none" in {
      var x = new getServlet()
      x.getKey("5") === None
    }

    "return expected value" in {
      var x = new getServlet()
      MyMap.map.put("6", "Hi")
      x.getKey("6") === Some("Hi")
    }
  }

  "putkeyval" should {
    "return 'SUCCESS'" in {
      var x = new putServlet()
      x.putKeyVal("13", "Hello") === "SUCCESS"

    }
    "return 'Key Already Present'" in {
      var x = new putServlet()
      MyMap.map.put("7", "Hello")
      x.putKeyVal("7", "Hello") === "Key Already Present"
    }
  }

  "updatekey" should {
    "return None" in {
      var x = new updateServlet()
      x.updateKey("14", "Hey") === None
    }
    "return expected value" in {
      var x = new updateServlet()
      MyMap.map.put("8", "Hey")
      x.updateKey("8", "Hola") === Some("Hey")
    }

  }

  "deletekey" should {
    "return None" in {
      var x = new deleteServlet()
      x.deleteKey("9") === None
    }
    "return expected value" in {
      var x = new deleteServlet()
      MyMap.map.put("10", "Bonjour")
      x.deleteKey("10") === Some("Bonjour")
    }
  }


}

