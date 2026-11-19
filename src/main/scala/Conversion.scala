import scala.language.implicitConversions

/**
  * Conversions: https://dotty.epfl.ch/docs/reference/contextual/conversions.html
  */
object Conversion:
private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  case class IntWrapper(a: Int) extends AnyVal
  case class DoubleWrapper(b: Double) extends AnyVal

  def convert[T, U](x: T)(using converter: Conversion[T, U]): U = {
      logger.error(">>> Entering convert(x={})", x)
      logger.warn("<<< Exiting convert(x={})", x)
      converter(x)
  }

  given IntWrapperToDoubleWrapper: Conversion[IntWrapper, DoubleWrapper] = new Conversion[IntWrapper, DoubleWrapper] {
    override def apply(i: IntWrapper): DoubleWrapper = {
        logger.error(">>> Entering apply(i={})", i)
        logger.warn("<<< Exiting apply(i={})", i)
        DoubleWrapper(i.a.toDouble)
    }
  }
  // Or:
  // given IntWrapperToDoubleWrapper: Conversion[IntWrapper, DoubleWrapper] = 
  //   (i: IntWrapper) => DoubleWrapper(i.a.toDouble)

  def useConversion(using f: Conversion[IntWrapper, DoubleWrapper]) =
    val y: IntWrapper = IntWrapper(4)
    val x: DoubleWrapper = y
    x

  /* Not working anymore.
    def useConversion(implicit f: A => B) = {
      val y: A = ...
      val x: B = a    // error under Scala 3
    }
   */

  def test(): Unit =
    println(useConversion)
    println(convert(IntWrapper(42)))
