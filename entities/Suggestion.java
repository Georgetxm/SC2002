package entities;

import java.time.LocalDate;
import java.util.Map.Entry;

import types.CampAspect;

/**
 * Represents a Suggestion Object
 * 
 * A Suggestion has the base attributres of a campId, rationale,
 * suggestionAspect (the CampAspect to alter @see CampAspect),
 * accepted (whether the suggestion has been accepted by the camp committee),
 * creationDate and lastUpdatedDate
 * Each suggestions has a unique suggestionId which is an auto-incremented
 * static attribute
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-12
 */
public class Suggestion {
    private final String creatorUserId;
    private int nextSuggestionId = 0;
    private final int suggestionId;
    private final int campId;
    private String rationale;
    private Entry<CampAspect, ? extends Object> suggestionAspect;
    private boolean accepted;
    private final LocalDate creationDate;
    private LocalDate lastUpdatedDate;

    /**
     * Constructor for Suggestion. Intended use is for creating a new suggestion
     * 
     * @param creatorUserId
     * @param campid
     * @param rationale
     * @param suggestionAspect
     * @param creationDate
     */
    public Suggestion(String creatorUserId, int campid, String rationale,
            Entry<CampAspect, ? extends Object> suggestionAspect,
            LocalDate creationDate) {
        this.creatorUserId = creatorUserId;
        this.suggestionId = nextSuggestionId++;
        this.campId = campid;
        this.suggestionAspect = suggestionAspect;
        this.accepted = false;
        this.creationDate = creationDate;
        this.lastUpdatedDate = creationDate;
    }

    public String getCreatorUserId() {
        return this.creatorUserId;
    }

    public int getSuggestionId() {
        return this.suggestionId;
    }

    public int getCampId() {
        return this.campId;
    }

    public Entry<CampAspect, ? extends Object> getSuggestionAspect() {
        return this.suggestionAspect;
    }

    public boolean setSuggestionAspect(Entry<CampAspect, ? extends Object> newSuggestionAspect) {
        if (newSuggestionAspect == null) {
            throw new IllegalArgumentException("Suuggestion aspect cannot be null or empty");
        }
        this.suggestionAspect = newSuggestionAspect;
        return true;
    }

    public String getRationale() {
        return this.rationale;
    }

    public boolean setRationale(String newRationale) {
        if (newRationale == null || newRationale.isEmpty()) {
            throw new IllegalArgumentException("Rationale cannot be null or empty");
        }
        this.rationale = newRationale;
        return true;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public boolean setAccepted(boolean accepted) {
        this.accepted = accepted;
        return true;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public LocalDate getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public boolean setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return true;
    }

}