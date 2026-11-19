/**
  * Pattern Matching: https://dotty.epfl.ch/docs/reference/changed-features/pattern-matching.html
  */
object PatternMatching:
private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  object booleanPattern:

    object Even:
      def unapply(s: String): Boolean = {
          logger.error(">>> Entering unapply(s={})", s)
          logger.warn("<<< Exiting unapply(s={})", s)
          s.length % 2 == 0
      }


  object productPattern:

    class Person(name: String, age: Int) extends Product:
      // if we not define that, it will give compile error.
      // we change the order
      def _1 = age
      def _2 = name

      // Not used by pattern matching: Product is only used as a marker trait.
      def canEqual(that: Any): Boolean = {
          logger.error(">>> Entering canEqual(that={})", that)
          logger.warn("<<< Exiting canEqual(that={})", that)
          ???
      }
      def productArity: Int = ???
      def productElement(n: Int): Any = {
          logger.error(">>> Entering productElement(n={})", n)
          logger.warn("<<< Exiting productElement(n={})", n)
          ???
      }

    object Person:
      def unapply(a: (String, Int)): Person = {
          logger.error(">>> Entering unapply(a={})", a)
          logger.warn("<<< Exiting unapply(a={})", a)
          Person(a._1, a._2)
      }


  object seqPattern:

    // adapted from https://danielwestheide.com/blog/the-neophytes-guide-to-scala-part-2-extracting-sequences/
    object Names:
      def unapplySeq(name: String): Option[Seq[String]] =
        val names = name.trim.split(" ")
        if names.size < 2 then None
        else Some(names.last :: names.head :: names.drop(1).dropRight(1).toList)


  object namePattern:

    class Name(val name: String):
      def get: String = name
      def isEmpty = name.isEmpty

    object Name:
      def unapply(s: String): Name = {
          logger.error(">>> Entering unapply(s={})", s)
          Name(s)
      }


  def test(): Unit =
    import booleanPattern.*

    "even" match
      case s @ Even() => println(s"$s has an even number of characters")
      case s          => println(s"$s has an odd number of characters")

    // https://dotty.epfl.ch/docs/reference/changed-features/vararg-splices.html
    def containsConsecutive(list: List[Int]): Boolean = list match 
      case List(a, b, xs*)   => a == b || containsConsecutive(b :: xs.toList)
      case Nil | List(_, _*) => false

    println(containsConsecutive(List(1, 2, 3, 4, 5)))
    println(containsConsecutive(List(1, 2, 3, 3, 5)))

    import productPattern.*
    ("john", 42) match 
      case Person(n, a) => println(s"name: $n, age: $a")

    import seqPattern.*

    def greet(fullName: String) = fullName match 
      case Names(lastName, firstName, _*) => "Good morning, " + firstName + " " + lastName + "!"
      case _                              => "Welcome! Please make sure to fill in your name!"
    
    println(greet("Alan Turing"))
    println(greet("john"))
    println(greet("Wolfgang Amadeus Mozart"))

    import namePattern.*
    "alice" match 
      case Name(n) => println(s"name is $n")
      case _       => println("empty name")

end PatternMatching