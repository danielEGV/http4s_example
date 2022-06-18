package co.com.example

import cats.effect._
import co.com.example.http.ApiService
import com.comcast.ip4s._
import org.http4s.ember.server._

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val api = new ApiService

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8084")
      .withHttpApp(api.service)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
