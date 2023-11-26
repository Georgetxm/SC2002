package entities;

import java.util.TreeMap;

import types.CampAspect;

/**
 * Represents the CampInfo record class
 * Automatically checks if the CampInfo object is complete
 * CampInfo object is complete if it contains all the CampAspect ENUM values
 * CampInfo object is incomplete if it does not contain all the CampAspect ENUM
 * values
 * 
 * @see CampAspect
 * @param info the TreeMap containing the Camp's information
 * 
 * @author Teo Xuan Ming
 * @version 1.1
 * @since 2021-11-24
 */
public record CampInfo(TreeMap<CampAspect, Object> info) {
    /**
     * Constructor for CampInfo
     * 
     * @param info the TreeMap containing the Camp's information
     */
    public CampInfo {
        if (info.size() != CampAspect.values().length)
            throw new java.lang.IllegalArgumentException("CampInfo not complete");
    }
}
