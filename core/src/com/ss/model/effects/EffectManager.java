package com.ss.model.effects;

import com.badlogic.gdx.utils.IntMap;
import com.ss.model.Session;
import com.ss.model.protocols.EffectHandler;

import java.util.List;


@SuppressWarnings("unused")
public class EffectManager implements EffectHandler {
  private static EffectManager instance = new EffectManager();
  private EffectHandler profileEffectHandler;
  private EffectHandler inventoryEffectHandler;
  private EffectHandler dropEffectHandler;
  private IntMap<EffectHandler> extHandlers;

  private EffectManager() {
    profileEffectHandler = ProfileEffectHandler.inst();
    inventoryEffectHandler = InventoryEffectHandler.inst();
    dropEffectHandler = DropEffectHandler.inst();
    extHandlers = new IntMap<>();
  }
  public static EffectManager inst() {
    return instance;
  }

  @Override
  public String handleEffect(Session session, List<Integer> effectFormat) {
    if (effectFormat == null || effectFormat.size() != 4)
      return "unknownFmt";

    int type = effectFormat.get(PARAM0);
    if (type >= 1 && type <= 2) {
      return profileEffectHandler.handleEffect(session, effectFormat);
    }

    if (type == 100) {
      return inventoryEffectHandler.handleEffect(session, effectFormat);
    }

    if (type == 200) {
      return dropEffectHandler.handleEffect(session, effectFormat);
    }

    EffectHandler extHandler = extHandlers.get(type);
    if (extHandler != null)
      return extHandler.handleEffect(session, effectFormat);
    return "unknownFmt";
  }

  public void addExtHandlers(int id, EffectHandler handler) {
    extHandlers.put(id, handler);
  }
}