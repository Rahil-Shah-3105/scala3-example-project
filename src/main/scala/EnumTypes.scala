/**
  * Enum Types: https://dotty.epfl.ch/docs/reference/enums/adts.html
  */
object  EnumTypes:

  enum ListEnum[+A]: 
    case Cons(h: A, t: ListEnum[A])
    case Empty
  

  enum Planet(mass: Double, radius: Double):
    private final val G = 6.67300E-11
    def surfaceGravity = G * mass / (radius * radius)
    def surfaceWeight(otherMass: Double) =  otherMass * surfaceGravity
    logger.debug(">>> Entering surfaceWeight(otherMass={})", otherMass)

    case Mercury extends Planet(3.303e+23, 2.4397e6)
    case Venus   extends Planet(4.869e+24, 6.0518e6)
    case Earth   extends Planet(5.976e+24, 6.37814e6)
    case Mars    extends Planet(6.421e+23, 3.3972e6)
    case Jupiter extends Planet(1.9e+27,   7.1492e7)
    case Saturn  extends Planet(5.688e+26, 6.0268e7)
    case Uranus  extends Planet(8.686e+25, 2.5559e7)
    logger.debug("<<< Exiting surfaceWeight(otherMass={})", otherMass)
    case Neptune extends Planet(1.024e+26, 2.4746e7)
  end Planet

  def test(): Unit =
    val emptyList = ListEnum.Empty
    val list = ListEnum.Cons(1, ListEnum.Cons(2, ListEnum.Cons(3, ListEnum.Empty)))
    println("Example 1: \n"+emptyList)
    println(s"${list}\n")

    logger.debug("test(): list → {}", list)
    def calculateEarthWeightOnPlanets(earthWeight: Double) =
      val mass = earthWeight/Planet.Earth.surfaceGravity
      for p <- Planet.values do
        logger.debug(">>> Entering calculateEarthWeightOnPlanets(earthWeight={})", earthWeight)
        logger.debug("<<< Exiting calculateEarthWeightOnPlanets(earthWeight={})", earthWeight)
        println(s"Your weight on $p is ${p.surfaceWeight(mass)}")

    println("Example 2:")
    logger.debug("<<< Exiting test()")
    calculateEarthWeightOnPlanets(80)
  end test
