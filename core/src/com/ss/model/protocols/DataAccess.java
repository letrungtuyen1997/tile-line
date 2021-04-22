package com.ss.model.protocols;

/*
Abstract data io, K,V style
*/
public interface DataAccess<T extends Model<T>> {
  void  sync(String key, T t);
  T     load(String key) throws Exception;
}