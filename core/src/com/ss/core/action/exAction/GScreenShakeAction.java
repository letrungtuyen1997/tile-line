package com.ss.core.action.exAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.core.util.GLayer;

public class GScreenShakeAction extends Action {
   float curOffX;
   float curOffY;
   float duration;
   Group[] layers;
   float time;
   float shakeStregth = 25;
   Actor gr;
   public static GScreenShakeAction screenShake(float duration, int strength, GLayer... var1) {
      GScreenShakeAction var3 = (GScreenShakeAction)Actions.action(GScreenShakeAction.class);
      var3.duration = duration;
      var3.layers = new Group[var1.length];
      var3.shakeStregth = strength;

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var3.layers[var2] = var1[var2].getGroup();
      }

      var3.time = 0.0F;
      return var3;
   }
   public static GScreenShakeAction screenShake1(float duration, float strength, Actor var1) {
      GScreenShakeAction var3 = (GScreenShakeAction)Actions.action(GScreenShakeAction.class);
      var3.duration = duration;
      var3.gr = var1;
      var3.shakeStregth = strength;

//      for(int var2 = 0; var2 < var1.length; ++var2) {
//         var3.layers[var2] = var1[var2].getGroup();
//      }

      var3.time = 0.0F;
      return var3;
   }


   public boolean act(float var1) {
      if(this.time == 0.0F) {
         this.begin();
      }

      if(this.time >= this.duration) {
         this.translateLayer1(-this.curOffX, -this.curOffY);
        // Gdx.app.log("s", "this.")
         return true;
      } else {
         float sx = MathUtils.random(-shakeStregth, shakeStregth);
         float sy = MathUtils.random(-shakeStregth, shakeStregth);
         this.translateLayer1(sx - this.curOffX, sy - this.curOffY);
         this.curOffX = sx;
         this.curOffY = sy;
         this.time += var1+var1/2;
         return false;
      }
   }

   public void begin() {
      this.curOffY = 0;
      this.curOffX = 0;
   }

   public void translateLayer(int var1, int var2) {
      Group[] var5 = this.layers;
      int var4 = var5.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         var5[var3].moveBy((float)var1, (float)var2);
      }

   }
   public void translateLayer1(float var1, float var2) {
      Actor var5 = this.gr;
//      int var4 = var5.length;

//      for(int var3 = 0; var3 < var4; ++var3) {
         var5.moveBy((float)var1, (float)var2);
//      }

   }

}
