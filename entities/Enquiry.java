package entities;

import java.time.LocalDate;

public class Enquiry {
    private final String creatorUserId;
    private final int campId;
    private static int nextEnquiryId = 0;
    private final int enquiryId;
    private String title;
    private String description;
    private boolean seen;
    private final LocalDate creationDate;
    private LocalDate lastUpdateDate;

    public Enquiry(String creatorUserId, int campId, String title, String description, boolean seen,
            LocalDate creationDate) {
        this.campId = campId;
        this.creatorUserId = creatorUserId;
        this.enquiryId = nextEnquiryId++;
        this.title = title;
        this.description = description;
        this.seen = seen;
        this.creationDate = creationDate;
        this.lastUpdateDate = creationDate;
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

    public LocalDate getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public boolean updateLastUpdateDate(LocalDate lastestDate) {
        this.lastUpdateDate = lastestDate;
        return true;
    }

	public int getCampId() {
		return campId;
	}

}
