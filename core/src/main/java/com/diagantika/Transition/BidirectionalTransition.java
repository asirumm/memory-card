package com.diagantika.Transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BidirectionalTransition implements ScreenTransition {
    private Texture texture;
    private Viewport viewport;

    @Override
    public void setTextureTransition(Texture textureRegion) {
        // For compatibility with interface
        this.texture   = textureRegion;
    }

    @Override
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public void render(SpriteBatch batch, float progress) {
        if (viewport == null || texture == null) return;

        float screenWidth = viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        // Gerak dari kiri: -screenWidth ke 0
        float slideXLeft = -screenWidth + (screenWidth * progress);

        // Gerak dari kanan: screenWidth ke 0
        float slideXRight = screenWidth - (screenWidth * progress);

        // Gambar tekstur dari kiri
        batch.draw(texture, slideXLeft, 0, screenWidth, screenHeight);

        // Gambar tekstur dari kanan
        batch.draw(texture, slideXRight, 0, screenWidth, screenHeight);

        batch.end();
    }

}
