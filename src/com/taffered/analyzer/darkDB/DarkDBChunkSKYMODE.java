package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.Uint32;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Benjamin on 12/07/2017.
 */
public class DarkDBChunkSKYMODE extends DarkDBChunk {

    private static final int SKYMODE_TEXTURE = 0;
    private static final int SKYMODE_STARS = 1;

    private Uint32 mMode;


    public DarkDBChunkSKYMODE(DarkInputStream in, Uint32 offset, Uint32 len) {
        super(in, offset, len);
        readChunk();
    }

    @Override
    public BranchGroup render() {
        return null;
    }

    @Override
    public DefaultMutableTreeNode getTreeNode() {
        DefaultMutableTreeNode tn = super.getTreeNode();

        this.keyValueTree(tn, "Mode", getSkymode(mMode));

        return tn;
    }

    private String getSkymode(Uint32 mMode) {
        if (mMode.getValue() == SKYMODE_STARS)
        {
            return "Stars";
        }

        if (mMode.getValue() == SKYMODE_TEXTURE)
        {
            return "Texture";
        }

        return "Uknown mode";
    }

    @Override
    protected void readChunk() {
        try {
            mMode = getIn().readUint32();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
