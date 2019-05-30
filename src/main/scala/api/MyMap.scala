package api


import scala.collection.JavaConverters._

import java.util.concurrent.ConcurrentHashMap

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import scala.collection.concurrent

object MyMap {
  val map: concurrent.Map[String, String] = new ConcurrentHashMap[String, String].asScala

  class getServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      var key = req.getParameter("key")
      var x = getKey(key)
      //        println("sleeping" + key)
      val async = req.startAsync
      //        Thread.sleep(10000)
      resp.setContentType("text/html")
      resp.setStatus(HttpServletResponse.SC_OK)
      resp.getWriter.println(s"<h2>Get Triggered </h2>" + x)
      async.complete()
      return
    }

    def getKey(key: String): Option[String] = {
      return map.get(key)
    }
  }

  class putServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      var key = req.getParameter("key")
      var value = req.getParameter("value")
      var x = putKeyVal(key, value)

      val async = req.startAsync
      println("Hi")
      print(value)
      resp.setContentType("text/html")
      resp.setStatus(HttpServletResponse.SC_OK)
      resp.getWriter.println(s"<h2>Put Triggered</h2>" + x)
      async.complete()
      return
    }

    def putKeyVal(key: String, value: String): String = {
      var returnVal = ""
      if (map.get(key) == None) {
        map.put(key, value)
        returnVal = "SUCCESS"
      }
      else returnVal = "Key Already Present"
      return returnVal
    }
  }

  class updateServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

      var key = req.getParameter("key")
      var value = req.getParameter("value")
      var returnVal = updateKey(key, value)
      val async = req.startAsync
      resp.setContentType("text/html")
      resp.setStatus(HttpServletResponse.SC_OK)
      resp.getWriter.println(s"<h2>Update Triggered</h2>" + returnVal)
      async.complete()
    }

    def updateKey(key: String, value: String): Option[String] = {
      return map.put(key, value)
    }
  }

  class deleteServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

      var key = req.getParameter("key")
      var returnVal = deleteKey(key)
      val async = req.startAsync
      resp.setContentType("text/html")
      resp.setStatus(HttpServletResponse.SC_OK)
      resp.getWriter.println(s"<h2>Delete Triggered</h2>" + returnVal)
      async.complete()
    }

    def deleteKey(key: String): Option[String] = {
      var returnVal = map.get(key)
      map.remove(key)
      return returnVal
    }
  }

}