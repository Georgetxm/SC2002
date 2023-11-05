package interactions;
import types.Perms;
public record MenuChoice (Perms perms, String text,Interaction action){}