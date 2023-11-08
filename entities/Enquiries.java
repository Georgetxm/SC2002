package entities;

import java.time.LocalDate;

public class Enquiries {
    private final String creatorUserId;
    private static int nextEnquiryId = 0;
    private final int enquiryId;
    private String title;
    private String description;
    private boolean seen;
    private LocalDate creationDate;

    public Enquiries(String creatorUserId, String title, String description, boolean seen,
            LocalDate creationDate) {
        this.creatorUserId = creatorUserId;
        this.enquiryId = nextEnquiryId++;
        this.title = title;
        this.description = description;
        this.seen = seen;
        this.creationDate = creationDate;
    }

    public String getCreatorUserId() {
        return this.creatorUserId;
    }

    public int getEnquiryId() {
        return this.enquiryId;
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

    public boolean isSeen() {
        return this.seen;
    }

    public boolean setSeen(boolean seen) {
        this.seen = seen;
        return true;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public boolean updateCreationDate(LocalDate newCreationDate) {
        if (newCreationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be null");
        }
        this.creationDate = newCreationDate;
        return true;
    }

}
