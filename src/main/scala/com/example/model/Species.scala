package com.example.model

sealed trait Species
case class Cat() extends Species
case class Dog() extends Species
object Cat extends Cat {
  override def toString(): String = "cat"
}
object Dog extends Dog {
  override def toString(): String = "dog"
}
object Species {
  def apply(s: String): Species = s match {
    case "cat" => new Cat
    case "dog" => new Dog
  }
}
