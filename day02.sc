//> using scala 3
//> using toolkit latest
//> using dep "com.lihaoyi::pprint:0.8.1"

val pwd = os.Path("/Users/madetech/projects/aoc/2023/scala")

import scala.util.matching.Regex

val redDraws   = "([0-9]+) red".r
val greenDraws = "([0-9]+) green".r
val blueDraws  = "([0-9]+) blue".r

val example1 =
  """
    |Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    |Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    |Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    |Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    |Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    |""".stripMargin.trim.split("\n").toList

case class Game(id: Int, red: List[Int], green: List[Int], blue: List[Int])

def parseGames(input: List[String]) = input.map: line =>
  Game(
    line.takeWhile(_ != ':').split(" ").last.toInt,
    redDraws.findAllMatchIn(line).toList.map(_.group(1).toInt),
    greenDraws.findAllMatchIn(line).toList.map(_.group(1).toInt),
    blueDraws.findAllMatchIn(line).toList.map(_.group(1).toInt)
  )

def part1(games: List[Game]) =
  games
    .filter: game =>
      game.red.forall(_ <= 12) &&
        game.green.forall(_ <= 13) &&
        game.blue.forall(_ <= 14)
    .foldLeft(0)(_ + _.id)

val example1Games = parseGames(example1)

part1(example1Games)

val games = parseGames(os.read(pwd / "day02.input1.txt").trim.split("\n").toList)

println("Part 1: " + part1(games))

def part2(games: List[Game]) =
  games
    .map: game =>
      game.red.max * game.green.max * game.blue.max
    .sum

part2(example1Games)

println("Part 2: " + part2(games))
