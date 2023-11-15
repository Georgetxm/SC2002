package entities;

import java.time.LocalDate;
import java.util.Map.Entry;

import types.CampAspects;

public class Suggestion {
    private final String creatorUserId;
    private int nextSuggestionId = 0;
    private final int suggestionId;
    private final int campId;
    private String rationale;
    private Entry<CampAspects, ? extends Object> suggestionAspect;
    private boolean accepted;
    private final LocalDate creationDate;
    private LocalDate lastUpdatedDate;

    public Suggestion(String creatorUserId, int campid, String rationale,
            Entry<CampAspects, ? extends Object> suggestionAspect,
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

    public Entry<CampAspects, ? extends Object> getSuggestionAspect() {
        return this.suggestionAspect;
    }

    public boolean setSuggestionAspect(Entry<CampAspects, ? extends Object> newSuggestionAspect) {
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