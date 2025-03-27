package patmat

class HuffmanSuite extends munit.FunSuite:
  import Huffman.*

  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }


  test("weight of a larger tree (10pts)") {
    new TestTrees:
      assertEquals(weight(t1), 5)
  }


  test("chars of a larger tree (10pts)") {
    new TestTrees:
      assertEquals(chars(t2), List('a','b','d'))
  }

  test("string2chars hello world") {
    assertEquals(string2Chars("hello, world"), List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times: empty list") {
    assertEquals(times(Nil), Nil)
  }

  test("times: non-empty list") {
    assertEquals(times(string2Chars("aabbbcdd")), List(('a', 2), ('b', 3), ('c', 1), ('d', 2)))
  }

  test("make ordered leaf list for some frequency table (15pts)") {
    assertEquals(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))), List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton: true case") {
    new TestTrees:
      assert(singleton(List(t1)))
  }

  test("singleton: false case") {
    new TestTrees:
      assert(!singleton(List(t1, t2)))
  }

  test("combine of some leaf list (15pts)") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(combine(leaflist), List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("createCodeTree success") {
    val input = "ehe".toList
    assertEquals(createCodeTree(input),
      Fork(
        Leaf('h', 1),
        Leaf('e', 2),
        List('h', 'e'),
        3
      )
    )
  }

  test("createCodeTree failure") {
    interceptMessage[Error]("unable to create code tree") {
      createCodeTree(Nil)
    }
  }

  test("encode: huffman est cool?") {
    val mySecret = encode(frenchCode)(string2Chars("huffmanestcool"))
    assertEquals(mySecret, secret)
  }

  test("decode and encode a very short text should be identity (10pts)") {
    new TestTrees:
      assertEquals(decode(t1, encode(t1)("ab".toList)), "ab".toList)
  }

  test("decode and quick-encode a very short text should be identity (10pts)") {
    new TestTrees:
      assertEquals(decode(t1, quickEncode(t1)("ab".toList)), "ab".toList)
  }

  test("convert to CodeTable") {
    new TestTrees:
      assertEquals(convert(t2), List(
        ('a', List(0, 0)),
        ('b', List(0, 1)),
        ('d', List(1))
      ))
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
