#!/usr/bin/env kotlin

@file:Repository("https://ajoberstar.org/bintray-backup/ajoberstar-backup")
@file:DependsOn("org.codehaus.groovy:groovy:3.0.5")
@file:DependsOn("org.ajoberstar.grgit:grgit-core:4.0.2")
@file:DependsOn("org.slf4j:slf4j-simple:1.7.30")

import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.operation.BranchListOp
import kotlin.system.exitProcess

fun red(text: String) = "\u001B[31m$text\u001B[0m"
fun green(text: String) = "\u001B[32m$text\u001B[0m"
fun blue(text: String) = "\u001B[34m$text\u001B[0m"

val grgit = Grgit.open(mapOf("currentDir" to "."))
val remote = "origin"

val localBranches = grgit.branch.list(mapOf("mode" to BranchListOp.Mode.LOCAL))
println("Local branches:")
localBranches.forEach { branch ->
    println("  ${green(branch.name)}")
}

val remoteBranches = grgit.branch.list(mapOf("mode" to BranchListOp.Mode.REMOTE))
println("Remote branches:")
remoteBranches.forEach { branch ->
    println("  ${red(branch.name)}")
}

val branchesToRemove = localBranches.filter { branch ->
    remoteBranches.firstOrNull { it.name == "$remote/${branch.name}" } == null
}

println()

if (branchesToRemove.isEmpty()) {
    println("No branches to remove, finishing.")
    exitProcess(0)
}

println("Candidates to remove:")
branchesToRemove.forEach { branch ->
    println("  ${blue(branch.name)}")
}

println("Asking branch by branch:")

branchesToRemove.forEach { branch ->
    println("* Remove ${branch.name}? y/n ")
    val answer = readLine()
    if (answer == "y") {
        grgit.branch.remove(mapOf("names" to listOf(branch.name), "force" to true))
    }
}
