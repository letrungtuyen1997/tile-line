package com.ss.model.effects;

import com.ss.model.Session;
import com.ss.model.dto.PropData;
import com.ss.model.protocols.EffectHandler;

import java.util.List;


class InventoryEffectHandler implements EffectHandler {
  private static InventoryEffectHandler instance = new InventoryEffectHandler();

  @Override
  public String handleEffect(Session session, List<Integer> effectFormat) {
    int propId = effectFormat.get(PARAM1);
    int amount = effectFormat.get(PARAM2);

    if (!PropData.propMap.containsKey(propId)) {
      return "unknownItem";
    }

    session.inventory.addItem(propId, amount);
    session.effectResult.add(effectFormat);
    return "ok";
  }

  private InventoryEffectHandler() {}
  public static InventoryEffectHandler inst() {
    return instance;
  }
}