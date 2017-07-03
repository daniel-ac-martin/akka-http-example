package com.example

import com.typesafe.config.ConfigFactory

object Config {
  protected val config = ConfigFactory.load()

  case class Httpd(host: String, port: Int)
  protected val httpdConfig = config.getConfig("httpd")
  val httpd = Httpd(
    httpdConfig.getString("host"),
    httpdConfig.getInt("port")
  )
}
