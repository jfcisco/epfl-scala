package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll

import scala.annotation.tailrec

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:
  // Create generator for a heap
  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for
      x <- arbitrary[Int]
      h <- oneOf(const(empty), genHeap)
    yield insert(x, h)
  )
  given Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if isEmpty(h) then 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { (a: Int) =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("insertTwo") = forAll { (x: Int, y: Int) =>
    val h = insert(x, insert(y, empty))
    findMin(h) == Math.min(x, y)
  }

  property("insertAndDelete") = forAll { (x: Int) =>
    val h = insert(x, empty)
    isEmpty(deleteMin(h))
  }

  property("sorted") = forAll { (h: H, x: Int, y: Int) =>
    @tailrec
    def isSorted(previous: Int, h: H): Boolean =
      if isEmpty(h) then true
      else
        val currentMin = findMin(h)
        previous <= currentMin && isSorted(currentMin, deleteMin(h))

    if isEmpty(h) then
      val testHeap = insert(x, insert(y, h))
      isSorted(findMin(testHeap), deleteMin(testHeap))
    else isSorted(findMin(h), deleteMin(h))
  }

  @tailrec
  private def heapMatchesList(expectList: List[Int], heap: H): Boolean = expectList match
    case Nil => isEmpty(heap)
    case x :: xs => findMin(heap) == x && heapMatchesList(xs, deleteMin(heap))

  property("deleteMin") = forAll { (a: Int, b: Int, c: Int) =>
    val h = insert(a, insert(b, insert(c, empty)))
    val sortedTrio = List(a, b, c).sorted
    heapMatchesList(sortedTrio, h)
  }

  property("meld") = forAll { (left: Int, right : Int) =>
    val expectedOrder = if left < right then List(left, right) else List(right, left)
    val leftHeap = insert(left, empty)
    val rightHeap = insert(right, empty)
    heapMatchesList(expectedOrder, meld(leftHeap, rightHeap))
  }

