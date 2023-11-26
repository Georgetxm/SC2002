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
    /**
     * nextEnquiryId ensures uniqueness of next enquiry as it gets added
     */
    private static int nextEnquiryId = 0;

    /**
     * enquiryId is the unique id of the enquiry
     */
    private final int enquiryId;

    /**
     * enquiryBody is the body of the enquiry
     */
    private String enquiryBody;

    /**
     * seen is whether the enquiry has been seen
     * if seen, the enquiry cannot be edited
     */
    private boolean seen;

    /**
     * creationDate is the date that the enquiry was created
     */
    private final LocalDate creationDate;
    /**
     * lastUpdateDate is the date that the enquiry was last updated
     */
    private LocalDate lastUpdateDate;

    /**
     * replies is the list of replies to the enquiry
     */
    private ArrayList<String> replies;

    /**
     * 
     * Constructor for Enquiry
     * 
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
     * 
     * Constructor for Enquiry when reading from the enquiry.csv
     * Overloaded to include lastUpdateDate and replies
     * 
     * @param enquiryId
     * @param enquiryBody
     * @param seen
     * @param creationDate
     * @param lastUpdateDate
     * @param replies
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
     * @param nextEnquiryId the next enquiryId to be assigned to a new enquiry
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
     * @param newEnquiryBody the new enquiryBody to be set
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
     * @param seen the new seen attribute to be set
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
     * @param lastestDate the new lastUpdateDate to be set
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
     * @param reply the reply to be added
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
     * @param reply the reply to be removed
     * @return true if the reply is successfully removed, false otherwise
     */
    public boolean removeReply(String reply) {
        if (reply == null || reply.isEmpty()) {
            throw new IllegalArgumentException("Reply cannot be null or empty");
        }
        this.replies.remove(reply);
        return true;
    }

}
