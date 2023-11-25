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

/**
 * A class that implements the functions required for a directory.
 */
public class Lookup implements Directory{
	/**
	 * Represents a graph of entities. Each entity is a node of the graph.
	 * <p>
	 * Each entity can be linked to any other entity, and stores a hashset for each type of entity it can be linked to.
	 * Entity types are distinguished by their Java class, and the adjacency sets are stored in a HashMap.
	 * This allows O(1) retrieval of linked elements, if a source is specified.
	 */
	private HashMap<Entry<Class<?>,Serializable>,HashMap<Class<?>,HashSet<Serializable>>> network = new HashMap<Entry<Class<?>,Serializable>,HashMap<Class<?>,HashSet<Serializable>>>();
	/**
	 * Stores all elements grouped by type. This allows O(1) retrieval of elements of a specific type.
	 */
	private HashMap<Class<?>,HashSet<Serializable>> store = new HashMap<Class<?>,HashSet<Serializable>>();
	/**
	 * Stores a set of visible camps. The visibility of a camp depends on whether it is part of this set.
	 */
	private HashSet<Integer> visible = new HashSet<Integer>();
	/**
	 * Stores a set of all entity classes encountered to aid in removal and addition of elements.
	 */
	private HashSet<Class<?>> classes = new HashSet<Class<?>>();
	/**
	 * Stores entities to serve as filters for the next get operation.
	 * <p>
	 * Only elements with links to all these entities will be passed on during get operations. Entities without links to every entity listed here will be filtered out.
	 */
	private static HashMap<Class<?>,Object> regularfilters = new HashMap<Class<?>,Object>();
	/**
	 * Denotes whether the next get operation will exclude non visible camps.
	 */
	private static boolean visibilityfilter;
	
	public Lookup with(Class<?> type, Serializable value){regularfilters.put(type, value); return this;}
	public Lookup withvisibility() {visibilityfilter = true; return this;}
	public HashSet<Serializable> get(Class<?> type) {
		HashSet<Serializable> returnset = store.get(type);
		if(returnset == null) returnset = new HashSet<Serializable>();
		for(Class<?> filtertype : regularfilters.keySet()) {
			HashSet<Serializable> filter = null;
			if(regularfilters.get(filtertype)!=null)
				filter = network.get(new HashMap.SimpleEntry<Class<?>,Object>(filtertype,regularfilters.get(filtertype))).get(type);
			if(filter!=null)returnset.retainAll(filter);
		}
		if(type.equals(Camp.class)) {
			if(visibilityfilter) returnset.retainAll(visible);
		}
		regularfilters.clear();
		visibilityfilter = false;
		return returnset;
	}public Lookup add(Class<?> type, Serializable id) {
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
		return this;
	}
	public Lookup remove(Class<?> type, Serializable id) {
		for(Entry<Class<?>,HashSet<Serializable>> neighbourclass: network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(type,id)).entrySet())
			for(Serializable neighbour: neighbourclass.getValue())
				network.remove(new HashMap.SimpleEntry<Class<?>,Serializable>(neighbourclass.getKey(),neighbour));
		network.remove(new HashMap.SimpleEntry<Class<?>,Serializable>(type,id));
		store.get(type).remove(id);
		update();
		return this;
	}
	public Lookup link(List<Entry<Class<?>, Serializable>> items) {
		for(int i=0;i<items.size()-1;i++) for(int j=i+1;j<items.size();j++) {
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(i).getKey(),items.get(i).getValue())).get(items.get(j).getKey()).add(items.get(j).getValue());
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(j).getKey(),items.get(j).getValue())).get(items.get(i).getKey()).add(items.get(i).getValue());
		}
		update();
		return this;
	}
	public Lookup delink(List<Entry<Class<?>, Serializable>> items) {
		for(int i=0;i<items.size()-1;i++) for(int j=i+1;j<items.size();j++) {
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(i).getKey(),items.get(i).getValue())).get(items.get(j).getKey()).remove(items.get(j).getValue());
			network.get(new HashMap.SimpleEntry<Class<?>,Serializable>(items.get(j).getKey(),items.get(j).getValue())).get(items.get(i).getKey()).remove(items.get(i).getValue());
		}
		update();
		return this;
	}
	public void update() {
		try {
	        File fileOne=new File("visible");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(visible);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		try {
	        File fileOne=new File("classes");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(classes);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
		try {
	        File fileOne=new File("network");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(network);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {e.printStackTrace();}
		try {
	        File fileOne=new File("store");
	        FileOutputStream fos=new FileOutputStream(fileOne);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);

	        oos.writeObject(store);
	        oos.flush();
	        oos.close();
	        fos.close();
	    } catch(Exception e) {e.printStackTrace();}
	}
	@SuppressWarnings("unchecked")
	public Lookup sync() {
		try {
			File toRead=new File("classes");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        classes = (HashSet<Class<?>>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {e.printStackTrace();}
		try {
			File toRead=new File("visible");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        visible = (HashSet<Integer>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {e.printStackTrace();}
		try {
			File toRead=new File("network");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        network = (HashMap<Entry<Class<?>, Serializable>, HashMap<Class<?>, HashSet<Serializable>>>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {e.printStackTrace();}
		try {
			File toRead=new File("store");
	        FileInputStream fis=new FileInputStream(toRead);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        store = (HashMap<Class<?>, HashSet<Serializable>>) ois.readObject();
	        ois.close();
	        fis.close();
		}catch(Exception e) {e.printStackTrace();}
		return this;
	}
	@Override
	public Boolean togglevisibility(Integer id) {
		if(visible.contains(id)) {
			visible.remove(id);
			update();
			return false;
		}
		visible.add(id);
		update();
		return true;
	}
}
