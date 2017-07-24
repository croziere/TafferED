package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;
import com.taffered.utils.DarkTimeUtils;
import com.taffered.utils.UInt;

import javax.media.j3d.BranchGroup;
import javax.swing.tree.DefaultMutableTreeNode;


public class DarkDBChunkBRHEAD extends DarkDBChunk {

    private String mCreator;
    private String mName;
    private UInt mUknown1;
    private UInt mGridSize;
    private Boolean mShowGrid;
    private UInt mShading;
    private UInt mUknown5;
    private Boolean mUseGrid;
    private UInt mTime;

    public DarkDBChunkBRHEAD(DarkInputStream in, UInt offset, UInt len) {
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

        this.keyValueTree(tn, "Creator", mCreator);
        this.keyValueTree(tn, "Name", mName);
        this.keyValueTree(tn, "Uknown1", mUknown1.toHexFString());
        this.keyValueTree(tn, "Grid size", mGridSize.toString());
        this.keyValueTree(tn, "Show grid", mShowGrid.toString());
        this.keyValueTree(tn, "Shading", mShading.toHexFString() + " (" + getShadingState(mShading) + ")");
        this.keyValueTree(tn, "Uknown5", mUknown5.toHexFString());
        this.keyValueTree(tn, "Use grid", mUseGrid.toString());
        this.keyValueTree(tn, "Time", mTime.getValue() + " - " + DarkTimeUtils.INSTANCE.MilisToDuration(mTime.getValue()));

        return tn;
    }

    protected String getShadingState(UInt shading)
    {
        if (shading.getValue() == 2)
        {
            return "Enable";
        }
        else
        {
            return "Disable";
        }
    }

    @Override
    protected void readChunk() {
        try {

            mCreator = getIn().readStaticString(16);

            mName = getIn().readStaticString(16);

            mUknown1 = getIn().readUint32();

            getIn().skipBytes(64);

            mGridSize = getIn().readUint32();

            mShowGrid = getIn().readBoolean();

            mShading = getIn().readUint32();

            mUknown5 = getIn().readUint32();

            mUseGrid = getIn().readBoolean();

            mTime = getIn().readUint32();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
