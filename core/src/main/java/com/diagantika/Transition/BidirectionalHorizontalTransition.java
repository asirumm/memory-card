package com.diagantika.Transition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Creating transtion horizontal from left and right position
 * ]----[
 * ]--[
 * ][
 */
public class BidirectionalHorizontalTransition implements ScreenTransition{
    private TextureRegion texture;
    private Viewport viewport;

    @Override
    public void setTextureTransition(TextureRegion textureRegion) {
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

        float slideXLeft = -screenWidth + (screenWidth * progress);

        float slideXRight = screenWidth - (screenWidth * progress);

        // Gambar tekstur dari kiri
        // draw left
        batch.draw(texture, slideXLeft, 0, screenWidth, screenHeight);

        // Gambar tekstur dari kanan
        // draw right
        batch.draw(texture, slideXRight, 0, screenWidth, screenHeight);

    }
}
