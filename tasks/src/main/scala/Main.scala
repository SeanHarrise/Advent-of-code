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
      // Iterate over each line in the file
      var sum = 0
      for (line <- bufferedSource.getLines) {
        // Process each line as needed
        val firstDigit = getFirstDigit(line)
        val lastDigit = getFirstDigit(line.reverse)
        val combinedDigits = firstDigit + lastDigit
        sum += combinedDigits.toInt
      }
      println(sum)
    } finally {
      // It's important to close the file to release resources
      bufferedSource.close()
    }
  }

  @tailrec
  def getFirstDigit(input : String) : String = {
    if (input(0).isDigit) input(0).toString 
    else getFirstDigit(input.slice(1, input.length))
  }
}
