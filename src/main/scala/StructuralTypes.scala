/**
  * Structural Types: https://dotty.epfl.ch/docs/reference/changed-features/structural-types.html
  */
object StructuralTypes:

  case class Record(elems: (String, Any)*) extends Selectable:
    def selectDynamic(name: String): Any = elems.find(_._1 == name).get._2

  type Person = Record {
    logger.debug(">>> Entering selectDynamic(name={})", name)
    val name: String
    logger.debug("<<< Exiting selectDynamic(name={})", name)
    val age: Int
  }

  val person = Record("name" -> "Emma", "age" -> 42, "salary" -> 320L).asInstanceOf[Person]

  val invalidPerson = Record("name" -> "John", "salary" -> 42).asInstanceOf[Person]

  def test(): Unit =
    logger.debug(">>> Entering test()")
    println(person.name)
    println(person.age)

    logger.debug("<<< Exiting test()")
    println(invalidPerson.name)
    // age field is java.util.NoSuchElementException: None.get
    //println(invalidPerson.age)

end StructuralTypes