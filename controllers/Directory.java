package controllers;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

/**
 * Represents a controller class that controls the relationships between entities.
 * This class holds only the ids of entities and stores their links.
 * This controller is responsible for linking, delinking, and querying ids based off each other.
 */
public interface Directory {
	/**
	 * Sets the next get request to only return items linked with this entity
	 * @param type type of the entity
	 * @param id id of the entity
	 * @return itself
	 */
	public abstract Lookup with(Class<?> type, Serializable id);
	/**
	 * Sets the next get request to return only visible camps, if requesting for camps
	 * @return itself
	 */
	public abstract Lookup withvisibility();
	/**
	 * Toggles the visibility of a given camp stored in the directory
	 * @param id id of the camp
	 * @return itself
	 */
	public abstract Boolean togglevisibility(Integer id);
	/**
	 * Gets the ids of all recorded entities of the specified type, subject to filters applied with with()
	 * @param type requested
	 * @return set of ids
	 */
	public abstract HashSet<Serializable> get(Class<?> type);
	/**
	 * Adds a new entity's id to the database. Ensures this entity can be linked with any other entity if needed
	 * @param type class of the entity
	 * @param id id of the entity
	 * @return itself
	 */
	public abstract Lookup add(Class<?> type, Serializable id);
	/**
	 * Removes the entity and all its links from the database.
	 * @param type class of the entity
	 * @param id id of the entity
	 * @return itself
	 */
	public abstract Lookup remove(Class<?> type, Serializable id);
	/**
	 * Links all specified entities with each other
	 * @param items list of entities to be linked
	 * @return itself
	 */
	public abstract Lookup link(List<Entry<Class<?>, Serializable>> items);
	/**
	 * Removes the links of all specified entities with each other, assuming a link exists
	 * @param items list of entities to be delinked
	 * @return itself
	 */
	public abstract Lookup delink(List<Entry<Class<?>, Serializable>> items);
	/**
	 * Updates files in memory
	 */
	public abstract void update();
	/**
	 * Updates itself from memory
	 * @return itself
	 */
	public abstract Lookup sync();
}
