package controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import entities.Camp;
import entities.CampInfo;
import entities.Suggestion;
import entities.Enquiry;
import entities.Student;
import entities.User;
import types.CampAspects;
import types.Faculty;
import types.Perms;
import types.Role;

public class MainController implements CampController, UserController, SuggestionController, EnquiryController {
    private ArrayList<User> users;
    private ArrayList<Camp> camps;
    private ArrayList<Suggestion> suggestions;
    private ArrayList<Enquiry> enquiries;

    public MainController(ArrayList<User> users, ArrayList<Camp> camps) {
        this.users = users;
        this.camps = camps;
        this.suggestions = new ArrayList<Suggestion>();
        this.enquiries = new ArrayList<Enquiry>();
    }

    /*
     * Returns a Camp object given its ID
     * 
     * @param campId the Camp's ID
     * 
     * @return the Camp object with the given ID, null if not found
     */

    public Camp findCampById(int campId) {
        for (Camp camp : camps) {
            if (camp.getCampid() == campId) {
                return camp;
            }
        }
        return null;
    }

    public Suggestion findSuggestionById(int campId, int suggestionId) {
        Camp camp = findCampById(campId);
        for (Integer suggestion : camp.getSuggestions()) {
            if (suggestion == suggestionId) {
                for (Suggestion s : suggestions) {
                    if (s.getSuggestionId() == suggestionId) {
                        return s;
                    }
                }
            }

        }
        return null;
    }

    /*
     * Create a new Camp Object based on the given CampInfo object
     * Adds the Camp object to the camps ArrayList
     * 
     * @param info the CampInfo object containing the Camp's information, @See
     * CampInfo record class for more details
     * 
     * @return the Camp's ID
     */
    @Override
    public int addCamp(CampInfo info, String ownerid) {
        Camp camp = new Camp(info, new HashSet<String>(), new HashSet<String>(), false, LocalDate.now());
        camps.add(camp);
        return camp.getCampid();
    }

