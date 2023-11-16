package camsAction;
/**
 * Exception thrown when some Interaction does not have the requisite data it requires to carry out its action.
 * Interactions pass data to each other via Data.
 * If a request to Data fails, it will usually raise this exception.
 * This is usually handled by GetData, which contains error handling wrapper functions for interacting with Data
 * @see entities.Data
 * @see GetData
 * @author Tay Jih How
 * @version 1.0
 * @since 2021-11-01
 */
public class MissingRequestedDataException extends Exception {
	/**
	 * @param string to indicate the data requested that was missing or malformed, or any other comments.
	 */
	public MissingRequestedDataException(String string) {
		super(string);
	}

	/**
	 * Generated serial id for serialisation
	 */
	private static final long serialVersionUID = -3502861909870023906L;

}
