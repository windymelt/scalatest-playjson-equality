package dev.capslock.scalatestplayjson

import org.scalactic._
import play.api.libs.json.{JsNull, Json, JsValue, JsNumber, JsObject, JsArray, JsString, JsBoolean, JsTrue, JsFalse}
import org.scalactic.TolerantNumerics
import TripleEquals._
import java.math.MathContext
import scala.math.BigDecimal.RoundingMode

object Equality {

  /** Provides default equality for JsValue.
    * @see
    *   [[dev.capslock.scalatestplayjson.Equality.default.DEFAULT_TOLERANCE]]
    */
  object default {

    /** The default tolerance for comparing [[play.api.libs.json.JsNumber]]. `0.00000000001`.
      */
    val DEFAULT_TOLERANCE = BigDecimal(10) pow -11

    implicit val jsNumberDefaultEquality: org.scalactic.Equality[JsNumber] = jsNumberEquality(DEFAULT_TOLERANCE)
    implicit val jsValueDefaultEquality: org.scalactic.Equality[JsValue]   = jsonEquality(jsNumberDefaultEquality)
  }

  /** Defines equality for [[play.api.libs.json.JsValue]]. Requires implicit [[org.scalactic.Equality[JsNumber]]] to
    * provide numerical tolerance.
    *
    * @param jsNumberEquality
    * @return
    */
  implicit def jsonEquality(implicit
      jsNumberEquality: org.scalactic.Equality[JsNumber],
  ): org.scalactic.Equality[JsValue] = new org.scalactic.Equality[JsValue] {
    def areEqual(a: JsValue, b: Any): Boolean = {
      b match {
        case js: JsValue =>
          a match {
            case o: JsObject =>
              js match {
                case o2: JsObject =>
                  o.fields.forall { case (k, v) =>
                    o2.value.get(k) match {
                      case Some(v2) => areEqual(v, v2)
                      case None     => false
                    }
                  } && o2.fields.forall { case (k, v) =>
                    o.value.get(k) match {
                      case Some(v2) => areEqual(v, v2)
                      case None     => false
                    }
                  }
                case _ => false
              }
            case a: JsArray =>
              js match {
                case a2: JsArray =>
                  a.value.zip(a2.value).forall { case (v, v2) => areEqual(v, v2) }
                case _ => false
              }
            case JsString(x) =>
              js match {
                case JsString(y) => x === y
                case _           => false
              }
            case JsBoolean(x) =>
              js match {
                case JsBoolean(y) => x === y
                case _            => false
              }
            case JsTrue =>
              js match {
                case JsTrue => true
                case _      => false
              }
            case JsFalse =>
              js match {
                case JsFalse => true
                case _       => false
              }
            case JsNull =>
              js match {
                case JsNull => true
                case _      => false
              }
            case x: JsNumber => {
              js match {
                case y: JsNumber => {
                  jsNumberEquality.areEquivalent(x, y)
                }
                case _ => false
              }
            }
          }
        case _ => false
      }
    }
  }

  /** Defines equality for [[play.api.libs.json.JsNumber]] with tolerance. When with tolerance, it will compare two
    * JsNumber with the given tolerance. For example, when tolerance is `t` and given value is `x`, it will compare `x -
    * t <= y <= x + t`.
    *
    * @param tolerance
    * @return
    */
  def jsNumberEquality(tolerance: BigDecimal): Equality[JsNumber] = new Equality[JsNumber] {
    def areEqual(a: JsNumber, b: Any): Boolean = {
      b match {
        case JsNumber(y) =>
          (a.value <= y + tolerance) && (a.value >= y - tolerance)
        case _ => false
      }
    }
  }

  /** Prove that [[play.api.libs.json.JsNumber]] is Numeric. This enables `+-` operator for `JsNumber`.
    *
    * @example
    *   {{{ import org.scalatest.matchers.should._ import Equality.jsNumberIsNumeric
    *
    * val json1 = JsNumber(1.0000) val json2 = JsNumber(1.00001)
    *
    * json1 shouldEqual json2 +- JsNumber(0.0001) // => true }}}
    */
  implicit val jsNumberIsNumeric: Numeric[JsNumber] = new Numeric[JsNumber] {
    def plus(x: JsNumber, y: JsNumber): JsNumber   = JsNumber(x.value + y.value)
    def minus(x: JsNumber, y: JsNumber): JsNumber  = JsNumber(x.value - y.value)
    def times(x: JsNumber, y: JsNumber): JsNumber  = JsNumber(x.value * y.value)
    def negate(x: JsNumber): JsNumber              = JsNumber(-x.value)
    def fromInt(x: Int): JsNumber                  = JsNumber(x)
    def toInt(x: JsNumber): Int                    = x.value.toInt
    def toLong(x: JsNumber): Long                  = x.value.toLong
    def toFloat(x: JsNumber): Float                = x.value.toFloat
    def toDouble(x: JsNumber): Double              = x.value.toDouble
    def compare(x: JsNumber, y: JsNumber): Int     = x.value.compare(y.value)
    def parseString(str: String): Option[JsNumber] = Json.parse(str).asOpt[JsNumber]
  }

}
