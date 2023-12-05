//> using scala 3
//> using dep "com.lihaoyi::fastparse:3.0.2"
//> using dep "com.lihaoyi::pprint:0.8.1"

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.util.Using

enum Colour:
  case blue, red, green
type Count = Int
case class Game(id: Int, draws: Map[Colour, Seq[Count]])

type Games = Seq[Game]

object Games:
  import fastparse.*, MultiLineWhitespace.*
  def draw[$: P]           = P(CharIn("0-9").rep(1).!.map(_.toInt) ~ CharIn("a-z").rep(1).!.map(Colour.valueOf) ~ CharIn(",;").?)
  def draws[$: P]          = P(draw.rep(1)).map(_.groupBy(_._2).map(_ -> _.map(_._1)))
  def game[$: P]           = P("Game" ~ CharIn("0-9").rep.!.map(_.toInt) ~ ":" ~ draws).map(Game.apply)
  def games[$: P]          = P("\n".? ~ game.rep ~ End)
  def apply(input: String) = parse(input, games(_)) match
    case Parsed.Success(gamesFound, _) => gamesFound
    case _                             => Nil

val example = Games("""
    |Game 1: 3 blue, 13 red; 2 blue, 5 green
    |Game 2: 2 blue, 5 green, 3 red; 1 blue
    |""".stripMargin)

extension (games: Games)
  def part1 =
    games
      .filter: game =>
        game.draws.getOrElse(Colour.red, Nil).forall(_ <= 12) &&
          game.draws.getOrElse(Colour.green, Nil).forall(_ <= 13) &&
          game.draws.getOrElse(Colour.blue, Nil).forall(_ <= 14)
      .foldLeft(0)(_ + _.id)

example.part1

extension (games: Games)
  def part2 =
    games
      .map: game =>
        game.draws.getOrElse(Colour.red, Nil).max * game.draws.getOrElse(Colour.green, Nil).max * game.draws.getOrElse(Colour.blue, Nil).max
      .sum

example.part2

val input = Games(
  Files.readString(
    Path.of("day02.input1.txt"),
    StandardCharsets.UTF_8
  )
)
println("Part 1: " + input.part1)
println("Part 2: " + input.part2)
