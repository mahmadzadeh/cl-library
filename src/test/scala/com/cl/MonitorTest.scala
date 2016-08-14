package com.cl

import org.scalatest.{BeforeAndAfter, FunSuite, Assertions}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.cl.url.CLUrl

@RunWith(classOf[JUnitRunner])
class MonitorTest extends FunSuite with BeforeAndAfter with Assertions with MockitoSugar{


    test("create a monitor")  {
        val query = mock[CLUrl]

        val monitor = new Monitor(query)

        assert(monitor!=null)
    }

    test("execute a monitor")  {
        val query = mock[CLUrl]

        val monitor = new Monitor(query)

        val monitorResult = monitor.execute()

        assert(monitorResult !=null)
    }


}
