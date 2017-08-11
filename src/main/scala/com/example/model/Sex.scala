package com.example.model

sealed trait Sex
case class Female() extends Sex
case class Male() extends Sex
object Female extends Female {
  override def toString(): String = "female"
}
object Male extends Male {
  override def toString(): String = "male"
}
object Sex {
  def apply(s: String): Sex = s match {
    case "female" => new Female
    case "male" => new Male
  }
}
