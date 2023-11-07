package core;
import java.util.LinkedHashMap;
import types.CampAspects;

public record CampInfo( LinkedHashMap<CampAspects,Object> info) {}


// Currently
// {
//		CampAspects.NAME: String name
//		CampAspects.DATE: HashSet<LocalDate> dates
//		CampAspects.LASTREGISTERDATE: LocalDate regdate
//		CampAspects.USERGROUP: Faculty faculty
//		CampAspects.LOCATION: Location location
//		CampAspects.SLOTS: Integer totalslots
//		CampAspects.COMITTEESLOTS: Integer comitteeslots
//		CampAspects.DESCRIPTION: String description
//		CampAspects.STAFFIC: Integer userid
// }