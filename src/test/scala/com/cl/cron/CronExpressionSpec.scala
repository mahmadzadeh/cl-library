package com.cl.cron

import org.scalatest.{Matchers, FlatSpec}
import scala.util.{Success, Failure, Try}

class CronExpressionSpec extends FlatSpec with Matchers {

    behavior of "cron expression every n seconds"

    it should "be able to constructed" in {
        val c = new CronExpression
    }

    it should "create a cron expression for every 22 seconds" in {
        val c = new CronExpression

        assertResult("22 * * * ? *") {
            c.everyNSeconds(22)
        }
    }

    it should "n has to be 0 <=n<=59 " in {
        val c = new CronExpression

        intercept[IllegalArgumentException] {
            c.everyNSeconds(-1)
        }

        assertResult("0 * * * ? *") {
            c.everyNSeconds(0)
        }

        assertResult("59 * * * ? *") {
            c.everyNSeconds(59)
        }

        intercept[IllegalArgumentException] {
            c.everyNSeconds(60)
        }
    }

    behavior of "cron expression every n minutes"

    it should "n has to be 0 <=n<=59 " in {
        val c = new CronExpression

        intercept[IllegalArgumentException] {
            c.everyNMinutes(-1)
        }

        assertResult("* 0 * * ? *") {
            c.everyNMinutes(0)
        }

        assertResult("* 59 * * ? *") {
            c.everyNMinutes(59)
        }

        intercept[IllegalArgumentException] {
            c.everyNMinutes(60)
        }
    }

    behavior of "cron expression executed at hour n"

    it should "n has to be 0 <=n<=23 " in {
        val c = new CronExpression

        intercept[IllegalArgumentException] {
            c.atHourN(-1)
        }

        assertResult("0 0 0 * ? *") {
            c.atHourN(0)
        }

        assertResult("0 0 23 * ? *") {
            c.atHourN(23)
        }

        intercept[IllegalArgumentException] {
            c.atHourN(60)
        }
    }

    behavior of "cron expression executed at n passed hour m"

    it should "n has to be 0 <=n<=23 " in {
        val c = new CronExpression

        intercept[IllegalArgumentException] {
            c.atNMinutesPassedHourM(-1, 0)
        }

        intercept[IllegalArgumentException] {
            c.atNMinutesPassedHourM(0, 24)
        }

        intercept[IllegalArgumentException] {
            c.atNMinutesPassedHourM(-1, -1)
        }

        assertResult("0 0 23 * ? *") {
            c.atNMinutesPassedHourM(0, 23)
        }

        assertResult("0 20 23 * ? *") {
            c.atNMinutesPassedHourM(20, 23)
        }

        intercept[IllegalArgumentException] {
            c.atNMinutesPassedHourM(60, 23)
        }
    }

    behavior of "cron expression executed at every hour"

    it should "return 0 0 * * ? * " in {
        val c = new CronExpression

        assertResult("0 0 * * ? *") {
            c.everyHour
        }
    }

    behavior of "cron expression executed at every N hour"

    it should "be between 0 and 23" in {
        val c = new CronExpression

        assertResult("0 0 0/2 * ? *") {
            c.everyNHour(2)
        }

        intercept[IllegalArgumentException] {
            c.everyNHour(50)
        }
    }
}
