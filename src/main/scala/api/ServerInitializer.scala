package api

import org.eclipse.jetty.server.{Server, ServerConnector}
import org.eclipse.jetty.servlet.ServletHandler
import org.eclipse.jetty.util.thread.QueuedThreadPool

class ServerInitializer {
  val mapAPI = new MyMap()
  val threadPool = new QueuedThreadPool(6, 1)
  val server = new Server(threadPool)
  var connector = new ServerConnector(server)
  connector.setPort(port)
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
    handler.addServletWithMapping(classOf[mapAPI.getServlet], getRoute)
    handler.addServletWithMapping(classOf[mapAPI.putServlet], putRoute)
    handler.addServletWithMapping(classOf[mapAPI.updateServlet], updateRoute)
    handler.addServletWithMapping(classOf[mapAPI.deleteServlet], deleteRoute)

    server.start()
    println(s"Server started on $port with Routes: '$getRoute', '$putRoute', '$updateRoute', '$deleteRoute'")
    server.join()

  }


  var port = 8090


}
