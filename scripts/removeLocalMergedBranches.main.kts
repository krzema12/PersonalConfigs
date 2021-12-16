#!/usr/bin/env kotlin

@file:DependsOn("org.eclipse.jgit:org.eclipse.jgit:4.6.0.201612231935-r")

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ListBranchCommand
import java.io.File
import kotlin.system.exitProcess

fun red(text: String) = "\u001B[31m$text\u001B[0m"
fun green(text: String) = "\u001B[32m$text\u001B[0m"
fun blue(text: String) = "\u001B[34m$text\u001B[0m"

val git = Git.open(File("."))

val remote = "origin"

val localBranches = git.branchList().call()
    .map { it.name.replaceFirst("refs/heads/", "") }
println("Local branches:")
localBranches.forEach { branch ->
    println("  ${green(branch)}")
}

val remoteBranches = git.branchList()
    .setListMode(ListBranchCommand.ListMode.REMOTE)
    .call()
    .map { it.name.replaceFirst("refs/remotes/origin/", "") }
println("Remote branches:")
remoteBranches.forEach { branch ->
    println("  ${red(branch)}")
}

val branchesToRemove = localBranches.filter { branch ->
    remoteBranches.firstOrNull { it == branch } == null
}

println()

if (branchesToRemove.isEmpty()) {
    println("No branches to remove, finishing.")
    exitProcess(0)
}

println("Candidates to remove:")
branchesToRemove.forEach { branch ->
    println("  ${blue(branch)}")
}

println("Asking branch by branch:")

branchesToRemove.forEach { branch ->
    println("* Remove ${branch}? y/n ")
    val answer = readLine()
    if (answer == "y") {
        git.branchDelete()
            .setBranchNames(branch)
            .setForce(true)
            .call()
    }
}
