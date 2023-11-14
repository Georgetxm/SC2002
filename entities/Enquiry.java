package entities;

import java.time.LocalDate;

public class Enquiry {
    private final String creatorUserId;
    private final int campId;
    private static int nextEnquiryId = 0;
    private final int enquiryId;
    private String enquiryBody;
    private boolean seen;
    private final LocalDate creationDate;
    private LocalDate lastUpdateDate;

    public Enquiry(String creatorUserId, int campId, String enquiryBody, boolean seen,
            LocalDate creationDate) {
        this.campId = campId;
        this.creatorUserId = creatorUserId;
        this.enquiryId = nextEnquiryId++;
        this.enquiryBody = enquiryBody;
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

    public String getEnquiryBody() {
        return this.enquiryBody;
    }

    public boolean setDescription(String newEnquiryBody) {
        if (newEnquiryBody == null || newEnquiryBody.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.enquiryBody = newEnquiryBody;
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
