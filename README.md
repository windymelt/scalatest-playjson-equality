## ScalaTest Equality definition for Play JSON

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