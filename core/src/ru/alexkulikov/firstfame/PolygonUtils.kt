package ru.alexkulikov.firstfame

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2

class PolygonUtils {

    private val center = Vector2()
    private val vec1 = Vector2()
    private val vec2 = Vector2()

    fun overlaps(polygon: Polygon, circle: Circle): Boolean {
        val vertices = polygon.transformedVertices
        center.set(circle.x, circle.y)
        val squareRadius = circle.radius * circle.radius
        var i = 0
        while (i < vertices.size) {
            if (i == 0) {
                if (Intersector.intersectSegmentCircle(vec1.set(vertices[vertices.size - 2], vertices[vertices.size - 1]),
                        vec2.set(vertices[i], vertices[i + 1]), center, squareRadius))
                    return true
            } else {
                if (Intersector.intersectSegmentCircle(vec1.set(vertices[i - 2], vertices[i - 1]), vec2.set(vertices[i], vertices[i + 1]), center, squareRadius))
                    return true
            }
            i += 2
        }
        return polygon.contains(circle.x, circle.y)
    }
}