package com.diagantika;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.ScreenManager.ApplicationScreen;
import com.diagantika.ScreenManager.TransitionManager;
import com.diagantika.Transition.BidirectionalHorizontalTransition;
import com.diagantika.Transition.HorizontalTransition;
import com.diagantika.Transition.ScreenTransition;

import java.util.HashMap;

public class Main extends ApplicationScreen {

    private HashMap<Transition,ScreenTransition> transition;

    public enum Transition{
        HORIZONTAL_TRANSITION,
        BIDERECTIONAL_TRANSITION
    }

    @Override
    public void create() {
        transitionManager = new TransitionManager();
        viewport = new ExtendViewport(Constant.VIRTUAL_WIDTH,Constant.VIRTUAL_HEIGHT);
        batch = new SpriteBatch();

        transition = new HashMap<>();
        transition.put(Transition.HORIZONTAL_TRANSITION,new HorizontalTransition());
        transition.put(Transition.BIDERECTIONAL_TRANSITION,new BidirectionalHorizontalTransition());

//        setFirstScreen(new SplashScreen());
    }

    public SpriteBatch getSpriteBath(){
        return batch;
    }

    public Viewport getViewport(){
        return viewport;
    }
}
