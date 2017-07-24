package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.Ubyte8;
import com.taffered.utils.Ushort16;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

/**
 * Created by Benjamin on 12/07/2017.
 */
class DarkDBTXLISTTexture implements Treeable {
    private Ubyte8 mOne;
    private Ubyte8 mFam;
    private Ushort16 mZero;
    private String mName;
    private String mFamName;


    public DarkDBTXLISTTexture(DarkInputStream in) {

        try {
            mOne = in.readUint8();
            mFam = in.readUint8();
            mZero = in.readUint16();
            mName = in.readStaticString(16);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setFamName(List<DarkDBTXLISTFam> families)
    {
        try {
            mFamName = families.get(mFam.getValue()).toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mFamName = "Uknown...";
        }
    }

    @Override
    public DefaultMutableTreeNode getTreeNode() {
        DefaultMutableTreeNode tn = new DefaultMutableTreeNode(mName);

        tn.add(new DefaultMutableTreeNode("One : " + mOne.toString()));
        tn.add(new DefaultMutableTreeNode("Fam : " + mFamName + " - " + mFam.toString()));
        tn.add(new DefaultMutableTreeNode("Zero : " + mZero.toString()));

        return tn;
    }
}
