package entities;

import java.time.LocalDate;

public class Suggestion {
    private final String creatorUserId;
    private int nextSuggestionId = 0;
    private final int suggestionId;
    private String title;
    private String description;
    private boolean accepted;
    private final LocalDate creationDate;
    private LocalDate lastUpdatedDate;

    public Suggestion(String creatorUserId, String title, String description,
            LocalDate creationDate) {
        this.creatorUserId = creatorUserId;
        this.suggestionId = nextSuggestionId++;
        this.title = title;
        this.description = description;
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

    public String getTitle() {
        return this.title;
    }

    public boolean setTitle(String newTitle) {
        if (newTitle == null || newTitle.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = newTitle;
        return true;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean setDescription(String newDescription) {
        if (newDescription == null || newDescription.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = newDescription;
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