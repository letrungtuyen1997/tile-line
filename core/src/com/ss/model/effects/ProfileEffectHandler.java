package com.ss.model.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.ss.model.Session;
import com.ss.model.composite.Profile;
import com.ss.model.protocols.EffectHandler;

import java.lang.reflect.Field;
import java.util.List;


/*
Using composite pattern to stack multiple handlers into 1 handlers
 */
@SuppressWarnings("unused")
class ProfileEffectHandler implements EffectHandler {
  private static final int LINEAR_INCREASE                = 1;
  private static final int BOUND_INCREASE                 = 2;

  private static final int MONEY_PROPERTY                 = 1;
  private static final int EXP_PROPERTY                   = 2;
  private static final int LEVEL_PROPERTY                 = 3;
  private static final int STAR_PROPERTY                  = 4;

  private static final int DISPLAY_NAME                   = 1;

  private static final IntMap<String> fmt2Field;

  static {
    fmt2Field = new IntMap<>();
    fmt2Field.put(MONEY_PROPERTY,   "money");
    fmt2Field.put(EXP_PROPERTY,     "exp");
    fmt2Field.put(LEVEL_PROPERTY,   "level");
    fmt2Field.put(STAR_PROPERTY,    "star");
  }

  private static ProfileEffectHandler inst = new ProfileEffectHandler();
  private IntMap<EffectHandler> subHandlers;

  private ProfileEffectHandler () {
    subHandlers = new IntMap<>();

    subHandlers.put(LINEAR_INCREASE, ((session, eff) -> {
      int propertyId    = eff.get(EffectHandler.PARAM1);
      int amount        = eff.get(EffectHandler.PARAM2);

      Field field               = getProfileField(fmt2Field.get(propertyId));

      if (field != null) {
        try {
          int oldValue = field.getInt(session.profile);
          int newValue = oldValue + amount;
          field.setInt(session.profile, newValue);
          session.effectResult.add(eff);
          return "ok";
        }
        catch (IllegalAccessException e) {
          return "unknownProps";
        }
      }

      return "unknownProps";
    }));

    subHandlers.put(BOUND_INCREASE, ((session, eff) -> {
      int propertyId    = eff.get(EffectHandler.PARAM1);
      int lowBound      = eff.get(EffectHandler.PARAM2);
      int upBound       = eff.get(EffectHandler.PARAM3);

      Field field       = getProfileField(fmt2Field.get(propertyId));

      if (field != null) {
        try {
          int oldValue        = field.getInt(session.profile);
          int rangeIncrement  = MathUtils.random(lowBound, upBound);
          int newValue        = oldValue + rangeIncrement;
          if (newValue < 0)
            newValue = 0;
          field.setInt(session.profile, newValue);
          session.effectResult.add(eff);
          return "ok";
        }
        catch (IllegalAccessException e) {
          return "unknownProps";
        }
      }

      return "unknownProps";
    }));
  }

  private Field getProfileField(String fieldName) {
    try {
      return Profile.class.getField(fieldName);
    }
    catch (Exception e) {
      Gdx.app.error("ProfileEffectHandler.getUserInfoField", e.getMessage());
      return null;
    }
  }

  public static ProfileEffectHandler inst() {
    return inst;
  }

  @Override
  public String handleEffect(Session session, List<Integer> effectFormat) {
    EffectHandler subHandler = subHandlers.get(effectFormat.get(PARAM0));

    if (subHandler != null) {
      return subHandler.handleEffect(session, effectFormat);
    }
    return "unknownFmt";
  }
}