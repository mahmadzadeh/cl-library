package com.utils

import java.io.{PrintWriter, File}

import scala.util.Try

object FileUtil {

    def write(content:String)(file:File)(op: java.io.PrintWriter => Unit ): Try[Unit] = Try {
        val printWriter = new PrintWriter(file, "UTF-8")
        try {
            op( printWriter)
        } finally {
            printWriter.close()
        }
    }


}
