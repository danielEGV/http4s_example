package co.com.example.http.service

import cats.effect.IO
import co.com.example.http.HttpService
import org.http4s.{Header, HttpApp, HttpRoutes}
import org.http4s.dsl.io._
import org.typelevel.ci.CIStringSyntax

import java.util.UUID

class HelloV2HttpService extends HttpService {

  override def service: HttpRoutes[IO] =
    extractCardNumberHeaderV2 { cardNumber =>
      responseWithHeader(Header.Raw(ci"uuid", UUID.randomUUID().toString)) {
        HttpRoutes.of[IO] {
          case GET -> Root / "helloV2" / name =>
            cardNumber.fold(Ok(s"Hello, $name."))(cn =>
              Ok(s"Hello, $name, your card number is: $cn")
            )
        }
      }
    }

}
