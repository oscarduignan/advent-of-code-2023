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

val example1 =
  """
    |467..114..
    |...*......
    |..35..633.
    |......#...
    |617*......
    |.....+.58.
    |..592.....
    |......755.
    |...$.*....
    |.664.598..
    |""".stripMargin.trim.split("\n").toList

def cells(input: List[String]): List[Cell] = input.indices.flatMap(x =>
  input(x).zipWithIndex.map: value =>
    value._1 -> (x, value._2)
).toList

@tailrec
def parse(input: List[Cell], symbolsFound: List[Cell] = Nil, numbersFound: List[Number] = Nil): ParsedInput =
  if input.isEmpty then
    ParsedInput(symbolsFound, numbersFound)
  else
    input.span(_._1.isDigit) match
      case (digits, notDigits) if digits.isEmpty =>
        val (leadingChars, remainingInput) = notDigits.span(!_._1.isDigit)
        parse(remainingInput, symbolsFound ++ leadingChars.filter(_.value != '.'), numbersFound)
      case (digits, remainingInput) =>
        parse(remainingInput, symbolsFound, numbersFound :+ Number(
          digits.map(_._1).mkString.toInt,
          digits.map(_._2)
        ))

val parsedExample = parse(cells(example1))

print("Example: ")
pprint.pprintln(parsedExample)

print("Example: parts = ")
pprint.pprintln(parsedExample.parts)

def part1(input: ParsedInput): Int = input.parts.map(_.number).sum

println("Part01 (example):   " + part1(parsedExample))

val pwd = os.Path("/Users/madetech/projects/aoc/2023/scala")

val input1 = parse(cells(os.read(pwd / "day03.input1.txt").linesIterator.toList))

println("Part01 (my inputs): " + part1(input1))

// TODO
// what have I done wrong, number too high? but example matches
// there must be some edge cases that I haven't considered...
// are negative numbers a thing????? geez