    /*
     * Tags a User to a Camp object's attendees or committee HashMap attribute
     * Tags a Camp to a Student object's camps HashSet or campCommittee int
     * attribute
     * 
     * @param campId the Camp this User belongs to
     * 
     * @param userId the User's ID
     * 
     * @param role the User's role
     * 
     * @return true if the User is successfully added to the Camp, false otherwise
     */
    @Override
    public boolean joinCamp(int campId, String userId, Role role) {
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null) {
            if (role == Role.ATTENDEE &&
                    camp.addAttendee(userId) &&
                    user.registerAsAttendee(campId)) {
                return true;
            }
            if (role == Role.COMMITTEE
                    && camp.addCommittee(userId)
                    && user.setCampComittee(campId)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Overriden method from CampController
     * Tags an enquiry to a Camp object's enquiries HashMap
     * 
     * @param campId the Camp this enquiry belongs to
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully added to the Camp, false
     */
    @Override
    public boolean addEnquiry(int campId, int enquiryId) {
        Camp camp = findCampById(campId);
        if (camp == null) {
            return false;
        } else {
            if (!camp.addEnquiry(enquiryId)) {
                return false;
            }
        }
        return true;
    }
    /*
     * Overriden method from UserController
     * Tags an enquiry to a Student object's enquiries HashMap
     * 
     * @param userId the Student this enquiry belongs to
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully added to the Student, false
     */
    @Override
    public boolean addEnquiry(String userId, int enquiryId) {
        Student user = (Student) findUserById(userId);
        if (user == null) {
            return false;
        } else {
            if (!user.addEnquiry(enquiryId)) {
                return false;
            }
        }
        return true;
    }
    /*
     * Overriden method from CampController
     * Tags a suggestion to a Camp object's suggestions HashMap
     * 
     * @param campId the Camp this enquiry belongs to
     * 
     * @param suggestionId the suggestion's ID
     * 
     * @return true if the suggestion is successfully added, false otherwise
     */
    @Override
    public boolean addSuggestion(int campId, int suggestionId) {
        Camp camp = findCampById(campId);
        if (camp == null) {
            return false;
        } else {
            if (!camp.addSuggestion(suggestionId)) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * Overriden method from CampController
     * Tags a suggestion to a Camp object's suggestions HashMap
     * Tags a suggestion to a User object's suggestions HashMap
     * 
     * @param campId the Camp this enquiry belongs to
     * 
     * @param creatorId the User who created this enquiry
     * 
     * @param suggestionId the suggestion's ID
     * 
     * @return true if the suggestion is successfully added, false otherwise
     */
    @Override
    public boolean addSuggestion(String creatorId, int suggestionId) {
        Student user = (Student) findUserById(creatorId);
        if (user == null) {
            return false;
        } else {
            if (!user.addSuggestion(suggestionId)) {
                return false;
            }
        }
        return true;
    }

    /*
     * Overriden method from CampController
     * Deletes a Camp object from the camps ArrayList
     * 
     * @param campid the Camp's ID
     * 
     * @return true if the Camp is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteCamp(int campid) {
        Camp camp = findCampById(campid);
        if (camp != null) {
            camps.remove(camp);
            return true;
        }
        return false;
    }

    /*
     * Overriden method from CampController
     * Removes a User from a Camp object's attendees HashMap
     * Removes a Camp from a User object's camps HashSet
     * 
     * @param campid the Camp's ID
     * 
     * @param userid the User's ID
     * 
     * @return true if the User is successfully deleted, false otherwise
     */
    @Override
    public boolean removeAttendeeFromCamp(int campId, String userId) {
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null && user != null) {
            if (camp.removeAttendee(userId) && user.withdrawFromCamp(campId)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Overriden method from CampController
     * Removes an enquiry from a Camp object's enquiries HashMap
     * Removes an enquiry from a User object's enquiries ArrayList
     * 
     * @param campId the Camp's ID
     * 
     * @param creatorId the User's ID
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteEnquiry(int campId, int enquiryId) {
        Camp camp = findCampById(campId);
        if (camp != null && camp.removeEnquiry(enquiryId)){
            return true;
        }
        return false;
    }
    
    /*
     * Overriden method from UserController
     * Removes an enquiry from a User object's enquiries HashSet
     * 
     * @param creatorId the User's ID
     * 
     * @param enquiryId the enquiry's ID
     * 
     * @return true if the enquiry is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteEnquiry(String creatorId, int enquiryId) {
        Student user = (Student) findUserById(creatorId);
        if (user != null && user.removeEnquiry(enquiryId)) {
            return true;
        }
        return false;
    }

    /*
     * Overriden method from CampController
     * Removes an enquiry from a Camp object's enquiries HashSet
     * 
     * @param campId the Camp's ID
     * 
     * @param suggestion the suggestion's ID
     * 
     * @return true if the suggestion is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteSuggestion(int campId, int suggestionId) {
        Camp camp = findCampById(campId);
        if (camp != null && camp.removeSuggestion(suggestionId)) {
            return true;
        }
        return false;
    }
    /*
     * Overriden method from CampController
     * Removes an enquiry from a User object's enquiries HashSet
     * 
     * @param creatorId the User's ID
     * 
     * @param suggestion the suggestion's ID
     * 
     * @return true if the suggestion is successfully deleted, false otherwise
     */
    @Override
    public boolean deleteSuggestion(String creatorId, int suggestionId) {
        Student user = (Student) findUserById(creatorId);
        if (user != null && user.removeSuggestion(suggestionId)) {
            return true;
        }
        return false;
    }
    // @Override
    // public HashMap<String, Integer> getCamps(Entry<CampAspects, ? extends Object>
    // filter) {
    // HashMap<String, Integer> filteredCamps = new HashMap<String, Integer>();
    // for (Camp camp : camps) {
    // for (CampAspects aspect : filter) {
    // if (camp.getCampInfo().info().containsKey(aspect)) {
    // filteredCamps.put(camp.getCampInfo().info().get(aspect).toString(),
    // camp.getCampid());
    // }
    // }
    // }
    // return filteredCamps;
    // }

    /*
     * Returns a HashSet of Student userIds who are attending the Camp
     */
    @Override
    public HashSet<String> getCampAttendees(int campId) {
        Camp camp = findCampById(campId);
        if (camp != null) {
            return camp.getAttendees();
        }
        return null;
    }

    // public Entry<Integer, Role>[] getCampParticipantID(int campId) {
    // Camp camp = findCampById(campId);
    // if (camp != null) {
    // return camp.getParticipants();
    // }
    // return null;
    // }

    /*
     * Returns a User object given its ID
     * 
     * @param userId the User's ID
     * 
     * @return the User object with the given ID, null if not found
     */

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    /*
     * Tags a User to a Camp object's committee HashMap attribute
     * Tags a Camp to a Student object's campCommittee int attribute
     * 
     * @param campId the Camp this User belongs to
     * 
     * @param userId the User's ID
     * 
     * @return true if the User is successfully added to the Camp, false otherwise
     */

    @Override
    public boolean setCampComittee(int campId, String userId) {
        Camp camp = findCampById(campId);
        Student user = (Student) findUserById(userId);
        if (camp != null && camp.addCommittee(userId) && user.setCampComittee(campId)) {
            return true;
        }
        return false;
    }

    /*
     * Returns a CampInfo record object which contains information of the given its
     * ID
     * Check CampAspects.java for the list of attributes in CampInfo
     * 
     * @param campId the Camp's ID
     * 
     * @return the CampInfo object with the given ID, null if not found
     */
    @Override
    public CampInfo getCampDetails(int campId) {
        Camp camp = findCampById(campId);
        if (camp != null) {
            return camp.getCampInfo();
        }
        return null;
    }

	@Override
	public int addEnquiry(String enquiry, String ownerid, int campid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editEnquiry(int enquiryid, String enquiry) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getEnquiry(int enquiryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Controller FilterCamp(int campid) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Controller FilterUser(String userid) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String[] getEnquiries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteEnquiry(int enquiryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean finaliseEnquiry(int enquiryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isEnquiryEditable(int enquiryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveReply(int enquiryid, String reply) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getReply(int enquiryid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addSuggestion(Entry<CampAspects, ? extends Object> suggestion, String rationale, String ownerid,
			int campid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editSuggestion(int id, Entry<CampAspects, ? extends Object> edited, String rationale) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Entry<Entry<CampAspects, ? extends Object>, String> getSuggestion(int suggestionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, Entry<CampAspects, ? extends Object>> getSuggestions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteSuggestion(int suggestionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean finaliseSuggestion(int suggestionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isSuggestionEditable(int suggestionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOwner(int suggestionid) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public int getHostCamp(int suggestionid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setCampComittee(String userid, String name, int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean joinCamp(String userid, String name, int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveCamp(String userid, int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<Integer, String> getCamp(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<Entry<Integer, Integer>> getUserEnquiries(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<Entry<Integer, Integer>> getUserSuggestions(int userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int incrementPoints(String userid, int points) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EnumSet<Perms> grantPerms(String userid, EnumSet<Perms> newperms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Perms> denyPerms(String userid, EnumSet<Perms> removedperms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Perms> replacePerms(String userid, EnumSet<Perms> replacementperms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CampController FilterAspect(Entry<CampAspects, ? extends Object> filter) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public HashMap<Integer, String> getCamps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCampStudentList(int campid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean editCampDetails(int campid, Entry<CampAspects, ? extends Object> detail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAttendeeFull(int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCommiteeFull(int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashSet<String> getCampComittees(int campid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CampController filterVisible() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean toggleCampVisiblity(int campid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Faculty getUserFaculty(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

}
