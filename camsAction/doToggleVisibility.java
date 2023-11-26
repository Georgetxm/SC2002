package camsAction;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

import cams.CamsInteraction;
import controllers.Controller;
import interactions.Interaction;
/**
 * Interaction that represents the action of changing the visibility of a camp to a value other than its original value.
 * Effectively serves as a function pointer
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public final class doToggleVisibility extends Interaction {
	/**
	 * Requests the controller to modify a camp's visibility between visible and not visible.
	 *@return the appropriate single camp menu with user, filter and camp tags
	 */
@Override
	public Interaction run(String currentuser, Scanner s, Controller control){
		System.out.println("Camp visibility changed. Camp is now:");
		System.out.println(control.Directory().togglevisibility(campid)?"Visible":"Not Visible");
		HashSet<Serializable> usercamps = null;
		usercamps = control.Directory().sync().with(entities.User.class, currentuser).get(entities.Camp.class);
		next = (usercamps!=null&&usercamps.contains(campid))?CamsInteraction.OwnCampMenu(campid, currentuser):CamsInteraction.OtherCampMenu(campid,currentuser);
		if(this.userid!=null) next = next.withuser(userid);
		if(this.filters!=null) next = next.withfilter(filters);
		return next.withcamp(campid);
	}

}
