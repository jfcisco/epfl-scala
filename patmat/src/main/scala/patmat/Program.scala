package patmat

import Huffman.*

@main def run(): Unit =
  val beeMovie = "NARRATOR: (Black screen with text; The sound of buzzing bees can be heard) According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible. BARRY BENSON: (Barry is picking out a shirt) Yellow, black. Yellow, black. Yellow, black. Yellow, black. Ooh, black and yellow! Let's shake it up a little."

  val beeCode = createCodeTree(string2Chars(beeMovie))
  val beeEncode = quickEncode(beeCode)
  val encodedScript = beeEncode(string2Chars(beeMovie))
  println(encodedScript.mkString)

  val decodedScript = decode(beeCode, encodedScript).mkString
  println(decodedScript)
  println(s"Is decode successful? ${beeMovie == decodedScript}")
