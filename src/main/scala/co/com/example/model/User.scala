package co.com.example.model

import io.circe.generic.auto._

import cats.effect.IO
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

object User {
  implicit val userDecoder: EntityDecoder[IO, User] = jsonOf[IO, User]
}

final case class User(name: String, email: String)
