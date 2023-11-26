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
    /**
     * nextSuggestionId ensures uniqueness of next suggestion as it gets added
     */
    private static int nextSuggestionId = 0;

    /**
     * suggestionId is the unique id of the suggestion
     */
    private final int suggestionId;

    /**
     * rationale is the reason for the suggestion
     */
    private String rationale;

    /**
     * suggestionAspect is the aspect of the camp that the suggestion is for
     * 
     * @see CampAspect
     */
    private Entry<CampAspect, ? extends Object> suggestionAspect;

    /**
     * accepted is whether the suggestion has been accepted
     * if accepted, the suggestion cannot be edited
     */
    private boolean accepted;

    /**
     * creationDate is the date that the suggestion was created
     */
    private final LocalDate creationDate;

    /**
     * lastUpdateDate is the date that the suggestion was last updated
     */
    private LocalDate lastUpdatedDate;

    /**
     * Constructor for Suggestion
     * 
     * @param rationale
     * @param suggestionAspect
     * @param creationDate
     */
    public Suggestion(String rationale,
            Entry<CampAspect, ? extends Object> suggestionAspect,
            LocalDate creationDate) {
        this.suggestionId = nextSuggestionId++;
        this.rationale = rationale;
        this.suggestionAspect = suggestionAspect;
        this.accepted = false;
        this.creationDate = creationDate;
        this.lastUpdatedDate = creationDate;
    }

    /**
     * 
     * Constructor for Suggestion when reading from the suggestion_list.csv
     * Overloaded to include lastUpdateDate
     * 
     * @param suggestionId
     * @param rationale
     * @param suggestionAspect
     * @param accepted
     * @param creationDate
     * @param lastUpdatedDate
     */
    public Suggestion(int suggestionId, String rationale,
            Entry<CampAspect, ? extends Object> suggestionAspect, boolean accepted,
            LocalDate creationDate, LocalDate lastUpdatedDate) {
        this.suggestionId = suggestionId;
        this.rationale = rationale;
        this.suggestionAspect = suggestionAspect;
        this.accepted = accepted;
        this.creationDate = creationDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * When reading from the suggestions.csv, the suggestionId is already included
     * get the next suggestionId from the csv file
     * this ensures that the next suggestionId is always greater than the previous
     * and previous suggestionId will not be overwritten
     * 
     * @param nextSuggestionId
     */
    public static void setNextSuggestionId(int nextSuggestionId) {
        Suggestion.nextSuggestionId = nextSuggestionId;
    }

    /**
     * Get the suggestionId of this suggestion
     * 
     * @return the suggestionId of this suggestion
     */
    public int getSuggestionId() {
        return this.suggestionId;
    }

    /**
     * Get the suggestionAspect of this suggestion. Each suggestion corresponds only
     * to one aspect of the camp
     * 
     * @return the suggestionAspect of this suggestion
     */
    public Entry<CampAspect, ? extends Object> getSuggestionAspect() {
        return this.suggestionAspect;
    }

    /**
     * Set the suggestionAspect of this suggestion. Each suggestion corresponds only
     * to one aspect of the camp
     * 
     * @param newSuggestionAspect the new suggestionAspect to be set
     * @return true if suggestionAspect is successfully set, false otherwise
     */
    public boolean setSuggestionAspect(Entry<CampAspect, ? extends Object> newSuggestionAspect) {
        if (newSuggestionAspect == null) {
            throw new IllegalArgumentException("Suggestion aspect cannot be null or empty");
        }
        this.suggestionAspect = newSuggestionAspect;
        return true;
    }

    /**
     * Get the rationale of this suggestion
     * 
     * @return the rationale of this suggestion
     */
    public String getRationale() {
        return this.rationale;
    }

    /**
     * Set the rationale of this suggestion
     * 
     * @param newRationale the new rationale to be set
     * @return true if rationale is successfully set, false otherwise
     */
    public boolean setRationale(String newRationale) {
        if (newRationale == null || newRationale.isEmpty()) {
            throw new IllegalArgumentException("Rationale cannot be null or empty");
        }
        this.rationale = newRationale;
        return true;
    }

    /**
     * Get the accepted status of this suggestion
     * 
     * @return the accepted status of this suggestion
     */
    public boolean isAccepted() {
        return this.accepted;
    }

    /**
     * Set the accepted status of this suggestion
     * 
     * @param accepted the new accepted status to be set
     * @return true if accepted status is successfully set, false otherwise
     */
    public boolean setAccepted(boolean accepted) {
        this.accepted = accepted;
        return true;
    }

    /**
     * Get the creationDate of this suggestion
     * 
     * @return the creationDate of this suggestion
     */
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Get the lastUpdatedDate of this suggestion
     * 
     * @return the lastUpdatedDate of this suggestion
     */
    public LocalDate getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    /**
     * Set the lastUpdatedDate of this suggestion
     * 
     * @param lastUpdatedDate the new lastUpdatedDate to be set
     * @return true if lastUpdatedDate is successfully set, false otherwise
     */
    public boolean setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return true;
    }

}