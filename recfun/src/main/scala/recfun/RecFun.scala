package recfun

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    // println("Pascal's Triangle")
    // for row <- 0 to 10 do
    //   for col <- 0 to row do
    //     print(s"${pascal(col, row)} ")
    //   println()

//    println("Balance")
//    val example = "(just an) example".toList
//    val isExampleBalanced = balance(example)
//    println(s"Is example balanced? $isExampleBalanced")

    println("Count Change")
    val combinations = countChange(9, List(1, 2))
    println(s"Number of valid combinations: $combinations");

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if c < 0 || c > r then 0
    else if c == 0 || c == r then 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    if chars.isEmpty then true
    else if chars.head == ')' then false
    else
      def balanceIter(netOpenParens: Int, rest: List[Char]): Int = {
        // If the list is empty, or there are already more closing than open parentheses, terminate iteration
        if rest.isEmpty || netOpenParens < 0 then
          netOpenParens
        else
          // Calculate the next value of netOpenParens, incrementing if another open parenthesis is found, decrementing if a closing parenthesis is found
          val nextNetOpenParens =
            if rest.head == '(' then netOpenParens + 1 
            else if rest.head == ')' then netOpenParens - 1
            else netOpenParens
          balanceIter(nextNetOpenParens, rest.tail)
      }

      // Tracks how many open parentheses were found previously and have not yet been paired with a closing paren
      val netOpenParens = if chars.head == '(' then 1 else 0
      balanceIter(netOpenParens, chars.tail) == 0
  }

  /**
   * Exercise 3
   */
  // Thanks to `spamegg` for the hint: https://www.coursera.org/learn/scala-functional-programming/discussions/weeks/1/threads/Xky3zcTJEe2Rxw5vmkBsRw/replies/-0u3e8TtEe2Rxw5vmkBsRw
  def countChange(money: Int, coins: List[Int]): Int =
    if coins.isEmpty || money < 0 then 0 // Invalid solutions
    else if money == 0 then 1 // Valid solution
    else // We can still make change
      // Either we use coins.head to make change, or
      // We move to the next denomination in the list
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
