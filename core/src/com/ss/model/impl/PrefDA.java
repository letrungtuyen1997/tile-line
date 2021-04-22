package com.ss.model.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.ss.model.protocols.DataAccess;
import com.ss.model.protocols.Model;


/*
Implement HttpDA of you want to save and load data remotely :)
 */
public class PrefDA<T extends Model<T>> implements DataAccess<T> {
  private final Class<T>  type;
  Json serializer;
  Preferences prefs;

  public PrefDA(Class<T> type) {
    this.type = type;
    this.serializer = new Json();
    this.serializer.setIgnoreUnknownFields(true);
    this.prefs = Gdx.app.getPreferences("def");
  }

  @Override
  public void sync(String key, T t) {
    String val = serializer.toJson(t, type);
    prefs.putString(key, val);
    prefs.flush();
  }

  @Override
  public T load(String key) {
    String val = prefs.getString(key, "");
    return serializer.fromJson(type, val);
  }
}