package co.com.example.http

import cats.effect.IO
import org.http4s.HttpApp
import cats.syntax.all._
import co.com.example.http.service.{HelloHttpService, HelloV2HttpService, UserRegistrationHttpService, UserRegistrationV2HttpService}
import monix.eval.Task

class ApiService {
  def service: HttpApp[IO] = Seq(
    new HelloHttpService,
    new HelloV2HttpService,
    new UserRegistrationHttpService
  )
    .map(_.service)
    .reduce(_ <+> _)
    .orNotFound

  def taskService: HttpApp[Task] = Seq(
    new UserRegistrationV2HttpService
  )
    .map(_.service)
    .reduce(_ <+> _)
    .orNotFound
}
