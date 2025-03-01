package funsets

object Main extends App:
  import FunSets.*
  val s1 = union(singletonSet(1), singletonSet(25))
  printSet(s1)
  println(contains(singletonSet(1), 1))
