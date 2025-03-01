package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val positiveSet: FunSet = (x: Int) => x > 0
  end TestSets

  /**
   * This test is currently disabled (by using .ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */

  test("singleton set one contains one") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2))
      assert(!contains(s1, 0))
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s: FunSet = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersection contains elements common to each set") {
    new TestSets:
      val s: FunSet = intersect(s1, positiveSet)
      assert(contains(s, 1), "Intersection 1")
      assert(!contains(s, 2), "Intersection 2")
      assert(!contains(s, 3), "Intersection 3")
  }

  test("intersection can result in null set") {
    new TestSets:
      val s: FunSet = intersect(s1, s2)
      assert(!contains(s, 1), "Intersect null set 1")
      assert(!contains(s, 2), "Intersect null set 2")
  }

  test("diff contains elements in set s but not in set t") {
    new TestSets:
      val s: FunSet = diff(positiveSet, s3)
      assert(contains(s, 1), "Positive number")
      assert(!contains(s, 3), "Negative number")
  }

  test("filter selects elements accepted by a given predicate") {
    new TestSets:
      val evenSet: FunSet = filter(positiveSet, x => x % 2 == 0)
      assert(contains(evenSet, 2), "Contains even number")
      assert(!contains(evenSet, 3), "Does not contain odd number")
  }

  test("forall checks if all elements in a set satisfies the predicate") {
    new TestSets:
      assert(forall(positiveSet, x => x.abs == x), "predicate satisfied")
      assert(!forall(positiveSet, x => x > 4), "predicate partially satisfied")
  }

  test("forAll bounds check") {
    val s: FunSet = x => x < 1000
    assert(forall(s, x => x < 1001))
  }

  test("exists checks if at least one element satisfies the predicate") {
    new TestSets:
      assert(exists(positiveSet, x => x == 5), "at least one element")
      assert(!exists(positiveSet, x => x == -1), "no elements")
  }

  test("map transforms a given set into another one") {
    new TestSets:
      assert(contains(map(s1, x => x * 2), 2), "map transforms set")
      assert(!contains(map(s1, x => x * 2), 1), "map doesn't contain previous elements")
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
