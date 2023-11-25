package entities;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents an Enquiry Object
 * 
 * A Enquiry has the base attributres of a campId, enquiryBody
 * seen (whether the enquiry has been seen, if seen, cannot be edited),
 * creationDate and lastUpdatedDate
 * Each enquiry has a unique enquiryId which is an auto-incremented
 * static attribute
 * 
 * @author Teo Xuan Ming
 * @version 1.0
 * @since 2021-11-12
 */
public class Enquiry {
    private static int nextEnquiryId = 0;
    private final int enquiryId;
    private String enquiryBody;
    private boolean seen;
    private final LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private ArrayList<String> replies;

    /**
     * Constructor for Enquiry
     * 
     * @param creatorUserId
     * @param campId
     * @param enquiryBody
     * @param seen
     * @param creationDate
     */
    public Enquiry(String enquiryBody, boolean seen,
            LocalDate creationDate) {
        this.enquiryId = nextEnquiryId++;
        this.enquiryBody = enquiryBody;
        this.seen = seen;
        this.creationDate = creationDate;
        this.lastUpdateDate = creationDate;
        this.replies = new ArrayList<String>();
    }

    /**
     * Constructor for Enquiry when reading from the enquiry.csv
     * Overloaded to include lastUpdateDate
     * 
     * @param creatorUserId
     * @param campId
     * @param enquiryBody
     * @param seen
     * @param creationDate
     * @param lastUpdatedDate
     */
    public Enquiry(int enquiryId, String enquiryBody, boolean seen,
            LocalDate creationDate, LocalDate lastUpdateDate, ArrayList<String> replies) {
        this.enquiryId = enquiryId;
        this.enquiryBody = enquiryBody;
        this.seen = seen;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.replies = replies;
    }

    /**
     * When reading from the enquiries.csv, the enquiryId is already included
     * get the next enquiryId from the csv file
     * this ensures that the next enquiryId is always greater than the previous
     * and previous enquiryId will not be overwritten
     * 
     * @param nextEnquiryId
     */
    public static void setNextEnquiryId(int nextEnquiryId) {
        Enquiry.nextEnquiryId = nextEnquiryId;
    }

    /**
     * Get the enquiryId of this enquiry
     * 
     * @return the enquiryId of this enquiry
     */
    public int getEnquiryId() {
        return this.enquiryId;
    }

    /**
     * Get the enquiryBody of this enquiry
     * 
     * @return the enquiryBody of this enquiry
     */
    public String getEnquiryBody() {
        return this.enquiryBody;
    }

    /**
     * Set the enquiryBody of this enquiry
     * 
     * @param newEnquiryBody
     * @return true if the enquiryBody is successfully set, false otherwise
     */
    public boolean setEnquiryBody(String newEnquiryBody) {
        if (newEnquiryBody == null || newEnquiryBody.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.enquiryBody = newEnquiryBody;
        return true;
    }

    /**
     * Get the seen attribute, whether the enquiry has been seen
     * if seen, the enquiry cannot be edited
     * 
     * @return true if the enquiry has been seen, false otherwise
     */
    public boolean isSeen() {
        return this.seen;
    }

    /**
     * Set the seen attribute of this enquiry
     * 
     * @param seen
     * @return true if the seen attribute is successfully set, false otherwise
     */
    public boolean setSeen(boolean seen) {
        this.seen = seen;
        return true;
    }

    /**
     * Get the creationDate of this enquiry
     * 
     * @return the creationDate of this enquiry
     */
    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    /**
     * Get the lastUpdateDate of this enquiry
     * 
     * @return the lastUpdateDate of this enquiry
     */
    public LocalDate getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * Set the lastUpdateDate of this enquiry
     * 
     * @param lastestDate
     * @return true if the lastUpdateDate is successfully set, false otherwise
     */
    public boolean updateLastUpdateDate(LocalDate lastestDate) {
        this.lastUpdateDate = lastestDate;
        return true;
    }

    /**
     * Get the replies of this enquiry
     * 
     * @return the replies of this enquiry
     */
    public ArrayList<String> getReplies() {
        return this.replies;
    }

    /**
     * Add a reply to this enquiry
     * 
     * @param reply
     * @return true if the reply is successfully added, false otherwise
     */
    public boolean addReply(String reply) {
        if (reply == null || reply.isEmpty()) {
            throw new IllegalArgumentException("Reply cannot be null or empty");
        }
        this.replies.add(reply);
        return true;
    }

    /**
     * Remove a reply from this enquiry
     * 
     * @param reply
     * @return
     */
    public boolean removeReply(String reply) {
        if (reply == null || reply.isEmpty()) {
            throw new IllegalArgumentException("Reply cannot be null or empty");
        }
        this.replies.remove(reply);
        return true;
    }

}
