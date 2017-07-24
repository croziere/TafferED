package com.taffered.renderer.objects

import com.taffered.analyzer.darkDB.Brush
import javax.media.j3d.Appearance
import javax.media.j3d.Geometry
import javax.media.j3d.PolygonAttributes
import javax.media.j3d.QuadArray
import javax.vecmath.Point3d


class DarkSolid(mBrush: Brush) : DarkObject(mBrush) {

    override fun createGeometry(): Geometry? {
        val geometry = QuadArray(24, QuadArray.COORDINATES)

        //Front
        geometry.setCoordinate(0, Point3d(mBrush.size.x, mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(1, Point3d(mBrush.size.x, -mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(2, Point3d(-mBrush.size.x, mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(3, Point3d(-mBrush.size.x, -mBrush.size.y, mBrush.size.z))

        // Front
        geometry.setCoordinate(4, Point3d(-mBrush.size.x, mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(5, Point3d(-mBrush.size.x, mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(6, Point3d(-mBrush.size.x, -mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(7, Point3d(-mBrush.size.x, -mBrush.size.y, -mBrush.size.z))


        //Left
        geometry.setCoordinate(8, Point3d(mBrush.size.x, -mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(9, Point3d(mBrush.size.x, -mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(10, Point3d(-mBrush.size.x, -mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(11, Point3d(-mBrush.size.x, -mBrush.size.y, -mBrush.size.z))

        //Back
        geometry.setCoordinate(12, Point3d(mBrush.size.x, mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(13, Point3d(mBrush.size.x, mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(14, Point3d(mBrush.size.x, -mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(15, Point3d(mBrush.size.x, -mBrush.size.y, -mBrush.size.z))

        //Right
        geometry.setCoordinate(16, Point3d(mBrush.size.x, mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(17, Point3d(mBrush.size.x, mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(18, Point3d(-mBrush.size.x, mBrush.size.y, mBrush.size.z))
        geometry.setCoordinate(19, Point3d(-mBrush.size.x, mBrush.size.y, -mBrush.size.z))

        //Bottom
        geometry.setCoordinate(20, Point3d(mBrush.size.x, mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(21, Point3d(mBrush.size.x, -mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(22, Point3d(-mBrush.size.x, mBrush.size.y, -mBrush.size.z))
        geometry.setCoordinate(23, Point3d(-mBrush.size.x, -mBrush.size.y, -mBrush.size.z))

        return geometry
    }

    override fun createAppearance(): Appearance? {
        val ap = Appearance()
        ap.coloringAttributes = mBrush.coloringAttributes
        ap.polygonAttributes = PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0f)

        return ap
    }
}
