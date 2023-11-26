package interactions;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Scanner;

import camsAction.MissingRequestedDataException;
import controllers.Controller;
import entities.UserInfoMissingException;
import types.CampAspect;
import types.Role;

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
	/**
	 * The interaction it returns.
	 * <p>
	 * All interactions must return their next interaction that they build so the menu tree may be traversed.
	 * An exception is the root interaction start menu returns donothing, which then returns null.
	 * This next interaction can change during runtime to allow uses to take different paths through the menus based off choices they make.
	 */
	protected Interaction next;
	/**
	 * A parameter passed by setter injection, denoting a user filter for camps.
	 */
	protected String userid;
	/**
	 * A parameter passed by setter injection, denoting a user filter for camps.
	 */
	protected Integer campid;
	/**
	 * A parameter passed by setter injection, denoting a suggestion filter.
	 */
	protected Integer suggestionid;
	/**
	 * A parameter passed by setter injection, denoting an enquiry filter.
	 */
	protected Integer enquiryid;
	/**
	 * A parameter passed by setter injection, denoting a user filter for enquiries and suggestions.
	 */
	protected String ownerid;
	/**
	 * A parameter passed by setter injection, denoting a list of camp aspect filters for camps.
	 */
	protected HashMap<CampAspect,Object> filters;
	/**
	 * A parameter passed by setter injection, denoting the roles required for a given attendance report.
	 */
	protected EnumSet<Role> rolerequested = EnumSet.noneOf(types.Role.class);
	/**
	 * A parameter passed by setter injection, denoting a message to be printed out immediately after this interaction is ran.
	 */
	protected String message;
	/**
	 * Function to be run
	 * @param currentuser the id of the current user
	 * @param s the scanner used for the entire app
	 * @param control the controller which contains all the control implementations
	 * @return the result of the run
	 * @throws UserInfoMissingException if the user id of the current user cannot be found
	 * @throws MissingRequestedDataException if the function requested data from the controller or Data, and it was invalid or missing.
	 */
	public abstract Interaction run(String currentuser, Scanner s, Controller control) throws UserInfoMissingException,MissingRequestedDataException;

	/**
	 * @param id the id of the user to filter by
	 * @return itself
	 */
	public final Interaction withuser(String id) {
		this.userid = id;
		return this;
	}
	/**
	 * @param id the id of the camp to filter by
	 * @return itself
	 */
	public final Interaction withcamp(int id) {
		this.campid = id;
		return this;
	}
	/**
	 * @param id the id of the suggestion to filter by
	 * @return itself
	 */
	public final Interaction withsuggestion(int id) {
		this.suggestionid=id;
		return this;
	}
	/**
	 * @param id the id of the enquiry to filter by
	 * @return itself
	 */
	public final Interaction withenquiry(int id) {
		this.enquiryid = id;
		return this;
	}
	/**
	 * @param filter a list of camp aspects to filter by
	 * @return itself
	 */
	public final Interaction withfilter(HashMap<CampAspect, Object> filter) {
		this.filters = filter;
		return this;
	}
	/**
	 * @param id the id of the user to filter by
	 * @return itself
	 */
	public final Interaction withowner(String id) {
		this.ownerid=id;
		return this;
	}
	/**
	 * @param roles the list of roles requested for the attendance list
	 * @return itself
	 */
	public final Interaction withroles(EnumSet<Role> roles) {
		this.rolerequested=roles;
		return this;
	}
}
