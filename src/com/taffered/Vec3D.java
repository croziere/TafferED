package com.taffered;

import com.taffered.analyzer.darkDB.Treeable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Vector3d;

public class Vec3D implements Treeable {

	private double x;
	private double y;
	private double z;

    /**
     * Dromed has a different origin
     * Dromed(x, y ,z) = J3D(z, x, y)
     */
	public static final int  VEC_DROMED = 1;
	public static final int VEC_J3D = 0;
	
	public Vec3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3D(double x, double y, double z, int type)
	{
		if (type == VEC_DROMED)
		{
			this.x = y;
			this.y = z;
			this.z = x;
		}
		else
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}

	public Vector3d getVector()
	{
		return new Vector3d(x, y, z);
	}
	
	@Override
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
