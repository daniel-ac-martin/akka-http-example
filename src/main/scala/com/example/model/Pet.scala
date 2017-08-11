package com.example.model

import java.time.LocalDate
import scala.concurrent.Future
import scalikejdbc._
import scalikejdbc.SQLSyntaxSupport
import scalikejdbc.async._
import scalikejdbc.async.AsyncDB
import scalikejdbc.async.AsyncDBSession
import scalikejdbc.async.ShortenedNames

case class Pet(id: Long, dob: LocalDate, name: String, sex: Sex, species: Species) extends ShortenedNames

object Pet extends SQLSyntaxSupport[Pet] with ShortenedNames {
  //override val columnNames = Seq("id", "dob", "name", "sex", "species")

  def apply(rs: WrappedResultSet) = new Pet(
    rs.long("id"), rs.localDate("dob"), rs.string("name"), Sex(rs.string("sex")), Species(rs.string("species"))
  )

  def create(dob: LocalDate, name: String, sex: Sex, species: Species)(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[Option[Long]] =
    sql"SELECT id FROM new_pet(${dob}, ${name}, ${sex}, ${species})".map(_.long("id")).single.future

  def read(id: Long)(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[Option[Pet]] =
    sql"SELECT id, dob, name, sex, species FROM pet(${id}::BIGINT)".map(Pet(_)).single.future

  def search(dobStart: Option[LocalDate], dobFinish: Option[LocalDate], name: Option[String], sex: Option[Sex], species: Option[Species])(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[List[Pet]] =
    sql"SELECT id, dob, name, sex, species FROM pets(${dobStart}, ${dobFinish}, ${name}, ${sex}, ${species})".map(Pet(_)).list.future
}
