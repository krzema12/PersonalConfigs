#!/usr/bin/env kscript

import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDir: File): String? {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

        proc.waitFor(60, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText()
    } catch(e: IOException) {
        e.printStackTrace()
        return null
    }
}

fun red(text: String) ="\u001B[31m$text\u001B[m"
fun greenBackground(text: String) = "\u001B[42m$text\u001B[m"

"git -c color.ui=always show --stat".runCommand(File("."))?.let { commandOutput ->
    val lines = commandOutput.lines()
    val justChangedFiles = lines.filter { it.contains(".java") }

    val names = justChangedFiles
            .mapNotNull {
                val matches = "/(\\w+).java".toRegex().find(it)
                matches?.groups?.get(1)?.value
            }
            .map {
                if (it.endsWith("Test")) {
                    it.removeSuffix("Test")
                } else {
                    it
                }
            }
            .distinct()

    val justChangedClasses = justChangedFiles.filter { !it.contains("Test.java") }
    val justChangedTests = justChangedFiles.filter { it.contains("Test.java") }

    names.forEach { name ->
        val foundChangedClass = justChangedClasses.find { fullLine
            -> fullLine.contains("/$name.java")
        }
        println(foundChangedClass ?: red("<NO PROD CODE CHANGED>"))

        val foundChangedTest = justChangedTests.find { fullLine
            -> fullLine.contains("/${name}Test.java")
        }
        println(foundChangedTest?.let { it.replace(it.substringBefore("|"), greenBackground(it.substringBefore("|"))) } ?: red("<NO TESTS CHANGED>"))
        println()
    }
}

