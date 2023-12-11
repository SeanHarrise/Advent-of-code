import scala.io.Source
import java.nio.file.{Paths, Path}
import scala.annotation.tailrec

object FileReader {
  def main(args: Array[String]): Unit = {
    // Specify the path to your text file
    val filePath = "/Users/seanharris/git/advent-of-code/tasks/src/main/resources/input.txt"

    // Use Source.fromFile to open the file and get a BufferedSource
    val bufferedSource = Source.fromFile(filePath)

    try {
      val sum = bufferedSource.getLines.foldLeft(0) { (acc, line) =>
        val firstDigit = getFirstDigit(line)
        val lastDigit = getLastDigit(line, line.length - 1)
        val combinedDigits = firstDigit + lastDigit
        println(line)
        println(combinedDigits)
        acc + combinedDigits.toInt
      }
      println(sum)
    } finally {
      // It's important to close the file to release resources
      bufferedSource.close()
    }
  }

  def matchStringRepresentation(input : String): (Boolean , String) = {
    input match {
        case _ if input.slice(0, 3) == "one" => (true, "1")
        case _ if input.slice(0, 3) == "two" => (true, "2")
        case _ if input.slice(0, 5) == "three" => (true, "3")
        case _ if input.slice(0, 4) == "four" => (true, "4")
        case _ if input.slice(0, 4) == "five" => (true, "5")
        case _ if input.slice(0, 3) == "six" => (true, "6")
        case _ if input.slice(0, 5) == "seven" => (true, "7")
        case _ if input.slice(0, 5) == "eight" => (true, "8")
        case _ if input.slice(0, 4) == "nine" => (true, "9")
        case _ => (false, "")
    }
  }

  @tailrec
  def getFirstDigit(input : String) : String = {
    val (stringMatch, matchedNumber) = matchStringRepresentation(input)
    if (input(0).isDigit) input(0).toString
    else if (stringMatch) matchedNumber
    else getFirstDigit(input.slice(1, input.length))
  }

  @tailrec
  def getLastDigit(input : String, startIndex : Int) : String = {
    val (stringMatch, matchedNumber) = matchStringRepresentation(input.slice(startIndex, input.length))
    if (input(startIndex).isDigit) input(startIndex).toString
    else if (stringMatch) matchedNumber
    else getLastDigit(input, startIndex - 1)
  }

}
