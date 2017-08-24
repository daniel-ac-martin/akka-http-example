package com.example.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ Matchers, WordSpec }

class PetRoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {
  "PetRoutes" should {
    /*
    "answer to GET requests to `/pet`" in {
      Get("/pet") ~> PetRoutes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "[]"
      }
    }
     */
  }
}
