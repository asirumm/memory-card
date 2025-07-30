package com.diagantika.Transition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HorizontalTransition implements ScreenTransition {
    private TextureRegion textureRegion;
    private Viewport viewport;

    @Override
    public void render(SpriteBatch batch, float progress) {
        if (textureRegion==null||viewport==null)return;

        float screenWidth  =  viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float slideX = -screenWidth + (screenWidth * progress);
        batch.draw(textureRegion, slideX, 0, screenWidth, screenHeight);
        batch.end();
    }

    @Override
    public void setTextureTransition(TextureRegion textureRegion) {
        this.textureRegion  = textureRegion;
    }

    @Override
    public void setViewport(Viewport viewport) {

        this.viewport = viewport;
    }


}
