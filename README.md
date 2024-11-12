## ScalaTest Equality definition for Play JSON

[![CI](https://github.com/windymelt/scalatest-playjson-equality/actions/workflows/ci.yaml/badge.svg?branch=main)](https://github.com/windymelt/scalatest-playjson-equality/actions/workflows/ci.yaml) [![Latest version](https://index.scala-lang.org/windymelt/scalatest-playjson-equality/scalatest-play-json-equality/latest-by-scala-version.svg)](https://index.scala-lang.org/windymelt/scalatest-playjson-equality/scalatest-play-json-equality) [![scaladoc](https://javadoc.io/badge2/dev.capslock/scalatest-play-json-equality_3/scaladoc.svg)](https://javadoc.io/doc/dev.capslock/scalatest-play-json-equality_3)


### Install

On sbt:

```scala
libraryDependencies += "dev.capslock" %% "scalatest-play-json-equality" % scalaTestEqualityPlayJsonVersion
```

### Usage

You can compare `JsValue` with `JsNumber` tolerance in ScalaTest.

```scala
import dev.capslock.scalatestplayjson.Equality.default._

val json1 = Json.parse("""{"a": 1, "b": 2}""")
val json2 = Json.parse("""{"a": 1, "b": 2.0000000000000001}""")

// default tolerance is applied
json1 shouldEqual json2
```

You can specify tolerance:

```scala
import dev.capslock.scalatestplayjson.Equality.{jsonEquality, jsNumberEquality}

val json1 = Json.parse("""{"a": 1, "b": 2}""")
val json2 = Json.parse("""{"a": 1, "b": 3}""")
json1 should equal(json2)(decided by jsonEquality(jsNumberEquality(1)))
```

This is equivalent to:

```scala
import dev.capslock.scalatestplayjson.Equality.{jsonEquality, jsNumberEquality} // you need jsonEquality to provide context
implicit val jEq: org.scalactic.Equality[JsNumber] = jsNumberEquality(1)

val json1 = Json.parse("""{"a": 1, "b": 2}""")
val json2 = Json.parse("""{"a": 1, "b": 3}""")
json1 shouldEqual json2
```

See test to more example.

## Release

```sh
dotenvx run -- sbt -v 'clean; +publishSigned; sonatypeBundleRelease'
```