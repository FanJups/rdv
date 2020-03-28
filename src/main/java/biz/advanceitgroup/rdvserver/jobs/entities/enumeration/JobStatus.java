package biz.advanceitgroup.rdvserver.jobs.entities.enumeration;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 16:55
 */
public enum JobStatus {

    WAITING(0),
    VALIDATED(1),
    BID_ACCEPTED(2),
    ATTRIBUTED(3),
    DONE(4),
    CANCELLED(5),
    CONFLICTED(6),
    REQUEST_CLOSE(7),
    UNKNOWN(8);

    private int value;

    JobStatus(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static JobStatus forValue(int value) {
        for (JobStatus jobStatus : values()) {
            if (jobStatus.getValue() == value) {
                return jobStatus;
            }
        }
        return null;
    }
}
