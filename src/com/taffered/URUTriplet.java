package com.taffered;

import com.taffered.analyzer.darkDB.Treeable;
import com.taffered.utils.Ushort16;

import javax.swing.tree.DefaultMutableTreeNode;

public class URUTriplet implements Treeable {

	private static final double URUToDegree = 360./65536.;
	private static final double URUToRadian = Math.PI/32768.;

	public static final int URU_DROMED = 1;

	private Ushort16 x;
	private Ushort16 y;
	private Ushort16 z;

	public URUTriplet(Ushort16 x, Ushort16 y, Ushort16 z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public URUTriplet(Ushort16 x, Ushort16 y, Ushort16 z, int type)
	{
		if (type == URU_DROMED)
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
	public Ushort16 getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(Ushort16 x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public Ushort16 getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(Ushort16 y) {
		this.y = y;
	}
	/**
	 * @return the z
	 */
	public Ushort16 getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(Ushort16 z) {
		this.z = z;
	}

	private double getDegX(){
		return x.getValue()*URUToDegree;
	}

	private double getDegY(){
		return y.getValue()*URUToDegree;
	}

	private double getDegZ(){
		return z.getValue()*URUToDegree;
	}

	public double getRadX(){
		return x.getValue()*URUToRadian;
	}

	public double getRadY(){
		return y.getValue()*URUToRadian;
	}

	public double getRadZ(){
		return z.getValue()*URUToRadian;
	}

	@Override
	public DefaultMutableTreeNode getTreeNode() {
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode();

		tn.add(new DefaultMutableTreeNode("x : " + x + " URU - " + getDegX()));
		tn.add(new DefaultMutableTreeNode("y : " + y + " URU - " + getDegY()));
		tn.add(new DefaultMutableTreeNode("z : " + z + " URU - " + getDegZ()));


		return tn;
	}

	public DefaultMutableTreeNode getNamedTreeNode(String name) {
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode(name);

		tn.add(new DefaultMutableTreeNode("x : " + x + " URU - " + getDegX()));
		tn.add(new DefaultMutableTreeNode("y : " + y + " URU - " + getDegY()));
		tn.add(new DefaultMutableTreeNode("z : " + z + " URU - " + getDegZ()));


		return tn;
	}



}
