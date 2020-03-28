package biz.advanceitgroup.rdvserver.jobs.dto;

import java.time.ZonedDateTime;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 13/02/2020 05:14
 */
public class BidAcceptDto {

    private int confirmationTimeout;
    private String paymentMode;
    private String paymentTransID;
    private double paymentAmount;
    private ZonedDateTime acceptDate;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String codeIsoLang;

    public BidAcceptDto() {
    }

    public BidAcceptDto(int confirmationTimeout, String paymentMode, String paymentTransID, double paymentAmount, ZonedDateTime acceptDate, ZonedDateTime startDate, ZonedDateTime endDate, String codeIsoLang) {
        this.confirmationTimeout = confirmationTimeout;
        this.paymentMode = paymentMode;
        this.paymentTransID = paymentTransID;
        this.paymentAmount = paymentAmount;
        this.acceptDate = acceptDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.codeIsoLang = codeIsoLang;
    }

    public int getConfirmationTimeout() {
        return confirmationTimeout;
    }

    public void setConfirmationTimeout(int confirmationTimeout) {
        this.confirmationTimeout = confirmationTimeout;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentTransID() {
        return paymentTransID;
    }

    public void setPaymentTransID(String paymentTransID) {
        this.paymentTransID = paymentTransID;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public ZonedDateTime getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(ZonedDateTime acceptDate) {
        this.acceptDate = acceptDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCodeIsoLang() {
        return codeIsoLang;
    }

    public void setCodeIsoLang(String codeIsoLang) {
        this.codeIsoLang = codeIsoLang;
    }

    @Override
    public String toString() {
        return "BidAcceptDto{" +
                "confirmationTimeout=" + confirmationTimeout +
                ", paymentMode='" + paymentMode + '\'' +
                ", paymentTransID='" + paymentTransID + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", acceptDate=" + acceptDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", codeIsoLang='" + codeIsoLang + '\'' +
                '}';
    }
}
