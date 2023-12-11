import scala.io.Source
import scala.annotation.tailrec

@main def gearRatios() = {
    // Read the file
    val filePath = "/Users/seanharris/git/advent-of-code/tasks/src/main/resources/input_day_3.txt"
    val lines = getLines(filePath)

    // Convert from list of strings to 2D char array and add '.' padding around the four sides of the array
    val asCharArray = lines.map(_.toCharArray).toArray
    val horizontalPad = Array.fill(asCharArray.length)('.')
    val padTopAndBottom = (horizontalPad +: asCharArray) :+ horizontalPad
    val fullyPadded = padTopAndBottom.map(x => ('.' +: x) :+ x)
    fullyPadded.foreach(x=> println(x.mkString))

    // Get padded matrix dimensions
    val m = fullyPadded.length
    val n = fullyPadded(0).length
    println(s"$m x $n")

    @tailrec
    def rowRecursion(i : Int, end : Int, runningTotal : Int) : Int = {
        if (i == end) {
            runningTotal
        } else {
            val rowValue = calculateRow(i, 1, n-1, 0)
            println()
            rowRecursion(i + 1 , end, runningTotal + rowValue)
        }
    }

    @tailrec
    def calculateRow(i : Int, j : Int, end : Int, runningTotal : Int) : Int = {
        if (j == end) {
            runningTotal
        } else {
            val element = fullyPadded(i)(j)
            print(element)
            if (element == '*') {
                // Check if two numbers around it
            }
            calculateRow(i, j+1, end, runningTotal+1)
        }
    }

    @tailrec
    def checkForNumbers(i : Int, j: Int, runningTotal : Int) : (Int, Int) = {
        (0, 0)
    }

    def checkSegmentForNumber(i : Int, j: Int) : List[Int] = {
        val segment = fullyPadded(i).slice(j-1, j+2)
        segment.mkString match {
            case s if s.matches("""^\d..$""") => println("Starts with one digit")
        }
    }

    println(rowRecursion(1, m-1, 0))
}

def getLines(filePath : String) : List[String] = {
    // Returns the input file as a list of lines
    // Has to be returned as list as if you leave it as a Iterator[String] aka bufferedSource.getLines() then the
    // program will throw an error as it stilll needs access to the Stream after it has closed
    val bufferedSource = Source.fromFile(filePath)
    try {
        bufferedSource.getLines.toList
    } finally {
        bufferedSource.close
    }
}