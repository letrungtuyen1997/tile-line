package com.ss.model.effects;

import com.badlogic.gdx.math.MathUtils;
import com.ss.model.Session;
import com.ss.model.dto.DropData;
import com.ss.model.protocols.EffectHandler;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


class DropEffectHandler implements EffectHandler {
  @Override
  public String handleEffect(Session session, final List<Integer> effectFormat) {
    int dropId = effectFormat.get(PARAM1);
    DropData.Drop drop = DropData.dropMap.get(dropId);

    if (drop == null)
      return "invalid_drop_id";
    if (drop.type == 1) { //pick 1 of them
//      int rand = ThreadLocalRandom.current().nextInt(1, drop.totalRate + 1);
      int rand = MathUtils.random(1,drop.totalRate+1);
      DropData.Pack selectedPack = null;
      int acc = 0;
      for (DropData.Pack pack : drop.packs) {
        acc += pack.rate;
        if (acc >= rand) {
          selectedPack = pack;
          break;
        }
      }
      if (selectedPack == null)
        return "unknown_drop_pack";
      return EffectManager.inst().handleEffect(session, selectedPack.effect);
    }
    else if (drop.type == 2) {
      for (DropData.Pack pack : drop.packs) {
//        int rand = ThreadLocalRandom.current().nextInt(1, 101);
        int rand = MathUtils.random(1,101);

        if (rand <= pack.rate) {
          EffectManager.inst().handleEffect(session, pack.effect);
        }
      }
      return "ok";
    }
    return "invalid_drop";
  }

  private DropEffectHandler() {

  }

  private static DropEffectHandler inst = new DropEffectHandler();

  public static DropEffectHandler inst() {
    return inst;
  }
}