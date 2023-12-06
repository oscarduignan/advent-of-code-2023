//> using scala 3
//> using dep "com.lihaoyi::fastparse:3.0.2"
//> using dep "com.lihaoyi::pprint:0.8.1"
//> using dep "org.scala-lang.modules::scala-parallel-collections:1.0.4"

import scala.annotation.tailrec
import scala.collection.SortedMap
import scala.collection.immutable.NumericRange
import java.nio.file.{Files, Path, Paths}
import java.nio.charset.StandardCharsets

type Mappings = Seq[Seq[Seq[(Long, Long)]]]

case class Almanac(seedToLocation: Mappings):
  val locationToSeed = seedToLocation.reverse.map(_.map(_.reverse))

  private def find(mappings: Mappings, input: Long) =
    mappings.foldLeft(input):
      case (lastValue, mapping) =>
        mapping.find(range =>
          val (lowerBound, upperBound) = range.last
          lastValue >= lowerBound && lastValue <= upperBound
        ) match
          case Some((targetStartIndex, _) :: (sourceStartIndex, _) :: Nil) =>
            targetStartIndex + (lastValue - sourceStartIndex)
          case _                                                           =>
            lastValue

  def getPlantingLocation(seed: Long) = find(seedToLocation, seed)
  def seedForLocation(location: Long) = find(locationToSeed, location)

object ParseAlmanac:
  import fastparse.*, SingleLineWhitespace.*
  def nl[$: P]             = P("\n")
  def number[$: P]         = CharIn("0-9").repX(1).!.map(_.toLong)
  def ranges[$: P]         = (number.rep(1) ~ nl).rep.map:
    _.map:
      case startA :: startB :: length :: Nil =>
        (startA, startA + length) :: (startB, startB + length) :: Nil
  def mappings[$: P]       = P(nl ~ CharIn("a-z\\-").repX ~ "map:" ~ nl ~ ranges)
  def almanac[$: P]        = mappings.rep.map(Almanac.apply)
  def seeds[$: P]          = P("seeds:" ~ number.rep ~ nl)
  def parser[$: P]         = P(seeds ~ almanac)
  def apply(input: String) = parse(input.trim, parser(_)) match
    case Parsed.Success(result, _) => Some(result)
    case _                         => None

val example = ParseAlmanac("""
    |seeds: 79 14 55 13
    |
    |seed-to-soil map:
    |50 98 2
    |52 50 48
    |
    |soil-to-fertilizer map:
    |0 15 37
    |37 52 2
    |39 0 15
    |
    |fertilizer-to-water map:
    |49 53 8
    |0 11 42
    |42 0 7
    |57 7 4
    |
    |water-to-light map:
    |88 18 7
    |18 25 70
    |
    |light-to-temperature map:
    |45 77 23
    |81 45 19
    |68 64 13
    |
    |temperature-to-humidity map:
    |0 69 1
    |1 0 69
    |
    |humidity-to-location map:
    |60 56 37
    |56 93 4
    |""".stripMargin)

// feel like it would have been a lot quicker for me to split strings

val (seeds, almanac) = example.get

//val (seeds, almanac) = ParseAlmanac(
//  Files.readString(
//    Path.of("day05.input1.txt"),
//    StandardCharsets.UTF_8
//  )
//).get

println("Part 01: " + seeds.map(almanac.getPlantingLocation).min)

// I've commented out the below because I rewrote using ranges, see [[day05-ranges.sc]]

// In reverse

//val seedRanges = seeds
//  .grouped(2)
//  .map(range => range.head until (range.head + range.last))
//  .toSeq
//
//@tailrec
//def part2(seedRanges: Seq[NumericRange[Long]], currentLocation: Long = 0): Long =
//  val seed = almanac.seedForLocation(currentLocation)
//  if seedRanges.exists(_ contains seed)
//  then currentLocation
//  else part2(seedRanges, currentLocation + 1)
//
//println("Part 02: " + part2(seedRanges))

// Brute force

//import scala.collection.parallel.CollectionConverters.*
//println(
//  "Part 02: " + seeds
//    .grouped(2)
//    .toSeq
//    .par
//    .map: range =>
//      val start = range.head
//      val end   = start + range.last
//      (start to end).foldLeft(Long.MaxValue)((smallestSoFar, seed) =>
//        smallestSoFar.min(almanac.getPlantingLocation(seed)))
//    .min
//)
