package com.taffered.analyzer.darkDB;

class BrushType {

	public static final int BRUSH_TYPE_SOLID = 0;
	public static final int BRUSH_TYPE_AIR = 1;
	public static final int BRUSH_TYPE_WATER = 2;
	public static final int BRUSH_TYPE_AIR2WATER = 3;
	public static final int BRUSH_TYPE_FLOOD = BRUSH_TYPE_AIR2WATER;
	public static final int BRUSH_TYPE_WATER2AIR = 4;
	public static final int BRUSH_TYPE_EVAPORATE = BRUSH_TYPE_WATER2AIR;
	public static final int BRUSH_TYPE_SOLID2WATER = 5;
	public static final int BRUSH_TYPE_SOLID2AIR = 6;
	public static final int BRUSH_TYPE_AIR2SOLID = 7;
	public static final int BRUSH_TYPE_WATER2SOLID = 8;
	public static final int BRUSH_TYPE_BLOCKABLE = 9;
	public static final int BRUSH_TYPE_ROOM = -5;
	public static final int BRUSH_TYPE_FLOW = -4;
	public static final int BRUSH_TYPE_OBJECT = -3;
	public static final int BRUSH_TYPE_AREA = -2;
	public static final int BRUSH_TYPE_LIGHT = -1;
}
