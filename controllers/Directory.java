package controllers;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public interface Directory {
	public abstract Lookup with(Class<?> type, Serializable id);
	public abstract Lookup withvisibility();
	public abstract HashSet<Serializable> get(Class<?> type);
	public abstract Lookup add(Class<?> type, Serializable id);
	public abstract Lookup remove(Class<?> type, Serializable id);
	public abstract Lookup link(List<Entry<Class<?>, Serializable>> items);
	public abstract Lookup delink(List<Entry<Class<?>, Serializable>> items);
	public abstract void update();
	public abstract Lookup sync();
}
