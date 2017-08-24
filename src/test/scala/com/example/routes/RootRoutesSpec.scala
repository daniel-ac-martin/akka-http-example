package com.example.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ Matchers, WordSpec }

class RootRoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {
  "RootRoutes" should {
    "answer to any request to `/`" in {
      Get("/") ~> RootRoutes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Server up and running"
      }
      Post("/") ~> RootRoutes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "Server up and running"
      }
    }
  }
}
