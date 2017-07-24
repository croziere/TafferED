package com.taffered.renderer

import javax.media.j3d.BranchGroup

interface Renderable {

    fun render(): BranchGroup

}
