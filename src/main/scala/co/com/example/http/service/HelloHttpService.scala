package co.com.example.http.service

import cats.effect.IO
import co.com.example.http.HttpService
import org.http4s.{ HttpRoutes, ResponseCookie}
import org.http4s.dsl.io._

class HelloHttpService extends HttpService[IO] {
  override def service: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case request @ GET -> Root / "hello" / name =>
        extractCardNumberHeader(request) { cardNumber =>
          addCookie(ResponseCookie("foo", "bar")) {
            cardNumber.fold(Ok(s"Hello, $name."))(cn =>
              Ok(s"Hello, $name, your card number is: $cn")
            )
          }
        }
    }
}
