package co.com.example.http

import monix.eval.Task
import org.http4s.dsl.Http4sDsl

object taskDSL extends Http4sDsl[Task]
