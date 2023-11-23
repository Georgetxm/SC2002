package controllers;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public interface Directory {
	public abstract Lookup with(Class<?> type, Serializable id);
	public abstract Lookup withvisibility();
	public abstract HashSet<Serializable> get(Class<?> type);
	public abstract void add(Class<?> type, Serializable id);
	public abstract void remove(Class<?> type, Serializable id);
	public abstract void link(List<Entry<Class<?>, Serializable>> items);
	public abstract void delink(List<Entry<Class<?>, Serializable>> items);
	public abstract void update();
	public abstract Lookup init();
}
