package com.diagantika.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.diagantika.Main;
import org.tinylog.Logger;

public class SplashScreen implements Screen {
    Main mainApp;
    @Override
    public void show() {
        mainApp = (Main) Gdx.app.getApplicationListener();
        Logger.debug("start splash");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            mainApp.switchScreen(new FirstScreen(),mainApp.bidirectionalTransition ,null);
        }
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
