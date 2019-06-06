package api

import java.time.ZonedDateTime

import org.junit.Test

object App {
//  @Test
  def main(args: Array[String]): Unit = {
    val instance = new ServerInitializer()
    instance.main(Array(""))
    ZonedDateTime.now().toInstant().toEpochMilli()
  }
}
