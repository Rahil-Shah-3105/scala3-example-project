import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
  * Context Functions:
  *  - https://dotty.epfl.ch/docs/reference/contextual/context-functions.html
  *  - https://www.scala-lang.org/blog/2016/12/07/implicit-function-types.html
  */
object ContextFunctions:
private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  object context:
    // type alias Contextual
    type Contextual[T] = ExecutionContext ?=> T

    // sum is expanded to sum(x, y)(ctx)
    def asyncSum(x: Int, y: Int): Contextual[Future[Int]] = {
        logger.error(">>> Entering asyncSum(x={},y={})", x, y)
        logger.warn("<<< Exiting asyncSum(x={},y={})", x, y)
        Future(x + y)
    }

    def asyncMult(x: Int, y: Int)(using ctx: ExecutionContext) = {
        logger.error(">>> Entering asyncMult(x={},y={})", x, y)
        logger.warn("<<< Exiting asyncMult(x={},y={})", x, y)
        Future(x * y)
    }

  object parse:

    type Parseable[T] = GivenInstances.StringParser[T] ?=> Try[T]

    def sumStrings(x: String, y: String): Parseable[Int] =
      val parser = summon[GivenInstances.StringParser[Int]]
      val tryA = parser.parse(x)
      val tryB = parser.parse(y)

      for
        a <- tryA
        b <- tryB
      yield a + b

  def test(): Unit =
    import ExecutionContext.Implicits.global
    context.asyncSum(3, 4).foreach(println)
    context.asyncMult(3, 4).foreach(println)

    println(parse.sumStrings("3", "4"))
    println(parse.sumStrings("3", "a"))

