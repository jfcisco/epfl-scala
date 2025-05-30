package calculator

object Polynomial extends PolynomialInterface:
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      Math.pow(b(), 2) - (4 * a() * c())
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    def roots(using Signal.Caller) = {
      // Positive
      val pos = (-b() + Math.sqrt(delta())) / (2 * a())
      // Negative
      val neg = (-b() - Math.sqrt(delta())) / (2 * a())
      Set(pos, neg)
    }

    Signal {
      if delta() < 0 then Set() else roots
    }
  }

