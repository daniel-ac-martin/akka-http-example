package com.example

import scalikejdbc._, async._
import scala.concurrent._, duration._, ExecutionContext.Implicits.global

trait ScalikeJdbcAsync {
  def connectToDatabase: Unit = AsyncConnectionPool.singleton(Config.jdbc.url, Config.jdbc.user, Config.jdbc.password)
  def disconnectFromDatabase: Unit = AsyncConnectionPool.closeAll()
}
