package com.diagantika.Transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.tinylog.Logger;

public class HorizontalTransition implements ScreenTransition {
    private Texture textureRegion;
    private Viewport viewport;

    @Override
    public void render(SpriteBatch batch, float progress) {
        float screenWidth  =  viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float slideX = -screenWidth + (screenWidth * progress);
        batch.draw(textureRegion, slideX, 0, screenWidth, screenHeight);
        batch.end();
    }

    @Override
    public void setTextureTransition(Texture textureRegion) {
        Logger.debug("texture already set");
        this.textureRegion  = textureRegion;
    }

    @Override
    public void setViewport(Viewport viewport) {
        Logger.debug("viewport already set");

        this.viewport = viewport;
    }


}
