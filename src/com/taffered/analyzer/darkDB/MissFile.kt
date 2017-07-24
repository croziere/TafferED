package com.taffered.analyzer.darkDB

import com.taffered.renderer.Renderable
import com.taffered.renderer.objects.Origin
import com.taffered.utils.DarkInputStream
import com.taffered.utils.UInt
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import javax.media.j3d.BranchGroup
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

/**
 * MissFile can represents any DarkDBFile
 */
class MissFile @Throws(FileNotFoundException::class, InvalidDarkDBFileException::class) constructor(private val missFile: File) : Renderable, DarkDBFile {

    private var header: DarkDBHeader? = null

    private var `in`: DarkInputStream? = null

    private val DarkDBInventory: MutableMap<String, DarkDBInvItem>

    private val DarkDBChunkList: MutableMap<String, DarkDBChunk>

    init {
        DarkDBInventory = HashMap<String, DarkDBInvItem>()
        DarkDBChunkList = TreeMap<String, DarkDBChunk>()
        readDB()
    }

    @Throws(FileNotFoundException::class, InvalidDarkDBFileException::class)
    constructor(path: String) : this(File(path))

    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#getBRLIST()
	 */
    override val brlist: DarkDBChunk?
        get() = DarkDBChunkList["BRLIST"]

    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#getChunk(java.lang.String)
	 */
    override fun getChunk(key: String): DarkDBChunk? {
        return DarkDBChunkList[key]
    }

    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#getChunkNameSet()
	 */
    override val chunkNameSet: Set<String>
        get() = DarkDBChunkList.keys


    @Throws(FileNotFoundException::class, InvalidDarkDBFileException::class)
    private fun readDB() {

        loadBin()

        header = DarkDBHeader(`in`) //Read the header first

        println(header)

        try {
            `in`!!.seek(header!!.invOffset.value)

            val n = `in`!!.readUint32()
            var nb = 0
            var nbn = 0
            println("Taille de l'inventaire : " + n)

            for (i in 0..n.value - 1) {
                val nameBuilder = StringBuilder()
                for (j in 0..11) {
                    nameBuilder.append(`in`!!.readByte().toChar())
                }
                val name = nameBuilder.toString()

                val offset = UInt(`in`!!.readLittleInt())
                val length = UInt(`in`!!.readLittleInt())

                if (length.value > 0) {
                    DarkDBInventory.put(name.trim { it <= ' ' }, DarkDBInvItem(name.trim { it <= ' ' }, offset, length))
                    nb++
                } else
                    nbn++
            }

            println("-- Objets non nuls : " + nb + " (" + nb.toFloat() / n.value.toInt() * 100 + "%)")
            println("-- Objets nuls : " + nbn + " (" + nbn.toFloat() / n.value.toInt() * 100 + "%)")

            println("Lecture des chunks")

            val inv_set = DarkDBInventory.entries

            for ((key, value) in inv_set) {

                DarkDBChunkList.put(key, DarkDBChunk.dispatchChunk(value, `in`!!))
            }


        } catch (e1: IOException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
        }


        try {
            `in`!!.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


    }

    @Throws(FileNotFoundException::class)
    private fun loadBin() {

        val `in` = DarkInputStream(missFile, "r")
        this.`in` = `in`

    }

    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#getTreeModel()
	 */
    override val treeModel: DefaultTreeModel
        get() {
            val tn = DefaultMutableTreeNode(missFile.name)

            tn.add(header!!.treeNode)
            val inv = DefaultMutableTreeNode("DBInventory")
            val set = DarkDBInventory.entries

            for ((_, value) in set) {
                inv.add(value.treeNode)
            }

            tn.add(inv)

            val chk_lst = DefaultMutableTreeNode("Chunks")
            val inv_set = DarkDBChunkList.entries
            for ((_, value) in inv_set) {
                chk_lst.add(value.treeNode)
            }

            tn.add(chk_lst)

            return DefaultTreeModel(tn)
        }


    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#getMasterChunksTreeModel()
	 */
    override val masterChunksTreeModel: DefaultTreeModel
        get() {
            val tn = DefaultMutableTreeNode(missFile.name)


            val inv_set = DarkDBChunkList.entries
            for ((_, value) in inv_set) {
                if (!value.name.contains("$") && !value.name.contains("AI_")) {
                    tn.add(value.treeNode)
                }
            }

            return DefaultTreeModel(tn)
        }


    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#getLinksChunksTreeModel()
	 */
    override val linksChunksTreeModel: DefaultTreeModel
        get() {
            val tn = DefaultMutableTreeNode(missFile.name)


            val inv_set = DarkDBChunkList.entries
            for ((_, value) in inv_set) {
                if (value.name.contains("L$")) {
                    tn.add(value.treeNode)
                }
            }

            return DefaultTreeModel(tn)
        }


    override val propertiesChunksTreeModel: DefaultTreeModel
        get() {
            val tn = DefaultMutableTreeNode(missFile.name)


            val inv_set = DarkDBChunkList.entries
            for ((_, value) in inv_set) {
                if (value.name.contains("P$")) {
                    tn.add(value.treeNode)
                }
            }

            return DefaultTreeModel(tn)
        }

    override val ldChunksTreeModel: DefaultTreeModel
        get() {
            val tn = DefaultMutableTreeNode(missFile.name)


            val inv_set = DarkDBChunkList.entries
            for ((_, value) in inv_set) {
                if (value.name.contains("LD$")) {
                    tn.add(value.treeNode)
                }
            }

            return DefaultTreeModel(tn)
        }

    override val aiChunksTreeModel: DefaultTreeModel
        get() {
            val tn = DefaultMutableTreeNode(missFile.name)


            val inv_set = DarkDBChunkList.entries
            for ((_, value) in inv_set) {
                if (value.name.startsWith("AI_")) {
                    tn.add(value.treeNode)
                }
            }

            return DefaultTreeModel(tn)
        }

    /* (non-Javadoc)
	 * @see com.taffered.analyzer.darkDB.DarkDBFile#render()
	 */
    override fun render(): BranchGroup {
        val scene = BranchGroup()

        scene.addChild(Origin())

        scene.addChild(DarkDBChunkList["BRLIST"]?.render())
        return scene
    }

}
