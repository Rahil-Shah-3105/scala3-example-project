/**
  * Type Lambdas: https://dotty.epfl.ch/docs/reference/new-types/type-lambdas.html
  */
object TypeLambdas:

  type M = [X, Y] =>> Map[Y, X]

  type Tuple = [X] =>> (X, X)

  def test(): Unit =
    logger.debug(">>> Entering test()")
    val m: M[String, Int] = Map(1 -> "1")
    println(m)

    logger.debug("test(): m → {}", m)
    val tuple: Tuple[String] = ("a", "b")
    logger.debug("test(): tuple → {}", tuple)
    logger.debug("<<< Exiting test()")
    println(tuple)

