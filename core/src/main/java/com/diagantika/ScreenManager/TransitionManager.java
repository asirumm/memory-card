package com.diagantika.ScreenManager;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.Transition.ScreenTransition;
import org.tinylog.Logger;

public class TransitionManager {
    private float transitionDuration = 1f; // default time for transition
    private float currentTransitionTime = 0f; // time current transition
    private SpriteBatch spriteBatch;
    private boolean isTransitioning = false; // flag for transition start and stop
    private TransitionCallBack transitionCallBack;
    private ScreenTransition transition;
    private Screen currentScreen;

    public void startTransition(SpriteBatch batch,
                                Screen currentScreen,
                                ScreenTransition transition,
                                Viewport viewport,
                                TransitionCallBack callBack){
        // menunggu sampai transisi sebelumnya selesai
        if (isTransitioning) return;

        Logger.debug("start transition");

        spriteBatch             = batch;
        this.currentScreen      = currentScreen;
        this.transition         = transition;
        transition.setViewport(viewport);
        this.transitionCallBack = callBack;

        isTransitioning = true;

        Logger.debug("transition ",isTransitioning);
    }

    public void setTransitionDuration(float transitionDuration) {
        this.transitionDuration = transitionDuration;
    }

    public void update(float delta) {
        if (isTransitioning==false) return;

        currentTransitionTime += delta;
        float progress = Math.min(currentTransitionTime / transitionDuration, 1.0f);

        currentScreen.render(delta);

        transition.render(spriteBatch,progress);

        if (progress >= 1.0f) {
            // Transisi selesai
            finishTransition();
        }
    }

    private void finishTransition() {
        Logger.debug("reset flag transition active");
        // reset flag
        isTransitioning       = false;
        currentTransitionTime = 0f;

        // Ganti ke next screen
        if (transitionCallBack != null) {
            transitionCallBack.onTransitionComplete();
        }

        transitionDuration = 1f;
    }

    public boolean isTransitioning() {
        return isTransitioning;
    }
}
