package com.diagantika.ScreenManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.Transition.ScreenTransition;
import org.tinylog.Logger;

/**
 * Manager for transition process
 */
public class TransitionManager {
    private float transitionDuration = 1f; // default time for transition
    private float currentTransitionTime = 0f; // time current transition
    private SpriteBatch spriteBatch;
    private boolean isTransitioning = false; // flag for transition start and stop
    private TransitionCallback transitionCallBack;
    private ScreenTransition transition;
    private Screen currentScreen;

    public void startTransition(SpriteBatch batch,
                                Screen currentScreen,
                                ScreenTransition transition,
                                Viewport viewport,
                                TransitionCallback callBack){

        // menunggu sampai transisi sebelumnya selesai
        // waiting for the previous transition to complete
        if (isTransitioning) return;

        Logger.debug("start transition process");


        // setup variable value
        spriteBatch             = batch;
        this.currentScreen      = currentScreen;
        this.transition         = transition;
        transition.setViewport(viewport);
        this.transitionCallBack = callBack;

        // change flag to trigger process transition
        isTransitioning = true;

    }

    public void setTransitionDuration(float transitionDuration) {
        this.transitionDuration = transitionDuration;
    }

    public void update(float delta) {
        // when not any transition to render
        if (!isTransitioning) return;

        // update transition time
        currentTransitionTime += delta;
        float progress = Math.min(currentTransitionTime / transitionDuration, 1.0f);

        // render transition (batch sudah di-begin di render method Application Screen)
        transition.render(spriteBatch, progress);

        // check if transition complete
        if (progress >= 1.0f) {
            finishTransition();
        }
    }

    private void finishTransition() {
        Logger.debug("reset flag transition");

        // reset flag
        isTransitioning       = false;
        currentTransitionTime = 0f;

        // Ganti ke next screen
        // change to next screen
        if (transitionCallBack != null) {
            transitionCallBack.onTransitionComplete();
        }

        // when the user changed we should change to default
        // so not effect to all transition
        transitionDuration = 1f;
    }

    public boolean isTransitioning() {
        return isTransitioning;
    }
}
