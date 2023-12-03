//> using scala 3
//> using toolkit latest
//> using dep "com.lihaoyi::pprint:0.8.1"

import scala.annotation.tailrec

type Coord = (Int, Int)
extension (coord: Coord)
  def x: Int = coord._1
  def y: Int = coord._2

type Cell = (Char, Coord)
extension (cell: Cell)
  def value = cell._1
  def coord = cell._2

case class Number(number: Int, coords: List[Coord]):
  val neighbouringRows: Set[Int] =
    coords.flatMap(coord => List(coord.x + 1, coord.x, coord.x - 1)).toSet
  val neighbouringCols: Set[Int] =
    coords.flatMap(coord => List(coord.y + 1, coord.y, coord.y - 1)).toSet
  def isNeighbour(cell: Cell): Boolean =
    neighbouringRows.contains(cell.coord.x) &&
    neighbouringCols.contains(cell.coord.y)

case class ParsedInput(symbols: List[Cell], numbers: List[Number]):
  val parts = numbers.filter: number =>
      symbols.exists(number.isNeighbour)
  val gears = symbols
    .filter(_.value == '*')
    .map(cell => cell -> numbers.filter(_.isNeighbour(cell)))
    .filter(_._2.length == 2)

val example1 =
  """
    |467..114..
    |...*......
    |..35..633.
    |......#...1
    |617*......
    |.....+.58.
    |..592.....
    |......755.
    |...$.*....
    |.664.598..
    |""".stripMargin.trim.split("\n").toList

// foolishly I turned my list of strings into a single list of cells and forgot to
// handle the case where there's a number on the end of a line, so to fix this I
// just append a period to the end of each line when flattening them into a list.
// Changed other bits trying to spot my error and will leave those in, I'm sure
// that it's totally over-engineered and inefficient but at least I got there 😅
def cells(input: List[String]): List[Cell] =
  input.indices.flatMap(x =>
    (input(x) + ".").zipWithIndex.map: value =>
      value._1 -> (x, value._2)
  ).toList

@tailrec
def parse(input: List[Cell], symbolsFound: List[Cell] = Nil, numbersFound: List[Number] = Nil): ParsedInput =
  if input.isEmpty then
    ParsedInput(symbolsFound, numbersFound)
  else
    val (digits, rest) = input.span(_.value.isDigit)
    val (chars, remainder) = rest.span(!_.value.isDigit)
    parse(
      remainder,
      symbolsFound ++ chars.filter(_.value != '.'),
      if digits.isEmpty
        then numbersFound
        else numbersFound :+ Number(
          digits.map(_.value).mkString.toInt,
          digits.map(_.coord)
        ))

val parsedExample = parse(cells(example1))

print("Example: ")
pprint.pprintln(parsedExample)

print("Example: parts = ")
pprint.pprintln(parsedExample.parts)

print("Example: gears = ")
pprint.pprintln(parsedExample.gears)

val pwd = os.Path("/Users/madetech/projects/aoc/2023/scala")
val input = parse(cells(os.read(pwd / "day03.input1.txt").linesIterator.toList))

def part1(input: ParsedInput): Int = input.parts.map(_.number).sum

println("Part01 (example):   " + part1(parsedExample))
println("Part01 (my inputs): " + part1(input))

def part2(input: ParsedInput): Int =
  input.gears.map(_._2.map(_.number).product).sum

println("Part02 (example):   " + part2(parsedExample))
println("Part02 (my inputs): " + part2(input))
