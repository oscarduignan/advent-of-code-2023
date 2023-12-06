# Advent of Code 2023

Not sure how much I'll do with the amount of other stuff I have on my plate at the moment - never seem to make it to
double digits

Run with [Scala CLI](https://scala-cli.virtuslab.org/)

And recommend writing new solutions in [Worksheets](https://docs.scala-lang.org/scala3/book/tools-worksheets.html) so
you don't have to litter `println()` everywhere

## Day 1

This was challenging because of the approach I took at first, a few examples were missing that are needed to understand
the full behaviour. For example "fiveight" needs to be read as 58.

- [View solution source code](day01.sc)

```shell
scala-cli day01.sc
```

```text
Compiling project (Scala 3.3.1, JVM (11))
Compiled project (Scala 3.3.1, JVM (11))
Part 1: 55123
Part 2: 55260
```

## Day 2

Figured it would be some thing across columns rather than rows, didn't bother filling in blanks when a row didn't have a
draw of a colour and turned out I didn't need to anyway.

- [View solution source code](day02.sc)
- [View alternate solution source code where I used fastparse](day02.fastparse.sc)

```shell
scala-cli day02.sc
```

```
Compiling project (Scala 3.3.1, JVM (11))
Compiled project (Scala 3.3.1, JVM (11))
Part 1: 2256
Part 2: 74229
```

## Day 3

Got stuck on this where I wasn't handling lines that ended with digits correctly :oof:

- [View solution source code](day03.sc)

```shell
scala-cli day03.sc
```

```
Part01 (example):   4361
Part01 (my inputs): 539590
Part02 (example):   467835
Part02 (my inputs): 80703636
```

## Day 4

Got caught out by not fully understanding the scoping of assignments in for/do blocks

- [View solution source code](day04.sc)

```shell
scala-cli day04.sc
```

```
Compiling project (Scala 3.3.1, JVM (11))
Compiled project (Scala 3.3.1, JVM (11))
Part01 : 24848
Part02 : 7258152
```

## Day 5

Really bent my brain working out how to do it with ranges, but it's fast

All my part2 answers seem to be right but off by 1, I'm not sure what is happening...

Before I solved it with ranges I did it brute force, in reverse, and then ranges but each time it was off by one.

I didn't realise it was off by one until doing it in reverse and thinking "what if" and submitting my answer with 1
removed and it was correct...

So from there I decided I needed to solve it with ranges for my sanity, but that is off by one as well

Guess I have probably missed something obvious, the amount of time spent means this is the last advent of code for me
this year :oof:

> **Note:**
> found a way to fix off by one, however I don't understand why it works, or why the other solutions which don't have
> the same thing have the same problem. Is it something to do with overlapping? Are my solutions broken in some way the :
> imposter-syndrome-agony: this is bringing

- [View solution source code (brute forcing part 2 in two different ways, slow)](day05.sc)
- [View solution source code (using ranges, fast)](day05-ranges.sc)

```shell
scala-cli day05-ranges.sc
```

```
Compiling project (Scala 3.3.1, JVM (11))
Compiled project (Scala 3.3.1, JVM (11))
Part 01: 662197086
Part 02: 52510809
```
