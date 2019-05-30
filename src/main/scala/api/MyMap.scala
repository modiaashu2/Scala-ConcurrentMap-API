package api


import scala.collection.JavaConverters._

import java.util.concurrent.ConcurrentHashMap

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import scala.collection.concurrent

class MyMap {
  val map: concurrent.Map[String, String] = new ConcurrentHashMap[String, String].asScala


  def createResponse(request: HttpServletRequest,
                     response: HttpServletResponse, route: String, returnVal: Option[String]): Unit = {
    val async = request.startAsync
    response.setContentType("text/html")
    response.setStatus(HttpServletResponse.SC_OK)
    response.getWriter.println(s"<H2>$route Triggered" + returnVal)
    async.complete()
  }

  class getServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      val key = req.getParameter("key")
      val returnVal = getKey(key)
      val async = req.startAsync
      resp.setContentType("text/html")
      createResponse(req, resp, "getVal", returnVal)
      async.complete()
    }

    def getKey(key: String): Option[String] = {
      map.get(key)
    }
  }

  class putServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      val key = req.getParameter("key")
      val value = req.getParameter("value")
      val returnVal = putKeyVal(key, value)
      createResponse(req, resp, "putVal", Option(returnVal))
    }

    def putKeyVal(key: String, value: String): String = {
      var returnVal = ""
      if (map.get(key).isEmpty) {
        map.put(key, value)
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
      map.put(key, value)
    }
  }

  class deleteServlet extends HttpServlet {
    override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

      val key = req.getParameter("key")
      val returnVal = deleteKey(key)
      createResponse(req, resp, "deleteVal", returnVal)
    }

    def deleteKey(key: String): Option[String] = {
      val returnVal = map.get(key)
      map.remove(key)
      returnVal
    }
  }

}