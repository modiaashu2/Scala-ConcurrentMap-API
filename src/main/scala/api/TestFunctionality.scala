package api

import java.util.Calendar
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

import requests.Response
import org.eclipse.jetty.util.thread.{ExecutorSizedThreadPool, QueuedThreadPool}
import org.junit.Test

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}

object TestFunctionality {
  def main(args: Array[String]) {
    println("Hello World!")
    var counter = 1
    print(Calendar.getInstance().getTime)

    var url1 = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=1&value=2"
    var response1 = requests.get(url1)
    println(response1.text())

    var url2 = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/get?key=1"
    var response2 = requests.get(url2)
    println(response2.text())

    var url3 = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/update?key=1&value=4"
    var response3 = requests.get(url3)
    println(response3.text())

    var url4 = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/get?key=1"
    var response4 = requests.get(url4)
    println(response4.text())

    var url5 = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/delete?key=1&value=2"
    var response5 = requests.get(url5)
    println(response5.text())

    var url6 = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/get?key=1&value=2"
    var response6 = requests.get(url6)
    println(response6.text())

//    var url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=1&value=2"
//    var response = requests.get(url)
//    println(response.text())
//
//    var url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=1&value=2"
//    var response = requests.get(url)
//    println(response.text())
//
//    var url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=1&value=2"
//    var response = requests.get(url)
//    println(response.text())
//
//    var url = s"http://ec2-13-126-222-27.ap-south-1.compute.amazonaws.com:8090/put?key=1&value=2"
//    var response = requests.get(url)
//    println(response.text())
    
    print(Calendar.getInstance().getTime)
  }

}
