package com.diagantika.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import org.tinylog.Logger;

public class FirstScreen implements Screen {
    @Override
    public void show() {
        Logger.debug("start first screen");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ORANGE);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Logger.debug("dispose");
    }
}
