package com.taffered.analyzer.darkDB;

import com.taffered.utils.DarkInputStream;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Benjamin on 12/07/2017.
 */
class DarkDBTXLISTFam implements Treeable {
    private String mName;

    public DarkDBTXLISTFam(DarkInputStream in) {
        try {
           mName = in.readStaticString(16);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public DefaultMutableTreeNode getTreeNode() {
        return new DefaultMutableTreeNode(mName);
    }

    @Override
    public String toString() {
        return mName;
    }
}
