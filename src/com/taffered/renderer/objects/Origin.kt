package com.taffered.renderer.objects

import javax.media.j3d.LineArray
import javax.media.j3d.Shape3D
import javax.vecmath.Color3f
import javax.vecmath.Point3f


class Origin : Shape3D() {

    init {
        mkGeometry()
    }

    private fun mkGeometry()
    {
        val origin = Point3f(0f, 0f, 0f)

        val x = Point3f(1f, 0f, 0f)
        val y = Point3f(0f, 1f, 0f)
        val z = Point3f(0f, 0f, 1f)

        val red = Color3f(1.0f, 0.0f, 0.0f)
        val green = Color3f(0.0f, 1.0f, 0.0f)
        val blue = Color3f(0.0f, 0.0f, 1.0f)


        val axisX = LineArray(2, LineArray.COORDINATES or LineArray.COLOR_3)
        axisX.setCoordinate(0, origin)
        axisX.setCoordinate(1, x)
        axisX.setColor(0, red)
        axisX.setColor(1, red)

        val axisY = LineArray(2, LineArray.COORDINATES or LineArray.COLOR_3)
        axisY.setCoordinate(0, origin)
        axisY.setCoordinate(1, y)
        axisY.setColor(0, green)
        axisY.setColor(1, green)

        val axisZ = LineArray(2, LineArray.COORDINATES or LineArray.COLOR_3)
        axisZ.setCoordinate(0, origin)
        axisZ.setCoordinate(1, z)
        axisZ.setColor(0, blue)
        axisZ.setColor(1, blue)

        this.addGeometry(axisX)
        this.addGeometry(axisY)
        this.addGeometry(axisZ)
    }

}