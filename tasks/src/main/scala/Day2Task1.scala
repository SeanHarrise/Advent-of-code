import scala.io.Source

@main def CubeConundrum() = {
    // Prepare to read the file
    val filePath = "/Users/seanharris/git/advent-of-code/tasks/src/main/resources/input_day_2.txt"
    val bufferedSource = Source.fromFile(filePath)

    // Set max for each colour
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    try {
        val sum = bufferedSource.getLines.foldLeft(0) { (acc, line) =>
            val gameIndex = getGameIndex(line)
            val okay = satisfiesMaxConstraint(line, " red", maxRed) && 
                satisfiesMaxConstraint(line, " green", maxGreen) && 
                satisfiesMaxConstraint(line, " blue", maxBlue)
            if (okay) {
                acc + gameIndex
            } else {
                acc
            }
        }
        println(sum)
    } finally {
        bufferedSource.close()
    }
}

def getGameIndex(line : String) : Int = {
    line.split(":")(0).split("Game ")(1).toInt
}
  
def satisfiesMaxConstraint(game : String, colour : String, maximum: Int) : Boolean = {
    var withinMax = true
    if (game.contains(colour)) {
        val occurences = game.split(colour)
        for (occurence <- occurences) {
            val splitSpaces = occurence.split(" ")
            val lastSegment = splitSpaces(splitSpaces.length - 1)
            val cubes = if (lastSegment.forall(Character.isDigit)) {
                lastSegment.toInt
            } else {
                splitSpaces(splitSpaces.length - 2).toInt
            }
            if (cubes > maximum) {
                withinMax = false
            }
        }
        withinMax
    } else {
        false
    }
}

