package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.Uint32;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 12/07/2017.
 */
public class DarkDBTXLIST extends DarkDBChunk {
    private Uint32 mLength;
    private Uint32 mTxtCount;
    private Uint32 mFamCount;

    private List<DarkDBTXLISTFam> mFamilies;

    private List<DarkDBTXLISTTexture> mTextures;

    public DarkDBTXLIST(DarkInputStream in, Uint32 offset, Uint32 len) {
        super(in, offset, len);
        mFamilies = new ArrayList<>();
        mTextures = new ArrayList<>();
        readChunk();
    }

    @Override
    public DefaultMutableTreeNode getTreeNode() {
        DefaultMutableTreeNode tn = super.getTreeNode();

        this.keyValueTree(tn, "Length", mLength.toHexFString());
        this.keyValueTree(tn, "TxtCount", Long.toString(mTxtCount.getValue()));
        this.keyValueTree(tn, "FamCount", Long.toString(mFamCount.getValue()));

        DefaultMutableTreeNode famTn = new DefaultMutableTreeNode("Families");
        DefaultMutableTreeNode txtTn = new DefaultMutableTreeNode("Textures");

        for (DarkDBTXLISTFam fam:mFamilies) {
            famTn.add(fam.getTreeNode());
        }

        for (DarkDBTXLISTTexture text:mTextures) {
            txtTn.add(text.getTreeNode());
        }

        tn.add(famTn);
        tn.add(txtTn);


        return tn;
    }

    @Override
    public BranchGroup render() {
        return null;
    }

    @Override
    protected void readChunk() {
        try {
            mLength = getIn().readUint32();
            mTxtCount = getIn().readUint32();
            mFamCount = getIn().readUint32();

            for (int i = 0; i < mFamCount.getValue(); i++) {
                mFamilies.add(new DarkDBTXLISTFam(getIn()));
            }

            for (int i = 0; i < mTxtCount.getValue(); i++) {
                DarkDBTXLISTTexture text = new DarkDBTXLISTTexture(getIn());
                text.setFamName(mFamilies);
                mTextures.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
