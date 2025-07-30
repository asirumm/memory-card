package com.diagantika;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.diagantika.Screen.SplashScreen;
import com.diagantika.ScreenManager.ApplicationScreen;
import com.diagantika.Transition.BidirectionalTransition;
import com.diagantika.Transition.HorizontalTransition;
import com.diagantika.ScreenManager.TransitionManager;
import org.tinylog.Logger;

public class Main extends ApplicationScreen {

    public HorizontalTransition transition;
    public BidirectionalTransition bidirectionalTransition;

    @Override
    public void create() {
        transitionManager = new TransitionManager();
        viewport = new ExtendViewport(640,480);
        batch = new SpriteBatch();

        transition = new HorizontalTransition();
        bidirectionalTransition = new BidirectionalTransition();
        transition.setTextureTransition(new Texture("manifest/background.png"));
        bidirectionalTransition.setTextureTransition(new Texture("manifest/background.png"));

        Logger.debug("start");

        setFirstScreen(new SplashScreen());
    }
}
