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

object UserRegistrationV2HttpService {
  final case class UserRegistrationRsp(id: String, email: String)
}

class UserRegistrationV2HttpService extends HttpService[IO] {
  override def service: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
        case req @ POST -> Root / "userV2" =>
          asV2[User](req) { user =>
            Ok(
              UserRegistrationRsp(
                Math.abs(user.hashCode()).toString,
                user.email
              ).asJson
            )
          }
    }
}
