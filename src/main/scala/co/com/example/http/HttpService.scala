package co.com.example.http

import cats.data.{Kleisli, OptionT}
import cats.Monad
import cats.effect.IO
import cats.implicits.toFunctorOps
import org.http4s._
import org.typelevel.ci.CIStringSyntax

trait HttpService {
  def service: HttpRoutes[IO]

  def addCookie[F[_]: Monad](cookie: ResponseCookie)(rsp: F[Response[F]]): F[Response[F]] =
    rsp.map(_.addCookie(cookie))

  def responseWithHeader(header: Header.ToRaw)(service: HttpRoutes[IO]): HttpRoutes[IO] = Kleisli { req =>
    service(req).map {
      case Status.Successful(rsp) =>
        rsp.putHeaders(header)
      case rsp =>
        rsp
    }
  }

  def extractCardNumberHeaderV2(serviceF: Option[String] => HttpRoutes[IO]): HttpRoutes[IO] = Kleisli { req =>
    serviceF
      .apply(req.headers.get(ci"card-number").map(_.head.value))
      .apply(req)
  }

  def extractCardNumberHeader[F[_]: Monad](req: Request[F])(f: Option[String] => F[Response[F]]): F[Response[F]] =
    f(req.headers.get(ci"card-number").map(_.head.value))

  //Initial, need to be refactored
  def as[T](f: T => HttpRoutes[IO])(implicit entityDecoder: EntityDecoder[IO, T]): HttpRoutes[IO] = Kleisli { req =>
    OptionT(
      for {
        t <- req.as[T]
        route <- f(t).apply(req).value
      } yield route
    )
  }

  //Initial, need to be refactored
  def asV2[T](req: Request[IO])(f: T => IO[Response[IO]])(implicit entityDecoder: EntityDecoder[IO, T]): IO[Response[IO]] =
    for {
      t <- req.as[T]
      route <- f(t)
    } yield route

}
