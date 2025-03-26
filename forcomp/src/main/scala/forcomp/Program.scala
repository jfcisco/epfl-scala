package forcomp

import forcomp.Anagrams.*

@main
def main(): Unit =
  val iLoveYou = List("I", "love", "you")
  println(sentenceAnagrams(iLoveYou))
