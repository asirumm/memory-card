package com.diagantika;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diagantika.ScreenManager.ApplicationScreen;
import com.diagantika.Transition.BidirectionalHorizontalTransition;
import com.diagantika.Transition.HorizontalTransition;
import com.diagantika.Transition.ScreenTransition;
import org.tinylog.Logger;

import java.util.HashMap;

/**
 * Remember the stageUI is already rendered at ApplicationScreen
 * when you need another stage please render like
 *
 * stageUI.act();
 * stageUI.getRoot().draw(batch,1);
 *
 * it cause we used framebuffer so beware using spritebatch
 *
 * and yah, im recommended to passing spritebatch instance from main
 * when create a stage instance. Cause when you doesnt passing batch
 * the stage will create another instance of spritebatch yet.
 */
public class Main extends ApplicationScreen {

    private HashMap<Transition,ScreenTransition> transition;
    private Skin skin;
    private BitmapFont bitmapFont;

    public enum Transition{
        HORIZONTAL_TRANSITION,
        BIDERECTIONAL_TRANSITION
    }

    @Override
    public void create() {
        super.create();

//        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("widget.atlas"));

        transition = new HashMap<>();
        HorizontalTransition ht = new HorizontalTransition();
        BidirectionalHorizontalTransition bt = new BidirectionalHorizontalTransition();
//        bt.setTextureTransition(atlas.findRegion("button"));


        // DONT FORGET TO SET TEXTURE
        transition.put(Transition.HORIZONTAL_TRANSITION,ht);
        transition.put(Transition.BIDERECTIONAL_TRANSITION,bt);

//        loadSkin("Grand9KPixel.ttf","widget.atlas","widget.json");
//        setFirstScreen(new SplashScree());
    }

    public SpriteBatch getSpriteBath(){
        return batch;
    }

    public HashMap<Transition, ScreenTransition> getTransition() {
        return transition;
    }

    public Viewport getViewport(){
        return viewport;
    }
    public Stage getStageUi(){
        return super.stageUI;
    }
    public OrthographicCamera virtualCamera(){
        return super.frameBufferCamera;
    }

    public Skin getSkin() {
        return skin;
    }

    //TODO saat ini kita masih menggunakan 1 ukuran font kedepannya buat sesuai keinginan
    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public OrthographicCamera screenCamera(){
        return super.screenCamera;
    }

    /**
     * Note :
     * - at your skin json dont include the bitmapfont or freetypefont
     * delete that after you export from scene composer
     * - make sure name of font is "font" if using another please change at
     * this line code
     *   skin.add("font",bitmapFont);
     *
     * ex:
     * "com.badlogic.gdx.scenes.scene2d.ui.TextButton$TextButtonStyle": {
     * 	"default": {
     * 		"font": "font",
     * 		"fontColor": "white",
     * 		"up": "button"
     * 	    }
     * }
     *
     */
    private void loadSkin(String fontName,
                          String atlasFileName,
                          String skinJsonFileName){

        Logger.debug("start load skins and bitmap");

        skin = new Skin();

        FreeTypeFontGenerator freeTypeFontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal(fontName));

        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameters.size = 8;
        parameters.magFilter = Texture.TextureFilter.Nearest;
        parameters.minFilter = Texture.TextureFilter.Nearest;
        parameters.genMipMaps = true;
        parameters.color = Color.WHITE;
        parameters.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        bitmapFont = freeTypeFontGenerator.generateFont(parameters);

        // dont forget dispose
        freeTypeFontGenerator.dispose();

        skin.add("font",bitmapFont);
        skin.addRegions(new TextureAtlas(atlasFileName));
        skin.load(Gdx.files.internal(skinJsonFileName));
    }

    @Override
    public void dispose() {
        super.dispose();
        bitmapFont.dispose();
        skin.dispose();
    }
}
