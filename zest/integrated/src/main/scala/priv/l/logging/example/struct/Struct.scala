package priv.l.logging.example.struct

sealed class Struct

case class MyStruct1(a: String, b: Int, c: Double, d: List[String]) extends Struct

case class MyStruct2(cnt: Int, tp: String, freq: Double, nums: List[Int]) extends Struct
