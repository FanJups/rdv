package biz.advanceitgroup.rdvserver.jobs.entities;


import javax.persistence.*;

import biz.advanceitgroup.rdvserver.commons.entities.AbstractEntityModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 31/01/2020 16:01
 */

@Entity
// @Table(name = "job")
@AttributeOverride(name="id",column=@Column(name="categoryID"))
public class Category extends AbstractEntityModel {

    private static final long serialVersionUID = 1L;

    @Column
    private String name_En;
    @Column
    private String description_En;
    @Column
    private String name_Fr;
    @Column
    private String description_Fr;
    @Column
    private boolean requireEval;
    @Column
    private String evalQuizzCount;
    @Column
    private Long evalDuration;
    @Column
    private double evalMinAverage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
    private Set<Job> jobs = new HashSet<>();

    public Category() {
        super();
    }

    public Category(Long categoryId) {
        super();
        setId(categoryId);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName_En() {
        return name_En;
    }

    public void setName_En(String name_En) {
        this.name_En = name_En;
    }

    public String getDescription_En() {
        return description_En;
    }

    public void setDescription_En(String description_En) {
        this.description_En = description_En;
    }

    public String getName_Fr() {
        return name_Fr;
    }

    public void setName_Fr(String name_Fr) {
        this.name_Fr = name_Fr;
    }

    public String getDescription_Fr() {
        return description_Fr;
    }

    public void setDescription_Fr(String description_Fr) {
        this.description_Fr = description_Fr;
    }

    public boolean getRequireEval() {
        return requireEval;
    }

    public void setRequireEval(boolean requireEval) {
        this.requireEval = requireEval;
    }

    public String getEvalQuizzCount() {
        return evalQuizzCount;
    }

    public void setEvalQuizzCount(String evalQuizzCount) {
        this.evalQuizzCount = evalQuizzCount;
    }

    public Long getEvalDuration() {
        return evalDuration;
    }

    public void setEvalDuration(Long evalDuration) {
        this.evalDuration = evalDuration;
    }

    public double getEvalMinAverage() {
        return evalMinAverage;
    }

    public void setEvalMinAverage(double evalMinAverage) {
        this.evalMinAverage = evalMinAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return requireEval == category.requireEval &&
                Double.compare(category.evalMinAverage, evalMinAverage) == 0 &&
                name_En.equals(category.name_En) &&
                description_En.equals(category.description_En) &&
                name_Fr.equals(category.name_Fr) &&
                description_Fr.equals(category.description_Fr) &&
                evalQuizzCount.equals(category.evalQuizzCount) &&
                evalDuration.equals(category.evalDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name_En, description_En, name_Fr, description_Fr, requireEval, evalQuizzCount, evalDuration, evalMinAverage);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name_En='" + name_En + '\'' +
                ", description_En='" + description_En + '\'' +
                ", name_Fr='" + name_Fr + '\'' +
                ", description_Fr='" + description_Fr + '\'' +
                ", requireEval=" + requireEval +
                ", evalQuizzCount='" + evalQuizzCount + '\'' +
                ", evalDuration=" + evalDuration +
                ", evalMinAverage=" + evalMinAverage +
                '}';
    }
}
