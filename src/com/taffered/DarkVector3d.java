package com.taffered;

import com.taffered.analyzer.darkDB.Treeable;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Vector3d;

public class DarkVector3d extends Vector3d implements Treeable {

    /**
     * Dromed has a different origin
     * Dromed(x, y ,z) = J3D(z, x, y)
	 * when reading Dromed data
     */
	public static final int  VEC_DROMED = 1;
	public static final int VEC_J3D = 0;

	/**
	 * Create a Vector3d from Dromed Vector
	 * WARNING : Always use it with data coming from DromED
	 * @param x Dromed x coordinate (z in Java3D)
	 * @param y Dromed y coordinate (x in Java3D)
	 * @param z Dromed z coordinate (y in Java3D)
	 */
	public DarkVector3d(double x, double y, double z)
	{
		super(y, z, x);
	}
	
	@Override
	@NotNull
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode();
		
		tn.add(new DefaultMutableTreeNode("x : " + x));
		tn.add(new DefaultMutableTreeNode("y : " + y));
		tn.add(new DefaultMutableTreeNode("z : " + z));
		
		
		return tn;
	}	
	
	public DefaultMutableTreeNode getNamedTreeNode(String name) {
DefaultMutableTreeNode tn = new DefaultMutableTreeNode(name);
		
		tn.add(new DefaultMutableTreeNode("x : " + x));
		tn.add(new DefaultMutableTreeNode("y : " + y));
		tn.add(new DefaultMutableTreeNode("z : " + z));
		
		
		return tn;
	}
}
