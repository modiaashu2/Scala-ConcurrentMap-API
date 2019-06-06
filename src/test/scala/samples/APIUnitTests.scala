package samples

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._
import api.MyMap

@RunWith(classOf[JUnitRunner])
class APIUnitTests extends Specification {

  "getval when the instance is created" should {
    val mapAPI = MyMap
    "return none" in {
      val getServlet = new mapAPI.getServlet()
      getServlet.getKeyFromMap("5") === None
    }

    "return expected value" in {
      val getServlet = new mapAPI.getServlet()
      mapAPI.partitionMap(6/5000).put("6", "Hi")
      getServlet.getKeyFromMap("6") === Some("Hi")
    }
  }

  "putkeyval" should {
    val mapAPI = MyMap
    "return 'SUCCESS'" in {
      val putServlet = new mapAPI.putServlet()
      putServlet.putKeyVal("13", "Hello") === "SUCCESS"

    }
    "return 'Key Already Present'" in {
      val putServlet = new mapAPI.putServlet()
      mapAPI.partitionMap(7/5000).put("7", "Hello")
      putServlet.putKeyVal("7", "Hello") === "Key Already Present"
    }
  }

  "updatekey" should {
    val mapAPI = MyMap
    "return None" in {
      val updateServlet = new mapAPI.updateServlet()
      updateServlet.updateKey("14", "Hey") === None
    }
    "return expected value" in {
      val updateServlet = new mapAPI.updateServlet()
      mapAPI.partitionMap(8/5000).put("8", "Hey")
      updateServlet.updateKey("8", "Hola") === Some("Hey")
    }

  }

  "deletekey" should {
    val mapAPI = MyMap
    "return None" in {
      val deleteServlet = new mapAPI.deleteServlet()
      deleteServlet.deleteKey("9") === None
    }
    "return expected value" in {
      val deleteServlet = new mapAPI.deleteServlet()
      mapAPI.partitionMap(10/5000).put("10", "Bonjour")
      deleteServlet.deleteKey("10") === Some("Bonjour")
    }
  }


}

