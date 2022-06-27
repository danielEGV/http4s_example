package co.com.example.model

import cats.effect.Sync
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

object User {
  implicit def userDecoder[F[_]: Sync]: EntityDecoder[F, User] = jsonOf[F, User]
}

final case class User(name: String, email: String)
