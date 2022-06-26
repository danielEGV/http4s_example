package co.com.example.http.service

import cats.effect.IO
import co.com.example.http.HttpService
import co.com.example.http.service.UserRegistrationHttpService.UserRegistrationRsp
import co.com.example.model.User
import io.circe.syntax.EncoderOps
import org.http4s.HttpRoutes
import io.circe.generic.auto._
import org.http4s.dsl.io._
import org.http4s.circe._

object UserRegistrationHttpService {
  final case class UserRegistrationRsp(id: String, email: String)
}

class UserRegistrationHttpService extends HttpService[IO] {
  override def service: HttpRoutes[IO] =
      as[User] { user =>
        HttpRoutes.of[IO] {
          case POST -> Root / "user" =>
            Ok(
              UserRegistrationRsp(
                Math.abs(user.hashCode()).toString,
                user.email
              ).asJson
            )
        }
      }
}
