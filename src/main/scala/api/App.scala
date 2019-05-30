package api

object App {
  def main(args: Array[String]): Unit = {
    val instance = new ServerInitializer()
    instance.main(Array(""))
  }
}
