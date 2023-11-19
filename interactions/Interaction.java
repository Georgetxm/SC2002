package interactions;
import java.util.HashMap;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
import entities.UserInfoMissingException;
import types.CampAspect;

/**
 * 
 */
public abstract class Interaction {
	/**
	 * Represents a function object, which doesn't exist in Java, sadly.
	 * <p>
	 * The purpose of these is to allow the passing of functions as a parameters to other functions.
	 * This would, in theory, allow easier dependency injection, a stronger version of dependency inversion that allows hot-swapping of dependencies.
	 * As this is an interface, we cannot specify a constructor (and it would be terrible to do so anyway).
	 * As such we will be relying on setter injection.
	 * For an example of how setter injection allows us to specify dependencies at runtime, check queryAllCampsMenu
	 * @see camsAction.queryCampsMenu
	 */
	protected Interaction next;
	protected String userid;
	protected Integer campid;
	protected Integer suggestionid;
	protected Integer enquiryid;
	protected String ownerid;
	protected HashMap<CampAspect,Object> filters;
	protected String message;
	/**
	 * Function to be run
	 * @return the result of the run
	 * @throws UserInfoMissingException if the user id of the current user cannot be found
	 * @throws MissingRequestedDataException if the function requested data from the controller or Data, and it was invalid or missing.
	 */
	public abstract Interaction run(String currentuser, Scanner s, Controller control) throws UserInfoMissingException,MissingRequestedDataException;

	public final Interaction withuser(String id) {
		this.userid = id;
		return this;
	}
	public final Interaction withcamp(int id) {
		this.campid = id;
		return this;
	}
	public final Interaction withsuggestion(int id) {
		this.suggestionid=id;
		return this;
	}
	public final Interaction withenquiry(int id) {
		this.enquiryid = id;
		return this;
	}
	public final Interaction withfilter(HashMap<CampAspect, Object> filter) {
		this.filters = filter;
		return this;
	}
	public final Interaction withowner(String id) {
		this.ownerid=id;
		return this;
	}
}
