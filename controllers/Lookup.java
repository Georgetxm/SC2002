package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import entities.Camp;

public class Lookup implements Directory{
	private HashMap<Entry<Class<?>,Serializable>,HashMap<Class<?>,HashSet<Serializable>>> network = new HashMap<Entry<Class<?>,Serializable>,HashMap<Class<?>,HashSet<Serializable>>>();
	private HashMap<Class<?>,HashSet<Serializable>> store = new HashMap<Class<?>,HashSet<Serializable>>();
	private HashSet<Integer> visible = new HashSet<Integer>();
	private HashSet<Class<?>> classes = new HashSet<Class<?>>();
	
	private static HashMap<Class<?>,Object> regularfilters;
	private static boolean visibilityfilter;
	
	public Lookup with(Class<?> type, Serializable value){regularfilters.put(type, value); return this;}
	public Lookup withvisibility() {visibilityfilter = true; return this;}
	public HashSet<Serializable> get(Class<?> type) {
		HashSet<Serializable> returnset = store.get(type);
		for(Class<?> filtertype : regularfilters.keySet())
			returnset.retainAll(
					network.get(new HashMap.SimpleEntry<Class<?>,Object>(filtertype,regularfilters.get(filtertype))).get(type));
		if(type.equals(Camp.class)) {
			if(visibilityfilter) returnset.retainAll(visible);
		}
		regularfilters.clear();
		visibilityfilter = false;
		return returnset;
	}public void add(Class<?> type, Serializable id) {
		if(!classes.contains(type)) {
			classes.add(type);
			for(HashMap<Class<?>,HashSet<Serializable>> node : network.values())
				node.put(type, new HashSet<Serializable>());
			store.put(type, new HashSet<Serializable>());
		}
		store.get(type).add(id);
		HashMap<Class<?>,HashSet<Serializable>> entry = new HashMap<Class<?>,HashSet<Serializable>>();
		for(Class<?> eachtype :classes) 
			entry.put(eachtype, new HashSet<Serializable>());
		network.put(new HashMap.SimpleEntry<Class<?>,Serializable>(type,id), entry);
		update();
	}
	public void remove(Class<?> type, Serializable id) {
		for(Entry<Class<?>,HashSet<Serializable>> neighbourclass: network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(type,id)).entrySet())
			for(Serializable neighbour: neighbourclass.getValue())
				network.remove(new HashMap.SimpleEntry<Class<?>,Serializable>(neighbourclass.getKey(),neighbour));
		network.remove(new HashMap.SimpleEntry<Class<?>,Serializable>(type,id));
		store.get(type).remove(id);
		update();
	}
	public void link(List<Entry<Class<?>, Serializable>> items) {
		for(int i=0;i<items.size()-1;i++) for(int j=i+1;j<items.size();j++) {
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(i).getKey(),items.get(i).getValue())).get(items.get(j).getKey()).add(items.get(j).getValue());
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(j).getKey(),items.get(j).getValue())).get(items.get(i).getKey()).add(items.get(i).getValue());
		}
		update();
	}
	public void delink(List<Entry<Class<?>, Serializable>> items) {
		for(int i=0;i<items.size()-1;i++) for(int j=i+1;j<items.size();j++) {
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(i).getKey(),items.get(i).getValue())).get(items.get(j).getKey()).remove(items.get(j).getValue());
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(j).getKey(),items.get(j).getValue())).get(items.get(i).getKey()).remove(items.get(i).getValue());
		}
		update();
	}
	public void update() {
		try {
	        File fileOne=new File("classes");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(classes);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {}
		try {
	        File fileOne=new File("network");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(network);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {}
		try {
	        File fileOne=new File("store");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(store);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {}
	}
	@SuppressWarnings("unchecked")
	public Lookup init() {
		try {
			File toRead=new File("classes");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        classes = (HashSet<Class<?>>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {}
		try {
			File toRead=new File("network");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        network = (HashMap<Entry<Class<?>, Serializable>, HashMap<Class<?>, HashSet<Serializable>>>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {}
		try {
			File toRead=new File("store");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        store = (HashMap<Class<?>, HashSet<Serializable>>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {}
		return this;
	}
}
