# Advent of Code 2023

Not sure how much I'll do with the amount of other stuff I have on my plate at the moment - never seem to make it to double digits

Run with [Scala CLI](https://scala-cli.virtuslab.org/)

And recommend writing new solutions in [Worksheets](https://docs.scala-lang.org/scala3/book/tools-worksheets.html) so you don't have to litter `println()` everywhere

## Day 1
This was challenging because of the approach I took at first, a few examples were missing that are needed to understand the full behaviour. For example "fiveight" needs to be read as 58.

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

```shell
scala-cli day03.sc
```
```
Example: ParsedInput(
  symbols = List(
    ('*', (1, 3)),
    ('#', (3, 6)),
    ('*', (4, 3)),
    ('+', (5, 5)),
    ('$', (8, 3)),
    ('*', (8, 5))
  ),
  numbers = List(
    Number(number = 467, coords = List((0, 0), (0, 1), (0, 2))),
    Number(number = 114, coords = List((0, 5), (0, 6), (0, 7))),
    Number(number = 35, coords = List((2, 2), (2, 3))),
    Number(number = 633, coords = List((2, 6), (2, 7), (2, 8))),
    Number(number = 1, coords = List((3, 10))),
    Number(number = 617, coords = List((4, 0), (4, 1), (4, 2))),
    Number(number = 58, coords = List((5, 7), (5, 8))),
    Number(number = 592, coords = List((6, 2), (6, 3), (6, 4))),
    Number(number = 755, coords = List((7, 6), (7, 7), (7, 8))),
    Number(number = 664, coords = List((9, 1), (9, 2), (9, 3))),
    Number(number = 598, coords = List((9, 5), (9, 6), (9, 7)))
  )
)
Example: parts = List(
  Number(number = 467, coords = List((0, 0), (0, 1), (0, 2))),
  Number(number = 35, coords = List((2, 2), (2, 3))),
  Number(number = 633, coords = List((2, 6), (2, 7), (2, 8))),
  Number(number = 617, coords = List((4, 0), (4, 1), (4, 2))),
  Number(number = 592, coords = List((6, 2), (6, 3), (6, 4))),
  Number(number = 755, coords = List((7, 6), (7, 7), (7, 8))),
  Number(number = 664, coords = List((9, 1), (9, 2), (9, 3))),
  Number(number = 598, coords = List((9, 5), (9, 6), (9, 7)))
)
Example: gears = List(
  (
    ('*', (1, 3)),
    List(
      Number(number = 467, coords = List((0, 0), (0, 1), (0, 2))),
      Number(number = 35, coords = List((2, 2), (2, 3)))
    )
  ),
  (
    ('*', (8, 5)),
    List(
      Number(number = 755, coords = List((7, 6), (7, 7), (7, 8))),
      Number(number = 598, coords = List((9, 5), (9, 6), (9, 7)))
    )
  )
)
Part01 (example):   4361
Part01 (my inputs): 539590
Part02 (example):   467835
Part02 (my inputs): 80703636
```
