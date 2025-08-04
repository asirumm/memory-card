package com.diagantika.Transition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HorizontalTransition implements ScreenTransition {
    private TextureRegion textureRegion;
    private Viewport viewport;

    /**
     * NOTE batch begin and end at ApplicationScreen
     * they manage the batch for framebuffer
     */
    @Override
    public void render(SpriteBatch batch, float progress) {
        if (textureRegion==null||viewport==null)return;

        float screenWidth  =  viewport.getWorldWidth();
        float screenHeight = viewport.getWorldHeight();


        float slideX = -screenWidth + (screenWidth * progress);
        batch.draw(textureRegion, slideX, 0, screenWidth, screenHeight);

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
