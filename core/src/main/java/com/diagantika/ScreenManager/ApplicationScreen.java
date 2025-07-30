package com.diagantika.ScreenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.Transition.ScreenTransition;
import org.tinylog.Logger;

/**
 * This class like a Game class or ApplicationAdapter
 * but with fungtionality a transition with texture
 *
 *
 */
public abstract class ApplicationScreen implements ApplicationListener {
    protected Screen currentScreen;
    protected Screen nextScreen;
    protected TransitionManager transitionManager;
    protected SpriteBatch batch;
    protected Viewport viewport;

    /**
     * Switch to new screen
     *
     * @param screen next screen to render
     * @param screenTransition which transition used
     * @param duration default is 1f
     */
    public void switchScreen(Screen screen, ScreenTransition screenTransition, @Null Float duration){
        Logger.debug("switch screen");

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
                        new TransitionCallback() {
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

    /**
     * You should call this method at main screen
     * which screen you want render for first time
     *
     * @param screen the first screen to render
     */
    protected void setFirstScreen(Screen screen){
        Logger.debug("first screen active");
        currentScreen = screen;
        currentScreen.show();
    }

    protected void renderCurrentScreen(){
        Logger.info("render new screen");
        currentScreen.dispose();
        currentScreen = nextScreen;
        currentScreen.show();
        nextScreen    = null;
    }

    @Override
    public void pause() {
        if(currentScreen!=null) currentScreen.pause();
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