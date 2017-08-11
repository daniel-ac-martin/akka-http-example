package com.example

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ Matchers, WordSpec }

class RoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "Routes" should {
    "answer to any request to `/`" in {
      Get("/") ~> Routes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "{\"msg\":\"Server up and running\"}"
      }
      Post("/") ~> Routes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "{\"msg\":\"Server up and running\"}"
      }
    }
    "answer to GET requests to `/hello`" in {
      Get("/hello") ~> Routes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "{\"msg\":\"Say hello to akka-http\"}"
      }
    }
    "not handle a POST request to `/hello`" in {
      Post("/hello") ~> Routes.routes ~> check {
        handled shouldBe false
      }
    }
    "respond with 405 when not issuing a GET to `/hello` and route is sealed" in {
      Put("/hello") ~> Route.seal(Routes.routes) ~> check {
        status shouldBe StatusCodes.MethodNotAllowed
      }
    }
  }

}
