# Advent of Code 2023

Not sure how much I'll do with the amount of other stuff I have on my plate at the moment - never seem to make it to double digits

Run with [Scala CLI](https://scala-cli.virtuslab.org/)

And recommend writing new solutions in [Worksheets](https://docs.scala-lang.org/scala3/book/tools-worksheets.html) so you don't have to litter `println()` everywhere

## Day 1
This was challenging because of the approach I took at first, a few examples were missing that are needed to understand the full behaviour. For example "fiveight" needs to be read as 58.

[View solution source code](day01.sc)

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
Figured it would be some thing across columns rather than rows, didn't bother filling in blanks when a row didn't have a draw of a colour and turned out I didn't need to anyway.

[View solution source code](day02.sc)

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

[View solution source code](day03.sc)

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

[View solution source code](day04.sc)

```shell
scala-cli day04.sc
```
```
Compiling project (Scala 3.3.1, JVM (11))
Compiled project (Scala 3.3.1, JVM (11))
Part01 : 24848
Part02 : 7258152
```

