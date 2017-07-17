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

  case class Jdbc(url: String, user: String, password: String)
  protected val jdbcConfig = config.getConfig("jdbc")
  val jdbc = Jdbc(
    jdbcConfig.getString("url"),
    jdbcConfig.getString("user"),
    jdbcConfig.getString("password")
  )
}
