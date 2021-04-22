package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.LoadParticle;
import com.ss.commons.PaticleConvert;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.WellcomeScene;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.effects.SoundEffect;

public class LoadingScene extends GScreen {
    Group group = new Group();
//    public static Effect effect;
    public static WellcomeScene wellcomeScene = new WellcomeScene();



    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        loading();
        new PaticleConvert();
        BitmapFontC.LoadBitmapFont();
        TextureAtlasC.LoadAtlas();
        SoundEffect.initSound();
        LoadParticle.init();
//        Config.loadjson();


    }
    static boolean firstShow = false;
    @Override
    public void show() {
        super.show();
            if(firstShow == false) {
                firstShow = true;
                GMain.platform.Onshow();
            }
    }

    float waitTime = 3;
    @Override
    public void run() {
         waitTime-= Gdx.graphics.getDeltaTime();
         if(waitTime<=0) {
             if (!GAssetsManager.isFinished()) {
                 System.out.println("load");
                 GAssetsManager.update();
             }
             else {
                 BitmapFontC.InitBitmapFont();
                 BitmapFontC.changeFont(BitmapFontC.setLangDefault(GMain.getSession().profile.idlang));
                 TextureAtlasC.InitAtlas();
//                 effect = new Effect();
                 //new PoolTile();
//                 this.setScreen();
                 System.out.println("chuyen");
             }
         }
    }
    void loading(){
        Image bg = new Image(new Texture("textureAtlas/bgload.png"));
        bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
        bg.setScale(1,-1);
        bg.setOrigin(Align.center);
        group.addActor(bg);
        Image load = new Image(new Texture("textureAtlas/loadding.png"));
        load.setOrigin(Align.center);
        load.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2+150, Align.center);
        group.addActor(load);
        aniload(load);
    }
    void aniload(Image img){
        img.addAction(Actions.sequence(
            Actions.forever(
                Actions.rotateBy(360,1f)
            )
        ));
    }
}
