package com.example.model

import org.joda.time.DateTime
import scala.concurrent.Future
import scalikejdbc._
import scalikejdbc.SQLSyntaxSupport
import scalikejdbc.async._
import scalikejdbc.async.AsyncDB
import scalikejdbc.async.AsyncDBSession
import scalikejdbc.async.ShortenedNames

case class Pet(id: Long, dob: DateTime, name: String, sex: String, species: String) extends ShortenedNames

object Pet extends SQLSyntaxSupport[Pet] with ShortenedNames {
  override val columnNames = Seq("id", "dob", "name", "sex", "species")

  def apply(rs: WrappedResultSet) = new Pet(
    rs.long("id"), rs.jodaDateTime("dob"), rs.string("name"), rs.string("sex"), rs.string("species")
  )

  def find(id: Long)(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[Option[Pet]] =
    sql"SELECT id, dob, name, sex, species FROM pet(${id}::BIGINT)".map(Pet(_)).single.future
}
