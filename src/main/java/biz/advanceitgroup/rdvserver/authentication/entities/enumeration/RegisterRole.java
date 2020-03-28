package biz.advanceitgroup.rdvserver.authentication.entities.enumeration;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 17:13
 */
public enum RegisterRole {

    WORKER(0),
    PROVIDER(1),
    WORKER_PROVIDER(2),
    ADMIN(3),
    UNKNOWN(4);

    private int value;

    RegisterRole(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public static RegisterRole forValue(int value) {
        for (RegisterRole registerRole : values()) {
            if (registerRole.getValue() == value) {
                return registerRole;
            }
        }
        return null;
    }
}
