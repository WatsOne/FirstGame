package ru.alexkulikov.firstfame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class BackGroundDrawer {

    private Image leftTexture;
    private Image centralTexture;
    private Image rightTexture;

    public BackGroundDrawer() {
        leftTexture = new Image(TextureLoader.getBackground());
        centralTexture = new Image(TextureLoader.getBackground());
        rightTexture = new Image(TextureLoader.getBackground());
    }

    public void drawBackGround(Stage stage) {
        leftTexture.setBounds(0, 0.5f, 12, (float) 12/0.75f);
        centralTexture.setBounds(12, 0.5f, 12, (float) 12/0.75f);
        rightTexture.setBounds(24, 0.5f, 12, (float) 12/0.75f);
        stage.addActor(leftTexture);
        stage.addActor(centralTexture);
        stage.addActor(rightTexture);
    }

    public void updateBackGround(float playerX) {
        if (playerX - 5 > leftTexture.getX() + leftTexture.getWidth()) {
            Image buffer = leftTexture;
            leftTexture = centralTexture;
            centralTexture = rightTexture;
            rightTexture = buffer;

            rightTexture.setX(centralTexture.getX() + rightTexture.getWidth());
        }
    }
}
