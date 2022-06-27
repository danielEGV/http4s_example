package co.com.example.http.service

import co.com.example.http.HttpService
import co.com.example.http.service.UserRegistrationV2HttpService.UserRegistrationRsp
import co.com.example.model.User
import io.circe.syntax.EncoderOps
import org.http4s.HttpRoutes
import io.circe.generic.auto._
import monix.eval.Task
import org.http4s.circe._

object UserRegistrationV2HttpService {
  final case class UserRegistrationRsp(id: String, email: String, msg: String)
}

class UserRegistrationV2HttpService extends HttpService[Task] {
  import co.com.example.http.taskDSL._

  override def service: HttpRoutes[Task] =
    HttpRoutes.of[Task] {
        case req @ POST -> Root / "userV2" =>
          asV2[User](req) { user =>
            val msg = Task
              .eval(s"Hello ${user.name}")
              .map(UserRegistrationRsp(
                Math.abs(user.hashCode()).toString,
                user.email,
                _
              ))
            Ok(msg.map(_.asJson))
          }
    }
}
