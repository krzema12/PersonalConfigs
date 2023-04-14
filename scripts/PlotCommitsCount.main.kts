#!/usr/bin/env kotlin

// Usage: PlotCommitsCount.main.kts /path/to/repo

@file:DependsOn("org.eclipse.jgit:org.eclipse.jgit:6.5.0.202303070854-r")
@file:DependsOn("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:4.3.0")
@file:DependsOn("org.jetbrains.lets-plot:lets-plot-image-export:3.1.0")

import org.eclipse.jgit.api.Git
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.ggsize
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

val pathToRepo = args[0]

val repo = Git.open(File(pathToRepo))
var formatter = SimpleDateFormat("yyyy-MM-dd")

val datesInGroups = repo.log().call().map { commit ->
    val instant = Instant.ofEpochSecond(commit.commitTime.toLong())
    val date = Date.from(instant)
    formatter.format(date)
}.groupingBy { it.substring(0..6) }
    .eachCount()

val plotData = mapOf(
    "yearMonth" to datesInGroups.keys.sorted(),
    "count" to datesInGroups.entries.sortedBy { it.key }.map { it.value },
)

var plot = letsPlot(plotData) +
    geomBar(stat = Stat.identity) { x = "yearMonth"; y = "count" } +
    ggsize(1920, 1080) +
    ggtitle("Number of commits month by month")

ggsave(plot, "Commit history.png")
