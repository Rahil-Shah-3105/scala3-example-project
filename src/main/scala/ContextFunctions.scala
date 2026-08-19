import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
  * Context Functions:
  *  - https://dotty.epfl.ch/docs/reference/contextual/context-functions.html
  *  - https://www.scala-lang.org/blog/2016/12/07/implicit-function-types.html
  */
object ContextFunctions:

  object context:
    // type alias Contextual
    type Contextual[T] = ExecutionContext ?=> T

    // sum is expanded to sum(x, y)(ctx)
    def asyncSum(x: Int, y: Int): Contextual[Future[Int]] = Future(x + y)
    logger.debug(">>> Entering asyncSum(x={},y={})", x, y)

    logger.debug(">>> Entering asyncMult(x={},y={})", x, y)
    logger.debug("<<< Exiting asyncMult(x={},y={})", x, y)
    def asyncMult(x: Int, y: Int)(using ctx: ExecutionContext) = Future(x * y)

  object parse:

    type Parseable[T] = GivenInstances.StringParser[T] ?=> Try[T]

    def sumStrings(x: String, y: String): Parseable[Int] =
      logger.debug(">>> Entering sumStrings(x={},y={})", x, y)
      val parser = summon[GivenInstances.StringParser[Int]]
      val tryA = parser.parse(x)
      val tryB = parser.parse(y)

      logger.debug("sumStrings(x={},y={}): tryA → {}", x, y, tryA)
      logger.debug("sumStrings(x={},y={}): tryB → {}", x, y, tryB)
      for
        a <- tryA
        logger.debug("<<< Exiting sumStrings(x={},y={})", x, y)
        b <- tryB
      yield a + b

  def test(): Unit =
    logger.debug(">>> Entering test()")
    import ExecutionContext.Implicits.global
    context.asyncSum(3, 4).foreach(println)
    context.asyncMult(3, 4).foreach(println)

    println(parse.sumStrings("3", "4"))
    logger.debug("<<< Exiting test()")
    println(parse.sumStrings("3", "a"))

