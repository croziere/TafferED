package com.taffered.renderer.objects

import com.taffered.analyzer.darkDB.Brush

import javax.media.j3d.Appearance
import javax.media.j3d.Geometry
import javax.media.j3d.Shape3D


abstract class DarkObject(protected var mBrush: Brush) : Shape3D() {

    protected var mGeometry: Geometry?
    protected var mAppearance: Appearance?

    init {
        mGeometry = createGeometry()
        mAppearance = createAppearance()

        this.geometry = mGeometry
        this.appearance = mAppearance
    }

    protected abstract fun createGeometry(): Geometry?

    protected abstract fun createAppearance(): Appearance?
}
