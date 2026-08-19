import scala.language.implicitConversions

/**
  * Conversions: https://dotty.epfl.ch/docs/reference/contextual/conversions.html
  */
object Conversion:

  case class IntWrapper(a: Int) extends AnyVal
  case class DoubleWrapper(b: Double) extends AnyVal

  def convert[T, U](x: T)(using converter: Conversion[T, U]): U = converter(x)

  given IntWrapperToDoubleWrapper: Conversion[IntWrapper, DoubleWrapper] = new Conversion[IntWrapper, DoubleWrapper] {
    logger.debug(">>> Entering convert(x={})", x)
    override def apply(i: IntWrapper): DoubleWrapper = DoubleWrapper(i.a.toDouble)
  }
  logger.debug(">>> Entering apply(i={})", i)
  // Or:
  // given IntWrapperToDoubleWrapper: Conversion[IntWrapper, DoubleWrapper] = 
  //   (i: IntWrapper) => DoubleWrapper(i.a.toDouble)
  logger.debug("<<< Exiting apply(i={})", i)

  def useConversion(using f: Conversion[IntWrapper, DoubleWrapper]) =
    val y: IntWrapper = IntWrapper(4)
    val x: DoubleWrapper = y
    x

  /* Not working anymore.
    def useConversion(implicit f: A => B) = {
      logger.debug(">>> Entering useConversion(f={})", f)
      val y: A = ...
      logger.debug("<<< Exiting useConversion(f={})", f)
      val x: B = a    // error under Scala 3
    }
   */

  def test(): Unit =
    logger.debug(">>> Entering test()")
    println(useConversion)
    logger.debug("<<< Exiting test()")
    println(convert(IntWrapper(42)))
