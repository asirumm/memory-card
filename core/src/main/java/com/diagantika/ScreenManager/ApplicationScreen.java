package com.diagantika.ScreenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.Constant;
import com.diagantika.Transition.ScreenTransition;
import org.tinylog.Logger;

import static com.diagantika.Constant.VIRTUAL_HEIGHT;
import static com.diagantika.Constant.VIRTUAL_WIDTH;

/**
 * This class like a Game class or ApplicationAdapter
 * but with fungtionality a transition with texture
 *
 *
 */
public class ApplicationScreen implements ApplicationListener {
    protected Screen currentScreen;
    protected Screen nextScreen;
    protected TransitionManager transitionManager;
    protected SpriteBatch batch;
    private FrameBuffer frameBuffer;
    protected OrthographicCamera frameBufferCamera;
    protected OrthographicCamera screenCamera;// current user screen size
    protected Viewport viewport;
    protected Stage stageUI;


    /**
     * So we want to scaling up from minimum screen to target screen
     * we put all to framebuffer and draws the texture from framebuffer
     * with batch
     */
    @Override
    public void create() {
        Logger.info("start initialize");

        transitionManager = new TransitionManager();
        batch             = new SpriteBatch();

        // has depth hanya untuk 3d
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,VIRTUAL_WIDTH,VIRTUAL_HEIGHT,false);

        // frame buffer camera setup
        frameBufferCamera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        // center camera
        frameBufferCamera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        frameBufferCamera.update();

        // target screen camera setup
        screenCamera      = new OrthographicCamera(Constant.screenTargetWidth, Constant.screenTargetHeight);
        // center camera
        screenCamera.position.set(Constant.screenTargetWidth/ 2f,Constant.screenTargetHeight / 2f, 0);
        screenCamera.update();


        // create viewport with camera buffer
        viewport          = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT,frameBufferCamera);

        viewport.update(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);

        // passing our batch, so the stage not creating another batch
        // we still use 1 batch
        stageUI = new Stage(viewport,batch);

        // we directly to target screen
        Gdx.graphics.setWindowedMode(Constant.screenTargetWidth,Constant.screenTargetHeight);
    }
    /**
     * Switch to new screen
     *
     * @param screen next screen to render
     * @param screenTransition which transition used
     * @param duration default is 1f
     */
    public void switchScreen(Screen screen, ScreenTransition screenTransition, @Null Float duration){
        Logger.debug("switch screen to {}",screen.getClass());

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

        frameBuffer.begin();
        ScreenUtils.clear(Color.BLUE);

        batch.setProjectionMatrix(frameBufferCamera.combined);
        batch.begin();

        stageUI.act();
        // kita harus ingat stage.draw dia end batch disana
        stageUI.getRoot().draw(batch,1);

        transitionManager.update(Gdx.graphics.getDeltaTime());

        if (currentScreen != null) {
            currentScreen.render(Gdx.graphics.getDeltaTime());
        }


        // apply nearest filter for pixel art
        frameBuffer.getColorBufferTexture()
                .setFilter(Texture.TextureFilter.Nearest,
                        Texture.TextureFilter.Nearest);


        batch.end();
        frameBuffer.end();

        ScreenUtils.clear(Color.WHITE);

        // start draw scaling up
        batch.setProjectionMatrix(screenCamera.combined);
        batch.begin();


        // ukuran langsung ke target game yakni 1280
        batch.draw(frameBuffer.getColorBufferTexture(),
                0,0,
                Constant.screenTargetWidth , Constant.screenTargetHeight,
                // ini agar tidak flip
                0, 1, 1, 0);

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        screenCamera.setToOrtho(true,width,height);
        screenCamera.update();
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
        frameBuffer.dispose();
        stageUI.dispose();
        if (currentScreen!=null){
            currentScreen.dispose();
        }
        if (nextScreen!=null){
            nextScreen.dispose();
        }

        Logger.debug("dispose");
    }
}