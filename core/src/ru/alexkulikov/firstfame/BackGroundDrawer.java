package ru.alexkulikov.firstfame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static ru.alexkulikov.firstfame.objects.Constants.*;

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
        leftTexture.setBounds(0, 0, VIEWPORT_WIDTH, (float) VIEWPORT_HEIGHT/0.75f);
        centralTexture.setBounds(VIEWPORT_WIDTH, 0, VIEWPORT_WIDTH, (float) VIEWPORT_HEIGHT/0.75f);
        rightTexture.setBounds(2 * VIEWPORT_WIDTH, 0, VIEWPORT_WIDTH, (float) VIEWPORT_HEIGHT/0.75f);
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
