package ru.alexkulikov.firstfame.background;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class GrassDrawer {
    private Grass leftGrass;
    private Grass centralGrass;
    private Grass rightGrass;

    public GrassDrawer(Stage stage) {
        leftGrass = new Grass();
        centralGrass = new Grass();
        rightGrass = new Grass();

        stage.addActor(leftGrass);
        stage.addActor(centralGrass);
        stage.addActor(rightGrass);
    }

    public void initialize() {
        leftGrass.setSpriteX(0);
        centralGrass.setSpriteX(leftGrass.getSpriteOffset());
        rightGrass.setSpriteX(centralGrass.getSpriteOffset());
    }

    public void update(float camX) {
        if (camX - 3 > leftGrass.getSpriteOffset()) {
            change();
        }
    }

    public void change() {
        Grass buffer = leftGrass;

        leftGrass = centralGrass;
        centralGrass = rightGrass;
        rightGrass = buffer;

        rightGrass.setSpriteX(centralGrass.getSpriteOffset());
    }
}
