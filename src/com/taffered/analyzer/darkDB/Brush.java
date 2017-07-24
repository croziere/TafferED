package com.taffered.analyzer.darkDB;

import com.taffered.URUTriplet;
import com.taffered.Vec3D;
import com.taffered.renderer.Renderable;
import com.taffered.utils.DarkInputStream;
import com.taffered.utils.Ubyte8;
import com.taffered.utils.Uint32;
import com.taffered.utils.Ushort16;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Color3f;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brush implements Treeable, Renderable {

	private Ushort16 id;
	private Ushort16 time;
	private int primal;
	private short base;
	private byte type;
	private Ubyte8 zero1;

	private Vec3D position;
	private URUTriplet rotation;
	private Vec3D size;

	private short cur_faces;
	private double snap_size;
	private Ubyte8 uknown3[];
	private Ubyte8 snap_grid;
	private Ubyte8 num_faces;
	private Ubyte8 edge;
	private Ubyte8 vertex;
	private Ubyte8 flags;
	private Ubyte8 group;
	private Uint32 uknown5;

	private long offset;

	private List<DarkDBBrushFace> faces;

	private DarkInputStream in;

		public Brush(DarkInputStream in) {
			this.in = in;
			uknown3 = new Ubyte8[18];
			faces = new ArrayList<>();
			try {
				readBrush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public Ushort16 getId() {
			return id;
		}

		private void readBrush() throws IOException {
			offset = in.getFilePointer();

			id = in.readUint16();
			time =in.readUint16();
			primal = in.readLittleInt();
			base = in.readLittleShort();
			type = in.readByte();
			in.skipBytes(1);


			position = new Vec3D(in.readLittleFloat(), in.readLittleFloat(), in.readLittleFloat(), Vec3D.VEC_DROMED);
			size = new Vec3D(in.readLittleFloat(), in.readLittleFloat(), in.readLittleFloat(), Vec3D.VEC_DROMED);
			rotation = new URUTriplet(in.readUint16(), in.readUint16(), in.readUint16(), URUTriplet.URU_DROMED);

			cur_faces = in.readLittleShort();
			snap_size = in.readLittleFloat();

			for(int i = 0; i < uknown3.length; i++) {
				uknown3[i] = in.readUint8();
			}

			snap_grid = in.readUint8();
			num_faces = in.readUint8();

			edge = in.readUint8();
			vertex = in.readUint8();
			flags = in.readUint8();
			group = in.readUint8();
			uknown5 = in.readUint32();

			if(type >= 0) {
				for(int i = 0; i < num_faces.getValue(); i++) {
					faces.add(new DarkDBBrushFace(in));
				}
			}
		}

		public int getDataSize() {
			int r = 0;
			r += 76; //Static size

			if(type >= 0) {
				r += 10 * num_faces.getValue();
			}

			return r;
		}

		@Override
		public String toString() {
			return "Brush [id=" + id + ",\n\t time=" + time + ",\n\t primal=" + primal
					+ ",\n\t base=" + base + ",\n\t type=" + type + ",\n\t zero1=" + zero1
					+ ",\n\t position=" + position + ",\n\t rotation=" + rotation
					+ ",\n\t size=" + size + ",\n\t cur_faces=" + cur_faces
					+ ",\n\t snap_size=" + snap_size + ",\n\t uknown3="
					+ Arrays.toString(uknown3) + ",\n\t snap_grid=" + snap_grid
					+ ",\n\t num_faces=" + num_faces + ",\n\t edge=" + edge
					+ ",\n\t vertex=" + vertex + ",\n\t flags=" + flags + ",\n\t group="
					+ group + ",\n\t uknown5=" + uknown5 + ",\n\t faces=" + faces
					+ ",\n\t in=" + in + "]";
		}

		@Override
		public DefaultMutableTreeNode getTreeNode() {
			if(uknown5.getValue() != 0)
				System.out.println("!!!!! BLOCK #" + id + " has uknown value " + uknown5);
			DefaultMutableTreeNode tn = new DefaultMutableTreeNode("#" + id + " - 0x" + Long.toHexString(offset).toUpperCase() + " - " + Brush.BrushTypeToName(type));
			tn.add(new DefaultMutableTreeNode("Time : " + time.getValue()));
			tn.add(new DefaultMutableTreeNode("Primal : " + primal));
			tn.add(new DefaultMutableTreeNode("Base : " + base));
			tn.add(new DefaultMutableTreeNode("Type : " + type + " (" + Brush.BrushTypeToName(type) + ")"));
			tn.add(position.getNamedTreeNode("Position"));
			tn.add(rotation.getNamedTreeNode("Rotation"));
			tn.add(size.getNamedTreeNode("Size"));
			tn.add(new DefaultMutableTreeNode("Current face : " + cur_faces));
			tn.add(new DefaultMutableTreeNode("Snap size : " + snap_size));
			tn.add(new DefaultMutableTreeNode("Uknown 3 : " + Arrays.toString(uknown3)));
			tn.add(new DefaultMutableTreeNode("Snap grid : " +  snap_grid.getValue()));
			tn.add(new DefaultMutableTreeNode("Nb faces : " + num_faces.getValue()));
			tn.add(new DefaultMutableTreeNode("Edge : " + edge.getValue()));
			tn.add(new DefaultMutableTreeNode("Vertex : " + vertex.getValue()));
			tn.add(new DefaultMutableTreeNode("Flags : " + flags.getValue()));
			tn.add(new DefaultMutableTreeNode("Group : " + group.getValue()));
			tn.add(new DefaultMutableTreeNode("Uknown5 : " + uknown5.getValue()));
			if(type >= 0) {
				DefaultMutableTreeNode fcLst = new DefaultMutableTreeNode("Faces");
				for(DarkDBBrushFace f : faces) {
					fcLst.add(f.getTreeNode());
				}
				tn.add(fcLst);
			}

			return tn;
		}

		private static String BrushTypeToName(int btype) {

			switch (btype) {
			case BrushType.BRUSH_TYPE_AIR:
					return "Air Brush";

			case BrushType.BRUSH_TYPE_AIR2SOLID:
				return "Air to Solid";

			case BrushType.BRUSH_TYPE_AIR2WATER:
				return "Air to Water";

			case BrushType.BRUSH_TYPE_AREA:
				return "Area";

			case BrushType.BRUSH_TYPE_BLOCKABLE:
				return "Blockable";

			case BrushType.BRUSH_TYPE_FLOW:
				return "Flow";

			case BrushType.BRUSH_TYPE_LIGHT:
				return "Light";

			case BrushType.BRUSH_TYPE_OBJECT:
				return "Object";

			case BrushType.BRUSH_TYPE_ROOM:
				return "Room";

			case BrushType.BRUSH_TYPE_SOLID:
				return "Solid";

			case BrushType.BRUSH_TYPE_SOLID2AIR:
				return "Solid to Air";

			case BrushType.BRUSH_TYPE_SOLID2WATER:
				return "Solid to Water";

			case BrushType.BRUSH_TYPE_WATER:
				return "Water";

			case BrushType.BRUSH_TYPE_WATER2AIR:
				return "Water to Air";

			case BrushType.BRUSH_TYPE_WATER2SOLID:
				return "Water to Solid";

			default:
				return "Uknown !";
			}
		}

		public ColoringAttributes getColoringAttributes(){

			float r = (float)id.getValue() * 0.1f;
			float g = (float)id.getValue() * 0.1f - 0.1f;
			float b = (float)id.getValue() * 0.1f + 0.1f;

			Color3f color = new Color3f(r, g, b);

			return new ColoringAttributes(color, ColoringAttributes.SHADE_FLAT);
		}

		@Override
		public BranchGroup render() {
			BranchGroup scene = new BranchGroup();

			if(type == BrushType.BRUSH_TYPE_SOLID && num_faces.getValue() == 6){

                //Rotate and translate !
			}

			return scene;
		}

	public Ushort16 getTime() {
		return time;
	}

	public int getPrimal() {
		return primal;
	}

	public short getBase() {
		return base;
	}

	public byte getType() {
		return type;
	}

	public Vec3D getPosition() {
		return position;
	}

	public URUTriplet getRotation() {
		return rotation;
	}

	public Vec3D getSize() {
		return size;
	}

	public short getCur_faces() {
		return cur_faces;
	}

	public double getSnap_size() {
		return snap_size;
	}

	public Ubyte8 getSnap_grid() {
		return snap_grid;
	}

	public Ubyte8 getNum_faces() {
		return num_faces;
	}

	public Ubyte8 getEdge() {
		return edge;
	}

	public Ubyte8 getVertex() {
		return vertex;
	}

	public Ubyte8 getFlags() {
		return flags;
	}

	public Ubyte8 getGroup() {
		return group;
	}

	public long getOffset() {
		return offset;
	}

	public List<DarkDBBrushFace> getFaces() {
		return faces;
	}
}