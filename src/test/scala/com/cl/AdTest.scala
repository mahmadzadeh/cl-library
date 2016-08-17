package com.cl

import com.cl.PosterDesperationLevel._
import com.cl.result.Ad
import com.utils.DateTimeUtil._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}

@RunWith(classOf[JUnitRunner])
class AdTest extends FunSuite with BeforeAndAfter {


    test("ad creation") {
        val ad = Ad("1234", "2006 BMW all wheel drive", 12345677, Some(12345677), "http://someURL.com")

        assert(ad != null)
    }

    test("ad comparison with two ads that are same") {
        val ad1 = Ad("1234", "2006 BMW all wheel drive", 12345677, Some(12345677), "http://someURL.com")
        val ad2 = Ad("1234", "2006 BMW all wheel drive", 12345677, Some(12345677), "http://someURL.com")

        assert(ad1 == ad2)
    }

    test("ad comparison with two ads that are different") {
        val ad1 = Ad("1234", "2006 BMW all wheel drive", 12345677, Some(12345677), "http://someURL.com")
        val ad2 = Ad("1233", "2005 BMW 325i", 12345677, Some(12345677), "http://someURL.com")

        assert(ad1 != ad2)
    }

    test("ad is posted before") {
        val ad1 = Ad("1234", "2006 BMW all wheel drive", 1378608494554L, Some(12345677), "http://someURL.com")
        val ad2 = Ad("1233", "2005 BMW 325i", 1378608499999L, Some(12345677), "http://someURL.com")

        assert(ad1.isPostedBefore(ad2))
    }

    test("poster desparation level for an ad posted 6 hours ago and not been updated") {
        val sixHoursAgoInMillis = System.currentTimeMillis() - millisInHours(5)

        val ad1 = Ad("1234", "2006 BMW all wheel drive", sixHoursAgoInMillis, None, "http://someURL.com")

        assert(HOPEFUL == ad1.posterDesperationLevel())

    }

    test("poster desparation level for an ad posted 6 hours ago and updated two hours ago") {
        val sixHoursAgoInMillis = System.currentTimeMillis() - millisInHours(5)
        val twoHoursAgoInMillis = System.currentTimeMillis() - millisInHours(2)

        val ad1 = Ad("1234", "2006 BMW all wheel drive", sixHoursAgoInMillis, Some(twoHoursAgoInMillis), "http://someURL.com")

        assert(HOPEFUL == ad1.posterDesperationLevel())

    }

    test("poster desparation level for an ad posted 1.5 days ago and updated two hours ago") {
        val oneAndHalfDaysAgoInMillis = System.currentTimeMillis() - millisInHours(1.5 * 24 toFloat)
        val twoHoursAgoInMillis = System.currentTimeMillis() - millisInHours(2)

        val ad1 = Ad("1234", "2006 BMW all wheel drive", oneAndHalfDaysAgoInMillis, Some(twoHoursAgoInMillis), "http://someURL.com")

        assert(HOPEFUL == ad1.posterDesperationLevel())

    }

    test("poster desparation level for an ad posted 2 days ago") {

        val now: Long = System.currentTimeMillis()
        val twoDaysAgoInMillis = now - millisInTwoDays()

        val ad1 = Ad("1234", "2006 BMW all wheel drive", twoDaysAgoInMillis, None, "http://someURL.com")

        assert(HOPEFUL == ad1.posterDesperationLevel(now))

    }

    test("poster desparation level for an ad posted 10 days ago") {

        val now: Long = System.currentTimeMillis()
        val tenDaysAgo = now - millisInDays(10)

        val ad1 = Ad("1234", "2006 BMW all wheel drive", tenDaysAgo, None, "http://someURL.com")

        assert(OPTIMISTIC == ad1.posterDesperationLevel(now))
    }

    test("poster desparation level for an ad posted 10 days and 10 seconds ago") {

        val now: Long = System.currentTimeMillis()
        val tenDaysAgo = now - millisInDays(10) - 10000

        val ad1 = Ad("1234", "2006 BMW all wheel drive", tenDaysAgo, None, "http://someURL.com")

        assert(DESPERATE == ad1.posterDesperationLevel(now))
    }

    test("poster desparation level for an ad posted 30 days ago") {

        val now = System.currentTimeMillis()
        val thirtyDaysAgo = now  - millisInDays(30)

        val ad1 = Ad("1234", "2006 BMW all wheel drive", thirtyDaysAgo, None, "http://someURL.com")

        assert(DESPERATE == ad1.posterDesperationLevel())
    }


    test("poster desparation level for an ad posted three months ago") {

        val now = System.currentTimeMillis()
        val thirtyDaysAgo = now  - millisInDays(90)

        val ad1 = Ad("1234", "2006 BMW all wheel drive", thirtyDaysAgo, None, "http://someURL.com")

        assert(DESPERATE == ad1.posterDesperationLevel(now))
    }
}




