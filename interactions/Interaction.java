package interactions;
import java.util.HashMap;

public abstract class Interaction {
	protected String message;
	protected abstract boolean run(HashMap<String, Object> data) throws Exception;
}
