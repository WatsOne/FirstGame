package ru.alexkulikov.firstfame.screen.test

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor


class TestActor(polygonRegion: PolygonRegion) : Actor() {

    private var pSprite: PolygonSprite = PolygonSprite(polygonRegion)
//    private var pSprite = RepeatablePolygonSprite()

    init {
//        pSprite.setPolygon(r, floatArrayOf(1f, 0.5f,1f,1f,5f,1f,5f,0.5f))
//        pSprite.setPosition(1f,1f)
        pSprite.setBounds(1f,1f,4f,0.5f)
    }

//    init {
//        setBounds(1f, 1f, 4f, 0.5f)
////        pSprite.setBounds(1f, 1f,400f, 10f)
//        Gdx.app.log("f", polygonRegion.region.regionHeight.toString() + " " + polygonRegion.region.regionWidth.toShort())
//    }

    override fun draw(batch: Batch, parentAlpha: Float) {
//        (batch as PolygonSpriteBatch).draw(polygonRegion, x, y, width, height)
//        pSprite.draw(batch as PolygonSpriteBatch)
        pSprite.draw(batch as PolygonSpriteBatch)
    }
}