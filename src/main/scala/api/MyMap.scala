package api


import scala.collection.JavaConverters._
import java.util.concurrent.{ConcurrentHashMap, Executors}
import java.util.Calendar
import java.time.ZonedDateTime

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import scala.collection.{concurrent, mutable}
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MyMap {
  val map = new mutable.HashMap[String, String]
  val partitionMap = ArrayBuffer[concurrent.Map[String, String]]()

  for (i <- 1 to 20) {
    partitionMap += new ConcurrentHashMap[String, String]().asScala
  }
  val threadPool = Executors.newFixedThreadPool(200)
  def createResponse(request: HttpServletRequest,
                     response: HttpServletResponse, route: String, returnVal: Option[String]): Unit = {

    request.startAsync
    //  Future {
    threadPool.execute(new Runnable {
      override def run(): Unit = {
        response.setContentType("text/html")
        response.setStatus(HttpServletResponse.SC_OK)
        response.getWriter.println(s"<H2>$route Triggered" + returnVal)
        // Thread.sleep(500)
        //     println("Response Sent" + ZonedDateTime.now().toInstant().toEpochMilli())
        request.getAsyncContext.complete()
      }
    })
    //  }

    //    async.complete()
  }

  class getServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      // println("Request received" + ZonedDateTime.now().toInstant().toEpochMilli())
      val key = req.getParameter("key")
      val returnVal = getKeyFromMap(key)
      if(key.toInt%1000 == 0)
        println(key)
      createResponse(req, resp, "getVal", returnVal)
    }

    def getKeyFromMap(key: String): Option[String] = {
      //      map.get(key)
      partitionMap(key.toInt/5000).get(key)
    }
  }

  class putServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      //     println("Request received" + ZonedDateTime.now().toInstant().toEpochMilli())
      //  req.startAsync
      //threadPool.execute(new Runnable {
      //  override def run(): Unit = {
      val key = req.getParameter("key")
      val value = req.getParameter("value")
      val returnVal = putKeyVal(key, value)
      if(key.toInt%1000 == 0)
        println(key)
      createResponse(req, resp, "putVal", Option(returnVal))
      //    req.getAsyncContext.complete()
      // }
      // })
    }

    def putKeyVal(key: String, value: String): String = {
      var returnVal = ""
      if (partitionMap(key.toInt/5000).get(key).isEmpty) {
        partitionMap(key.toInt/5000).put(key, value)
        returnVal = "SUCCESS"
      }
      else returnVal = "Key Already Present"
      returnVal
    }
  }

  class updateServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

      val key = req.getParameter("key")
      val value = req.getParameter("value")
      val returnVal = updateKey(key, value)
      createResponse(req, resp, "updateVal", returnVal)
    }

    def updateKey(key: String, value: String): Option[String] = {
      partitionMap(key.toInt/5000).put(key, value)
    }
  }

  class deleteServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

      val key = req.getParameter("key")
      val returnVal = deleteKey(key)
      createResponse(req, resp, "deleteVal", returnVal)
    }

    def deleteKey(key: String): Option[String] = {
      val returnVal = partitionMap(key.toInt/5000).get(key)
      partitionMap(key.toInt/5000).remove(key)
      returnVal
    }
  }

}