package dev.capslock.scalatestplayjson

import org.scalatest.flatspec._
import org.scalatest.matchers.should._

import play.api.libs.json.{JsNull, Json, JsValue, JsNumber, JsObject, JsArray, JsString, JsBoolean, JsTrue, JsFalse}

class EqualitySpec extends AnyFlatSpec with Matchers {
  "Equality" should "pass fundamental test" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"b": 2, "a": 1}""")
    json1 shouldEqual json2
  }

  it should "true === true" in {
    import Equality.default._

    val json1 = JsBoolean(true)
    val json2 = JsBoolean(true)
    json1 shouldEqual json2
  }

  it should "false === false" in {
    import Equality.default._

    val json1 = JsBoolean(false)
    val json2 = JsBoolean(false)
    json1 shouldEqual json2
  }

  it should "true !== false" in {
    import Equality.default._

    val json1 = JsBoolean(true)
    val json2 = JsBoolean(false)
    json1 should not equal json2
  }

  it should "false !== true" in {
    import Equality.default._

    val json1 = JsBoolean(false)
    val json2 = JsBoolean(true)
    json1 should not equal json2
  }

  it should "null === null" in {
    import Equality.default._

    val json1 = JsNull
    val json2 = JsNull
    json1 shouldEqual json2
  }
  // TODO: not null test

  it should "1 === 1" in {
    import Equality.default._

    val json1 = JsNumber(1)
    val json2 = JsNumber(1)
    json1 shouldEqual json2
  }

  it should "1 !== 2" in {
    import Equality.default._

    val json1 = JsNumber(1)
    val json2 = JsNumber(2)
    json1 should not equal json2
  }

  it should "1.0 === 1.0" in {
    import Equality.default._

    val json1 = JsNumber(1.0)
    val json2 = JsNumber(1.0)
    json1 shouldEqual json2
  }

  it should "1.0 !== 1.1" in {
    import Equality.default._

    val json1 = JsNumber(1.0)
    val json2 = JsNumber(1.1)
    json1 should not equal json2
  }

  it should """"foo" === "foo"""" in {
    import Equality.default._

    val json1 = JsString("foo")
    val json2 = JsString("foo")
    json1 shouldEqual json2
  }

  it should """"foo" !== "bar"""" in {
    import Equality.default._

    val json1 = JsString("foo")
    val json2 = JsString("bar")
    json1 should not equal json2
  }

  it should "[] === []" in {
    import Equality.default._

    val json1 = JsArray.empty
    val json2 = JsArray.empty
    json1 shouldEqual json2
  }

  it should "[1, 2] === [1, 2]" in {
    import Equality.default._

    val json1 = Json.arr(1, 2)
    val json2 = Json.arr(1, 2)
    json1 shouldEqual json2
  }

  it should "[1, 2] !== [2, 1]" in {
    import Equality.default._

    val json1 = Json.arr(1, 2)
    val json2 = Json.arr(2, 1)
    json1 should not equal json2
  }

  it should "[1, 2] !== [1, 2, 3]" in {
    import Equality.default._

    val json1 = Json.arr(1, 2)
    val json2 = Json.arr(1, 2, 3)
    json1 should not equal json2
  }

  it should "[1, 2] !== [1]" in {
    import Equality.default._

    val json1 = Json.arr(1, 2)
    val json2 = Json.arr(1)
    json1 should not equal json2
  }

  it should """{"a": 1, "b": 2} === {"b": 2, "a": 1}""" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"b": 2, "a": 1}""")
    json1 shouldEqual json2
  }

  it should """{"a": 1, "b": 2} !== {"a": 1, "b": 3}""" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1, "b": 3}""")
    json1 should not equal json2
  }

  it should """{"a": 1, "b": 2} !== {"a": 1}""" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1}""")
    json1 should not equal json2
  }

  it should """{"a": 1, "b": 2} !== {"a": 1, "b": 2, "c": 3}""" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1, "b": 2, "c": 3}""")
    json1 should not equal json2
  }

  it should """{"a": 1, "b" : {"c": 2}} === {"b": {"c": 2}, "a": 1}""" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b" : {"c": 2}}""")
    val json2 = Json.parse("""{"b": {"c": 2}, "a": 1}""")
    json1 shouldEqual json2
  }

  it should """{"a": 1, "b" : {"c": 2}} !== {"b": {"c": 3}, "a": 1}""" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b" : {"c": 2}}""")
    val json2 = Json.parse("""{"b": {"c": 3}, "a": 1}""")
    json1 should not equal json2
  }

  it should "treat number in very close range as equal" in {
    import Equality.default._

    val json1 = JsNumber(1)
    val json2 = JsNumber(0.3 * 3 + 0.1) // alike .9999999999999999
    json1 shouldEqual json2
  }

  it should "treat number in very close range as equal (for JsValue)" in {
    import Equality.default._

    val json1: JsValue = JsNumber(1)
    val json2: JsValue = JsNumber(0.3 * 3 + 0.1) // alike .9999999999999999
    json1 shouldEqual json2
  }

  it should "treat number in very close range as equal (using sugar)" in {
    import Equality.default._

    import Equality.jsNumberIsNumeric

    val json1 = JsNumber(1)
    val json2 = JsNumber(0.3 * 3 + 0.1) // alike .9999999999999999
    // we can use this syntax because JsNumber can be scala.math.Numeric
    json1 shouldEqual json2 +- JsNumber(0.00000000001)
  }

  it should "distinct number beyond tolerance" in {
    import Equality.jsNumberIsNumeric

    val json1 = JsNumber(1.0000)
    val json2 = JsNumber(1.00001)

    json1 shouldEqual json2 +- JsNumber(0.0001)
    json1 shouldEqual json2 +- JsNumber(0.00001)
    json1 should not equal json2 +- JsNumber(0.000001)
  }

  it should "compare object with very close number" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1, "b": 2.0000000000000001}""")
    // default tolerance is applied
    json1 shouldEqual json2
  }

  it should "compare object with very close number (beyond tolerance)" in {
    import Equality.default._

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1, "b": 2.000000000000001}""")
    // default tolerance is applied
    json1 shouldEqual json2
  }

  it should "compare object with very close number (using sugar)" in {
    import Equality.{jsonEquality, jsNumberEquality}

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1, "b": 3}""")
    json1 should equal(json2)(decided by jsonEquality(jsNumberEquality(1)))
  }

  it should "compare object with very close number (implicit form)" in {
    import Equality.{jsonEquality, jsNumberEquality} // you need jsonEquality to provide context
    implicit val jEq: org.scalactic.Equality[JsNumber] = jsNumberEquality(1)

    val json1 = Json.parse("""{"a": 1, "b": 2}""")
    val json2 = Json.parse("""{"a": 1, "b": 3}""")
    json1 shouldEqual json2
  }
}
