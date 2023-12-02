//> using scala 3
//> using toolkit latest

val pwd = os.Path("/Users/madetech/projects/aoc/2023/scala")

val example1 =
  """
  |1abc2
  |pqr3stu8vwx
  |a1b2c3d4e5f
  |treb7uchet
  |""".stripMargin.trim.split("\n").toList

def part1(input: List[String]): List[Int] =
  for
    line       <- input
    firstDigit <- line.find(_.isDigit)
    lastDigit  <- line.findLast(_.isDigit)
  yield s"${firstDigit}${lastDigit}".toInt

part1(example1).sum

val input1 = os.read(pwd / "day01.input1.txt").split("\n").toList

println("Part 1: " + part1(input1).sum)

val example2 =
  """
  |two1nine
  |eightwothree
  |abcone2threexyz
  |xtwone3four
  |4nineeightseven2
  |zoneight234
  |7pqrstsixteen
  |""".stripMargin.trim.split("\n").toList

val digitsToReplace = Range.inclusive(1, 9)

val wordForDigit = Seq(
  "zero",
  "one",
  "two",
  "three",
  "four",
  "five",
  "six",
  "seven",
  "eight",
  "nine"
)

def part2(input: List[String]): List[Int] =
  for
    line <- input
    digitsInLine =
      line.zipWithIndex
        .flatMap:
          case (character, position) =>
            if character.isDigit then Some(character.asDigit)
            else
              val precedingCharacters = line.take(position + 1)
              digitsToReplace.find: digit =>
                precedingCharacters.endsWith(wordForDigit(digit))
  yield (
    s"${digitsInLine.head}${digitsInLine.last}".toInt
  )

part2(example2)

part2(example2).sum

val input2 = os.read(pwd / "day01.input2.txt").split("\n").toList

println("Part 2: " + part2(input2).sum)

part2(
  List(
    "3gbtdlblgp", // 33
    "eighthree",  // 83
    "sevenine",   // 79
    "fiveight3"   // 53
  )
) // extra examples I needed to understand the problem
