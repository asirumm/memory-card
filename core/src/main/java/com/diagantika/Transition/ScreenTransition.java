package com.diagantika.Transition;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface ScreenTransition {

    /**
     * @param textureRegion texture for transition
     */
    void setTextureTransition(TextureRegion textureRegion);

    /**
     * @param viewport need for rendering with batch
     */
    void setViewport(Viewport viewport);
    void render(SpriteBatch batch, float progress);
}
