package com.cl

import scala.io.Source
import java.io.File

trait FileBasedTest {
    def readFile(fileName: String): String = {

        val source = Source.fromFile(new File("src/test/resources/" + fileName), "UTF-8")
        val lines = source.mkString

        source.close()

        lines


    }
}
