package co.com.example

import co.com.example.http.ApiService
import monix.eval.Task
import monix.execution.Scheduler
import org.http4s.ember.server.EmberServerBuilder

object MainTask {

  def main(args: Array[String]): Unit = {
    implicit val computationScheduler: Scheduler  = Scheduler.global
    val api = new ApiService

    EmberServerBuilder
      .default[Task]
      .withHost("0.0.0.0")
      .withPort(8084)
      .withHttpApp(api.taskService)
      .build
      .use(_ => Task.never)
      .runSyncUnsafe()
  }

}
