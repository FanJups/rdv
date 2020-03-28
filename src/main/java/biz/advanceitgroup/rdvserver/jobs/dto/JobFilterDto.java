package biz.advanceitgroup.rdvserver.jobs.dto;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 04/02/2020 14:37
 */
public class JobFilterDto {
    String lastJobCount;
    String sortBy;
    String codeIsoLang;

    public JobFilterDto() {
    }

    public JobFilterDto(String lastJobCount, String sortBy, String codeIsoLang) {
        this.lastJobCount = lastJobCount;
        this.sortBy = sortBy;
        this.codeIsoLang = codeIsoLang;
    }

    public String getLastJobCount() {
        return lastJobCount;
    }

    public void setLastJobCount(String lastJobCount) {
        this.lastJobCount = lastJobCount;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getCodeIsoLang() {
        return codeIsoLang;
    }

    public void setCodeIsoLang(String codeIsoLang) {
        this.codeIsoLang = codeIsoLang;
    }

    @Override
    public String toString() {
        return "JobLastDto{" +
                "lastJobCount='" + lastJobCount + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", codeIsoLang='" + codeIsoLang + '\'' +
                '}';
    }
}
