//> using scala 3
//> using toolkit latest
//> using dep "com.lihaoyi::pprint:0.8.1"

type ParsedInput = List[Array[Array[Int]]]

def parse(input: String): ParsedInput =
  input.trim.linesIterator
    .map(
      _.split(':').last
        .split('|')
        .map(
          _.split(' ')
            .filter(_.nonEmpty)
            .map(_.toInt)
        )
    )
    .toList

val example = parse("""
  |Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
  |Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
  |Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
  |Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
  |Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
  |Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
  |""".stripMargin)

def part1(input: ParsedInput) =
  input
    .map:
      case Array(numbers, winners, _*) =>
        numbers
          .intersect(winners)
          .foldLeft(0):
            case (0, _) => 1
            case (n, _) => n * 2
    .sum

part1(example)

val pwd = os.Path("/Users/madetech/projects/aoc/2023/scala")

val input = parse(os.read(pwd / "day04.input1.txt"))

println("Part01 : " + part1(input))

import scala.collection.mutable.ArrayBuffer

def part2(input: ParsedInput) =
  case class Card(matches: Int, copies: Int = 1):
    def createExtraCopies(n: Int): Card =
      copy(copies = copies + n)

  val cards = input
    .map:
      case Array(numbers, winners, _*) =>
        Card(numbers.intersect(winners).length)
    .to(ArrayBuffer)

  for
    i <- cards.indices
    n <- Range.inclusive(1, cards(i).matches)
  do cards(i + n) = cards(i + n).createExtraCopies(cards(i).copies)

  cards.map(_.copies).sum

part2(example)

println("Part02 : " + part2(input))


