package Getsetapi

import scala.collection._
import scala.collection.JavaConverters._
import org.eclipse.jetty.server.{Connector, NetworkConnector, Server, ServerConnector}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import org.eclipse.jetty.servlet.ServletHandler
import java.util.concurrent.ConcurrentHashMap

import org.eclipse.jetty.server.nio.NetworkTrafficSelectChannelConnector


object Example {

  import Mymap._
  import org.eclipse.jetty.util.thread.QueuedThreadPool

  val threadPool = new QueuedThreadPool(6, 1)
  val server = new Server(threadPool)
  var connector = new ServerConnector(server)
  connector.setPort(8090)
  server.setConnectors(Array(connector))
  val getRoute = "/get"
  val putRoute = "/put"
  val updateRoute = "/update"
  val deleteRoute = "/delete"
  val handler = new ServletHandler()

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    println("Hello World!")
    println("concat arguments = " + foo(args))

    server.setHandler(handler)
    handler.addServletWithMapping(classOf[getServlet], getRoute)
    handler.addServletWithMapping(classOf[putServlet], putRoute)
    handler.addServletWithMapping(classOf[updateServlet], updateRoute)
    handler.addServletWithMapping(classOf[deleteServlet], deleteRoute)

    server.start()
    println(s"Server started on ${port()} with Routes: '$getRoute', '$putRoute', '$updateRoute', '$deleteRoute'")
    server.join()

  }


  def port() = 8090

  object Mymap {
    val map: concurrent.Map[String, String] = new ConcurrentHashMap[String, String].asScala

    class getServlet extends HttpServlet {
      override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
        var key = req.getParameter("key")
        var x = getkey(key)
        //        println("sleeping" + key)
        val async = req.startAsync
        //        Thread.sleep(10000)
        resp.setContentType("text/html")
        resp.setStatus(HttpServletResponse.SC_OK)
        resp.getWriter.println(s"<h2>Get Triggered </h2>" + x)
        async.complete()
        return
      }

      def getkey(key: String): Option[String] = {
        return map.get(key)
      }
    }

    class putServlet extends HttpServlet {
      override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
        var key = req.getParameter("key")
        var value = req.getParameter("value")
        var x = putkeyval(key, value)

        val async = req.startAsync
        println("Hi")
        print(value)
        resp.setContentType("text/html")
        resp.setStatus(HttpServletResponse.SC_OK)
        resp.getWriter.println(s"<h2>Put Triggered</h2>" + x)
        async.complete()
        return
      }

      def putkeyval(key: String, value: String): String = {
        var x = ""
        if (map.get(key) == None) {
          map.put(key, value)
          x = "SUCCESS"
        }
        else x = "Key Already Present"
        return x
      }
    }

    class updateServlet extends HttpServlet {
      override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

        var key = req.getParameter("key")
        var value = req.getParameter("value")
        var x = updatekey(key, value)
        val async = req.startAsync
        resp.setContentType("text/html")
        resp.setStatus(HttpServletResponse.SC_OK)
        resp.getWriter.println(s"<h2>Update Triggered</h2>" + x)
        async.complete()
      }

      def updatekey(key: String, value: String): Option[String] = {
        return map.put(key, value)
      }
    }

    class deleteServlet extends HttpServlet {
      override protected def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {

        var key = req.getParameter("key")
        var x = deletekey(key)
        val async = req.startAsync
        resp.setContentType("text/html")
        resp.setStatus(HttpServletResponse.SC_OK)
        resp.getWriter.println(s"<h2>Delete Triggered</h2>" + x)
        async.complete()
      }

      def deletekey(key: String): Option[String] = {
        var x = map.get(key)
        map.remove(key)
        return x
      }
    }

  }


}
