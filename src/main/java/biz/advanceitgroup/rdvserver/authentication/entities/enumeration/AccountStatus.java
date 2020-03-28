package biz.advanceitgroup.rdvserver.authentication.entities.enumeration;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 16:50
 */
public enum AccountStatus {

    DISABLED(0),
    ACTIVATED(1),
    VALIDATED(2),
    UNKNOWN(3);

    private int value;

    AccountStatus(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static AccountStatus forValue(int value) {
        for (AccountStatus accountStatus : values()) {
            if (accountStatus.getValue() == value) {
                return accountStatus;
            }
        }
        return null;
    }
}
