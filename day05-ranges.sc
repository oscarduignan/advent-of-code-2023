//> using scala 3
//> using dep "com.lihaoyi::fastparse:3.0.2"
//> using dep "com.lihaoyi::pprint:0.8.1"
//> using dep "org.scala-lang.modules::scala-parallel-collections:1.0.4"

import scala.annotation.tailrec
import scala.collection.immutable.NumericRange
import java.nio.file.{Files, Path, Paths}
import java.nio.charset.StandardCharsets

@tailrec
def translate(
    range: NumericRange.Inclusive[Long],
    mappings: Seq[(NumericRange.Inclusive[Long], NumericRange.Inclusive[Long])],
    rangesPreviouslyFound: Seq[NumericRange.Inclusive[Long]] = Nil
): Seq[NumericRange.Inclusive[Long]] =
  if mappings.isEmpty then rangesPreviouslyFound :+ range
  else
    val (from, into) = mappings.head

    if range.end < from.start then
      // no more to translate because our mappings are sorted
      rangesPreviouslyFound :+ range
    else if range.start > from.end then
      // we can't begin with this mapping
      translate(range, mappings.tail) // omitted translated because it will always be Nil here
    else

      val before =
        if range.start < from.start
        then Some(range.start to (from.start - 1))
        else None

      val covering =
        if range.start < from.end
        then
          // we need to shift the values

          val diff = into.start - from.start

          val translatedStart =
            if range.start > from.start
            then range.start + diff
            else into.start

          val translatedEnd =
            if range.end < from.end
            then range.end + diff
            else into.end

          Some(translatedStart to translatedEnd)
        else None

      val rangesNowFound = rangesPreviouslyFound ++ Seq(before, covering).flatten

      if range.end <= from.end then
        // no more range to translate, so we are finished even if there are other mappings
        rangesNowFound
      else
        // we have more to map (if there are any more mappings, which we check at the top)
        translate(
          (from.end + 1) to range.end,
          mappings.tail,
          rangesNowFound
        )

object ParseAlmanac:
  import fastparse.*, SingleLineWhitespace.*
  def nl[$: P]             = P("\n")
  def number[$: P]         = CharIn("0-9").repX(1).!.map(_.toLong)
  def ranges[$: P]         = (number.rep(1) ~ nl).rep.map:
    _.map { case intoStart :: fromStart :: length :: Nil =>
      (fromStart to (fromStart + length), intoStart to (intoStart + length))
    }.sortBy(_._1.start) // even though I knew I needed it I forgot to add it
  def mappings[$: P]       = P(nl ~ CharIn("a-z\\-").repX ~ "map:" ~ nl ~ ranges)
  def almanac[$: P]        = mappings.rep
  def seeds[$: P]          = P("seeds:" ~ number.rep ~ nl)
  def parser[$: P]         = P(seeds ~ almanac)
  def apply(input: String) = parse(input.trim, parser(_)) match
    case Parsed.Success(result, _) => Some(result)
    case _                         => None

val input1 = ParseAlmanac("""
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

val input2 = ParseAlmanac(
  Files.readString(
    Path.of("day05.input1.txt"),
    StandardCharsets.UTF_8
  )
)

val (seeds, almanac) = input2.get

val seedRangesPart1 = seeds.map(seed => seed to seed)

// originally did NOT solve this with ranges :joy: but rewrote part1 in ranges to check my work

println(
  "Part 01: " + almanac
    .foldLeft(seedRangesPart1)((ranges, mappings) => ranges.flatMap(range => translate(range, mappings)))
    .map(_.start)
    .min
)

val seedRangesPart2 = seeds
  .grouped(2)
  .map: range =>
    range.head to range.head + range.last
  .toSeq

println(
  "Part 02: " + (almanac
    .foldLeft(seedRangesPart2)((ranges, mappings) => ranges.flatMap(range => translate(range, mappings)))
    .map(_.start)
    .min - 1)
)

// TODO why is this off by one? It must be my input?! Since doing in reverse and brute yielded same...

// what have I messed up
