package biz.advanceitgroup.rdvserver.jobs.dto;

import java.time.ZonedDateTime;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 13/02/2020 15:19
 */
public class BidConfirmDto {

    private ZonedDateTime confirmDate;
    private String paymentMode;
    private Long paymentAccount;
    private String codeIsoLang;

    public BidConfirmDto() {
    }

    public BidConfirmDto(ZonedDateTime confirmDate, String paymentMode, Long paymentAccount, String codeIsoLang) {
        this.confirmDate = confirmDate;
        this.paymentMode = paymentMode;
        this.paymentAccount = paymentAccount;
        this.codeIsoLang = codeIsoLang;
    }

    public ZonedDateTime getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(ZonedDateTime confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Long getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(Long paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getCodeIsoLang() {
        return codeIsoLang;
    }

    public void setCodeIsoLang(String codeIsoLang) {
        this.codeIsoLang = codeIsoLang;
    }

    @Override
    public String toString() {
        return "BidConfirmDto{" +
                "confirmDate=" + confirmDate +
                ", paymentMode='" + paymentMode + '\'' +
                ", paymentAccount=" + paymentAccount +
                ", codeIsoLang='" + codeIsoLang + '\'' +
                '}';
    }
}
