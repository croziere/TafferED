package com.taffered.analyzer.darkDB

import com.taffered.renderer.Renderable
import com.taffered.utils.DarkInputStream
import com.taffered.utils.Uint32
import java.io.IOException
import javax.swing.tree.DefaultMutableTreeNode

abstract class DarkDBChunk(protected val `in`: DarkInputStream, protected var offset: Uint32, protected var length: Uint32) : Treeable, Renderable {

    lateinit var name: String

    lateinit var version_high: Uint32

    protected lateinit var version_low: Uint32

    init {
        readHeader()
    }

    override val treeNode: DefaultMutableTreeNode
        get() {
            val tn = DefaultMutableTreeNode(this.name)

            tn.add(DefaultMutableTreeNode("Version high : " + this.version_high))
            tn.add(DefaultMutableTreeNode("Version low : " + this.version_low))
            tn.add(DefaultMutableTreeNode("Taille : " + this.length.toHexFString()))
            tn.add(DefaultMutableTreeNode("Offset : " + this.offset.toHexFString()))

            return tn
        }

    private fun readHeader() {
        try {
            `in`.seek(offset.value)

            name = `in`.readStaticString(12)

            version_high = `in`.readUint32()
            version_low = `in`.readUint32()

            `in`.skipBytes(Uint32.BYTE_SIZE) //Skip 0xFF

            offset = Uint32((offset.value + DarkDBChunk.HEADER_SIZE).toInt()) //Set the offset to the beginin of chunk data

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    protected abstract fun readChunk()

    protected fun keyValueTree(node: DefaultMutableTreeNode, key: String, value: String) {
        node.add(DefaultMutableTreeNode(key + " : " + value))
    }

    companion object {

        val HEADER_SIZE = 24

        internal fun dispatchChunk(item: DarkDBInvItem, `in`: DarkInputStream): DarkDBChunk {

            if (item.name == "BRLIST") {
                println("BRLIST Found at " + item.offset.toHexFString())
                return DarkDBChunkBRLIST(`in`, item.offset, item.length)
            }

            if (item.name == "GAM_FILE") {
                println("GAM_FILE Found at " + item.offset.toHexFString())
                return DarkDBChunkGAMFILE(`in`, item.offset, item.length)
            }

            if (item.name == "SIM_TIME") {
                return DarkDBChunkSIMTIME(`in`, item.offset, item.length)
            }

            if (item.name == "QUEST_DB") {
                return DarkDBChunkQuestDB(`in`, item.offset, item.length)
            }

            if (item.name == "FILE_TYPE") {
                return DarkDBChunkFILETYPE(`in`, item.offset, item.length)
            }

            if (item.name == "BRHEAD") {
                return DarkDBChunkBRHEAD(`in`, item.offset, item.length)
            }

            if (item.name == "SKYMODE") {
                return DarkDBChunkSKYMODE(`in`, item.offset, item.length)
            }

            if (item.name == "TXLIST") {
                return DarkDBTXLIST(`in`, item.offset, item.length)
            }

            return DarkDBChunkGeneric(`in`, item.offset, item.length)
        }
    }

}
