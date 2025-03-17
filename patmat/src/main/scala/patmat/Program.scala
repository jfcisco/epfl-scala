package patmat

import Huffman.*

@main def run(): Unit =
  val sampleTree = makeCodeTree(
    makeCodeTree(Leaf('x', 1), Leaf('e', 1)),
    Leaf('t', 2)
  )
  println(sampleTree)
