package ru.alexkulikov.firstfame.background;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class MountainDrawer {
    private Mountain leftMountain;
    private Mountain centralMountain;
    private Mountain rightMountain;

    private float mainOffset = 0;

    public MountainDrawer(Stage stage) {
        leftMountain = new Mountain();
        centralMountain = new Mountain();
        rightMountain = new Mountain();

        stage.addActor(leftMountain);
        stage.addActor(centralMountain);
        stage.addActor(rightMountain);
    }

    public void initialize() {
        leftMountain.setSpriteX(0);
        centralMountain.setSpriteX(leftMountain.getSpriteOffset());
        rightMountain.setSpriteX(centralMountain.getSpriteOffset());
        mainOffset = 0;
    }

    public void update(float camX, float zoom) {
        leftMountain.update(camX/2 + mainOffset, zoom);
        centralMountain.update(leftMountain.getSpriteOffset(), zoom);
        rightMountain.update(centralMountain.getSpriteOffset(), zoom);

        if (camX > leftMountain.getSpriteOffset()) {
            change();
        }
    }

    public void change() {
        Mountain buffer = leftMountain;
        mainOffset += leftMountain.getSpriteWidth();

        leftMountain = centralMountain;
        centralMountain = rightMountain;
        rightMountain = buffer;

        rightMountain.setSpriteX(centralMountain.getSpriteOffset());
    }
}
