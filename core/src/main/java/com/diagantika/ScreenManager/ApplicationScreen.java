package com.diagantika.ScreenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.Transition.ScreenTransition;
import org.tinylog.Logger;

public abstract class ApplicationScreen implements ApplicationListener {
    protected Screen currentScreen;
    protected Screen nextScreen;
    protected TransitionManager transitionManager;
    protected SpriteBatch batch;
    protected Viewport viewport;

    public void switchScreen(Screen screen, ScreenTransition screenTransition, @Null Float duration){
        Logger.debug("switch");
        nextScreen = screen;

        if (duration!=null){
            transitionManager.setTransitionDuration(duration);
        }

        transitionManager
                .startTransition(
                        batch,
                        currentScreen,
                        screenTransition,
                        viewport,
                        new TransitionCallBack() {
                            @Override
                            public void onTransitionComplete() {
                                renderCurrentScreen();
                            }
                        });
    }

    @Override
    public void render() {

        transitionManager.update(Gdx.graphics.getDeltaTime());

        if (transitionManager.isTransitioning()==false) {
            // jika tidak sedang berada di transisi kita render screen biasa
            if (currentScreen != null) {
                currentScreen.render(Gdx.graphics.getDeltaTime());
            }
            return;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.apply();
        viewport.update(width,height,true);
    }

    protected void setFirstScreen(Screen screen){
        Logger.debug("first screen active");
        currentScreen = screen;
        currentScreen.show();
    }

    protected void renderCurrentScreen(){
        Logger.info("switch screen");
        currentScreen.dispose();
        currentScreen = nextScreen;
        currentScreen.show();
        nextScreen    = null;
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        if (currentScreen!=null){
            currentScreen.dispose();
        }
        if (nextScreen!=null){
            nextScreen.dispose();
        }

        Logger.debug("dispose");
    }
}
