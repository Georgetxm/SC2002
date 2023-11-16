package interactions;
import types.Perms;
/**
 * Represents a choice that can be offered in a Menu along with other choices.
 * <p>
 * As function objects do not exist in Java, we have taken the liberty to create an interface called Interaction that mimics this behaviour
 * For examples of how MenuChoices are initialised, see CamsInteractions
 * @param perms The permission required for the user to see this choice
 * @param text The text to be shown on the menu when the choice is displayed
 * @param action The next function (Interaction) to be called when the choice is selected
 * @see cams.CamsInteraction
 * @see Interaction
 */
public record MenuChoice (Perms perms, String text,Interaction action){}