package ru.alexkulikov.firstfame.levels

import ru.alexkulikov.firstfame.objects.player.Player

class ZoomManager {
    private val zoomSpeed = 60

    lateinit var zooms: List<Zoom>

    private var zoomTick = 0
    private var zoomStep = 0.0f
    private var inZoom = false
    var zoomValue = 1.0f

    fun updateZoom(player: Player) {
        if (zoomTick > 0) {
            zoomValue += zoomStep
            zoomTick--
            return
        }

        zooms.forEach {
            if (inZoom(player.x, player.y, it.x, it.y, it.h, it.w)) {
                if (inZoom) {
                    return
                }
                zoomStep = (it.scale - zoomValue) / zoomSpeed
                zoomTick = zoomSpeed
                inZoom = true
                return
            }
        }

        if (inZoom) {
            zoomStep = (1.0f - zoomValue) / zoomSpeed
            zoomTick = zoomSpeed
            inZoom = false
        }
    }

    private fun inZoom(xPlayer: Float, yPlayer: Float, x:Float, y:Float, h: Float, w: Float): Boolean {
        return xPlayer in (x .. (x + w)) && yPlayer in (y .. (y + h))
    }

    fun restart() {
        inZoom = false
        zoomValue = 1.0f
        zoomTick = 0
    }
}