package com.cl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Assertions, BeforeAndAfter, FunSuite}
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class MonitorExecutionResultTest extends FunSuite with BeforeAndAfter with Assertions with MockitoSugar{

    test("create monitor execution result") {
        val  execResult= new MonitorExecutionResult()

        assert(execResult !=null)
    }

    test("compare two monitor results") {
        val  execResult1 = new MonitorExecutionResult()
        val  execResult2  = new MonitorExecutionResult()

        assert(execResult1.compare(execResult2) != null)
    }
}
