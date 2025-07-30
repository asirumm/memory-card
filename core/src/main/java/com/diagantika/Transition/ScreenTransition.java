package com.diagantika.Transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface ScreenTransition {
    public void setTextureTransition(Texture textureRegion);
    public void setViewport(Viewport viewport);
    public void render(SpriteBatch batch, float progress);
}
