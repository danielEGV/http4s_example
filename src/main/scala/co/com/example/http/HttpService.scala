package co.com.example.http

import cats.data.{Kleisli, OptionT}
import cats.{Functor, MonadThrow}
import cats.implicits._
import org.http4s._
import org.typelevel.ci.CIStringSyntax

trait HttpService[F[_]] {
  def service: HttpRoutes[F]

  def addCookie(cookie: ResponseCookie)(rsp: F[Response[F]])(implicit functor: Functor[F]): F[Response[F]] =
    rsp.map(_.addCookie(cookie))

  def responseWithHeader(header: Header.ToRaw)(service: HttpRoutes[F])(implicit functor: Functor[F]): HttpRoutes[F] = Kleisli { req =>
    service(req).map {
      case Status.Successful(rsp) =>
        rsp.putHeaders(header)
      case rsp =>
        rsp
    }
  }

  def extractCardNumberHeaderV2(serviceF: Option[String] => HttpRoutes[F]): HttpRoutes[F] = Kleisli { req =>
    serviceF
      .apply(req.headers.get(ci"card-number").map(_.head.value))
      .apply(req)
  }

  def extractCardNumberHeader(req: Request[F])(f: Option[String] => F[Response[F]]): F[Response[F]] =
    f(req.headers.get(ci"card-number").map(_.head.value))

  //Initial, need to be refactored
  def as[T](f: T => HttpRoutes[F])(
    implicit monadThrow: MonadThrow[F],
    entityDecoder: EntityDecoder[F, T]
  ): HttpRoutes[F] = Kleisli { req =>
    OptionT(
      for {
        t <- req.as[T]
        route <- f(t).apply(req).value
      } yield route
    )
  }

  //Initial, need to be refactored
  def asV2[T](req: Request[F])(f: T => F[Response[F]])(
    implicit monadThrow: MonadThrow[F],
    entityDecoder: EntityDecoder[F, T]
  ): F[Response[F]] =
    for {
      t <- req.as[T]
      route <- f(t)
    } yield route

}
