package co.com.example

import cats.effect._
import co.com.example.http.ApiService
import org.http4s.ember.server._

/*object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val api = new ApiService

    EmberServerBuilder
      .default[IO]
      .withHost("0.0.0.0")
      .withPort(8084)
      .withHttpApp(api.service)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}*/
