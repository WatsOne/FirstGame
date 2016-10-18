package ru.alexkulikov.firstfame;

public enum Material {
    ice(0.92f, 0.01f, 0.01f),
    stone(2.5f, 0.2f, 0.5f),
    wood(0.65f, 0.4f, 0.4f);

    private float density;
    private float restitution;
    private float friction;

    Material(float density, float restitution, float friction) {
        this.density = density;
        this.restitution = restitution;
        this.friction = friction;
    }

    public float getDensity() {
        return density;
    }

    public float getRestitution() {
        return restitution;
    }

    public float getFriction() {
        return friction;
    }
}
