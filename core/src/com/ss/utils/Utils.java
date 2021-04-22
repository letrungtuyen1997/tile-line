package com.ss.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.WellcomeScene;

import java.util.Date;

public class Utils {
    public static String result ="";
    public static class LeaderBoard{
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int    star;
    }

    private Utils(){

    }


    public static JsonValue GetJsV(String s){
        JsonReader JReader= new JsonReader();
        return JReader.parse(s);
    }


    public static class Store{
      int price;
    }
    public static String compressCoin(long num, int numOf){
        String str = "0";
        String dv = "";
        int ratio = 0;
        double x = 0;

        if(num >= 1000000000){
            ratio = 1000000000;
            dv = "B";
        }
        else if(num >= 1000000){
            ratio = 1000000;
            dv = "M";
        }
        else if(num >= 1000){
            ratio = 1000;
            dv = "K";
        }
        else {
            ratio = 1;
            dv = "";
        }
        x = (double)num/ratio;
        x = Math.floor(x*Math.pow(10, numOf))/Math.pow(10, numOf);
        str = x + dv;


        String strTemp = str.substring(str.length() - 2, str.length());
        if(strTemp.equals(".0")){
            str = str.substring(0, str.length() - 2);
        }
        else {
            strTemp = str.substring(str.length() - 3, str.length()-1);
            if(strTemp.equals(".0")){
                str = str.substring(0, str.length()-3);
                str += dv;
            }
        }

        return str;
    }
    public static String[] readData(){

        String StrLv = Gdx.files.internal("data/data.txt").readString();
       return StrLv.split("\n");
    }
    public static void changeImg(Image img, TextureAtlas atlas,String type){
        TextureRegion region = atlas.findRegion(type);
        region.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        img.setDrawable(new TextureRegionDrawable(region));
    }


}
