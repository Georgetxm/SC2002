package core;
import java.util.TreeMap;

import types.CampAspects;

public record CampInfo( TreeMap<CampAspects,Object> info) {
	public CampInfo {
        if (info.size()!= CampAspects.values().length) 
        throw new java.lang.IllegalArgumentException("CampInfo not complete");
    }
}


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