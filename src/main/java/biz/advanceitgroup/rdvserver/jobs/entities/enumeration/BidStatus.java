package biz.advanceitgroup.rdvserver.jobs.entities.enumeration;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 16:52
 */
public enum BidStatus {

    WAITING(0),
    ACCEPTED(1),
    ATTRIBUTED(2),
    CLOSED(3),
    CANCELLED(4),
    UNKNOWN(5);

    private int value;

    BidStatus(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static BidStatus forValue(int value) {
        for (BidStatus bidStatus : values()) {
            if (bidStatus.getValue() == value) {
                return bidStatus;
            }
        }
        return null;
    }
}
