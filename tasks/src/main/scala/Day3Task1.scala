import scala.io.Source
import scala.annotation.tailrec

@main def gearRatios() = {
    // Prepare to read the file
    val filePath = "/Users/seanharris/git/advent-of-code/tasks/src/main/resources/input_day_3.txt"

    val lines = getLines(filePath)
    val charArrays = lines.map(_.toCharArray).toArray
    val pad = Array.fill(charArrays(0).length)('.')
    val padded = (pad +: charArrays) :+ pad
    val noOfColumns = padded.length
    val fullyPadded = padded.map(x => ('.' +: x) :+ '.')
    val noOfRows = fullyPadded(0).length

    @tailrec
    def calculateRow(current : Int, end : Int, runningTotal : Int, row : Int) : Int = {
        if (current == end) {
            runningTotal
        } else {
            if (fullyPadded(row)(current).isDigit) {
                val numLength = getNumberLength(row, current + 1, 1)
                val partToAdd = addPartIfRequired(numLength, current, row)
                calculateRow(current + numLength, end, runningTotal + partToAdd, row)
            } else {
                calculateRow(current + 1, end, runningTotal, row)
            }
        }
    }

    @tailrec
    def rowRecursion(current : Int, end : Int, runningTotal : Int) : Int = {
        if (current == end) {
            runningTotal
        } else {
            val rowValue = calculateRow(1, noOfRows-1, 0, current)
            // println()
            rowRecursion(current + 1 , end, runningTotal + rowValue)
        }
    }

    def addPartIfRequired(numLength : Int, row : Int, column : Int) : Int = {
        println(s"Adding part if required: row $row column $column numLength $numLength")
        var partToBeAdded = false
        for (j <- column - 1 to column + 1) {
            for (i <- row - 1 to row + numLength) {
                val element = fullyPadded(j)(i)
                print(element)
                if (!(element.isDigit || (element == '.'))) {
                    partToBeAdded = true
                }
            }
            println()
        }
        if (partToBeAdded) {
            fullyPadded(column).slice(row, row + numLength).mkString.toInt
        } else {
            0
        }
    }

    @tailrec
    def getNumberLength(row : Int, column : Int, lengthSoFar : Int) : Int = {
        if (!fullyPadded(row)(column).isDigit) {
            lengthSoFar
        } else {
            getNumberLength(row, column + 1, lengthSoFar + 1)
        }
    }

    // Tests
    fullyPadded.foreach(row => println(row.mkString("")))
    rowRecursion(1, noOfColumns - 1, 0)
    println(rowRecursion(1, noOfColumns - 1, 0))
}

def getLines(filePath : String) : List[String] = {
    val bufferedSource = Source.fromFile(filePath)
    try {
        bufferedSource.getLines().toList
    } finally {
        bufferedSource.close()
    }
}