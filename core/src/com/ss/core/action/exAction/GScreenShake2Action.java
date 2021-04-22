package com.ss.core.action.exAction;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.core.util.GLayer;

public class GScreenShake2Action extends Action {
   int curOffX;
   int curOffY;
   float duration;
   Group[] layers;
   float time;
   int shakeStregth = 25;
   Group gr;
   public static GScreenShake2Action screenShake(float duration, int strength, GLayer... var1) {
      GScreenShake2Action var3 = (GScreenShake2Action)Actions.action(GScreenShake2Action.class);
      var3.duration = duration;
      var3.layers = new Group[var1.length];
      var3.shakeStregth = strength;

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var3.layers[var2] = var1[var2].getGroup();
      }

      var3.time = 0.0F;
      return var3;
   }
   public static GScreenShake2Action screenShake1(float duration, int strength, Group var1) {
      GScreenShake2Action var3 = (GScreenShake2Action)Actions.action(GScreenShake2Action.class);
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
         int sx = MathUtils.random(-shakeStregth, shakeStregth);
         int sy = MathUtils.random(-shakeStregth, shakeStregth);
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
   public void translateLayer1(int var1, int var2) {
      Group var5 = this.gr;
//      int var4 = var5.length;

//      for(int var3 = 0; var3 < var4; ++var3) {
         var5.moveBy((float)var1, (float)var2);
//      }

   }

}
