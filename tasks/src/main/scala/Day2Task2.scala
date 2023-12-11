import scala.io.Source
import scala.math._

@main def CubeConundrum() = {
    // Prepare to read the file
    val filePath = "/Users/seanharris/git/advent-of-code/tasks/src/main/resources/input_day_2.txt"
    val bufferedSource = Source.fromFile(filePath)

    try {
        val sumOfCubes = bufferedSource.getLines.foldLeft(0) { (acc, line) =>
            val idRemoved = line.split(":")(1)
            val cube = calculateCube(idRemoved)
            acc + cube
        }
        println(sumOfCubes)
    } finally {
        bufferedSource.close()
    }
}

def calculateCube(game : String) : Int = {
    println("#" * 120)
    println("Game: " + game)
    val splitHandfuls = game.split("; ")
    val allCubes = splitHandfuls.foldLeft((0, 0, 0)) {(acc1, handful) =>
        println("Whole Handful: " + handful)
        val segments = handful.split(" ")
        val minCubes = segments.foldLeft((0, 0, 0, 0)) {(acc2, segment) =>
            println(acc2)
            println("Segment: " + segment)
            val add = matchColours(segment)
            (acc2._1 + add._1 * acc2._4, 
            acc2._2 + add._2 * acc2._4, 
            acc2._3 +add._3 * acc2._4,  add._4)
        }
        println(minCubes)
        (max(minCubes._1, acc1._1), 
        max(minCubes._2, acc1._2), 
        max(minCubes._3, acc1._3))
    }
    allCubes._1 * allCubes._2 * allCubes._3
}

def matchColours(segment : String) : (Int, Int, Int, Int) = {
    segment match {
        case redRegex() => (1, 0, 0, 0)
        case greenRegex() => (0, 1, 0, 0)
        case blueRegex() => (0, 0, 1, 0)
        case "" => (0, 0, 0, 0)
        case _ => (0, 0, 0, segment.toInt)
    }
}

val redRegex = """red.*""".r
val greenRegex = """green.*""".r
val blueRegex = """blue.*""".r
