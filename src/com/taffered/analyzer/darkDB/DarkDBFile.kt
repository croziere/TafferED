package com.taffered.analyzer.darkDB

import javax.media.j3d.BranchGroup
import javax.swing.tree.DefaultTreeModel

interface DarkDBFile {

    val brlist: DarkDBChunk?

    fun getChunk(key: String): DarkDBChunk?

    val chunkNameSet: Set<String>

    val treeModel: DefaultTreeModel

    val masterChunksTreeModel: DefaultTreeModel

    val linksChunksTreeModel: DefaultTreeModel

    val propertiesChunksTreeModel: DefaultTreeModel

    val ldChunksTreeModel: DefaultTreeModel

    val aiChunksTreeModel: DefaultTreeModel

    fun render(): BranchGroup

}