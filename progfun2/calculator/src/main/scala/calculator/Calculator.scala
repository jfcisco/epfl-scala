package calculator

enum Expr:
  case Literal(v: Double)
  case Ref(name: String)
  case Plus(a: Expr, b: Expr)
  case Minus(a: Expr, b: Expr)
  case Times(a: Expr, b: Expr)
  case Divide(a: Expr, b: Expr)

object Calculator extends CalculatorInterface:
 import Expr.*

  def computeValues(
      namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    for (name, exprSignal) <- namedExpressions
    yield {
      // Remove current expression to avoid cyclic dependency issue
      val otherExpressions = namedExpressions - name
      name -> Signal(eval(exprSignal(), otherExpressions))
    }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]])(using Signal.Caller): Double = {
    def evalWithRefs(x: Expr) = eval(x, references)

    expr match
      case Literal(v) => v
      case Ref(n) => {
        val actualExpression = getReferenceExpr(n, references)
        evalWithRefs(actualExpression)
      }
      case Plus(a, b) => evalWithRefs(a) + evalWithRefs(b)
      case Minus(a, b) => evalWithRefs(a) - evalWithRefs(b)
      case Times(a, b) => evalWithRefs(a) * evalWithRefs(b)
      case Divide(a, b) => evalWithRefs(a) / evalWithRefs(b)
  }

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
      references: Map[String, Signal[Expr]])(using Signal.Caller): Expr =
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
