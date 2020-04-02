package com.lov.lovwebapp.model;

import javax.persistence.*;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String activityName;
    private String activityUnit;
    @ManyToOne
    private Goal activityGoal;
    private int activityPoints;
    private int activityAmount;

    public Activity(String activityName, String activityUnit, Goal activityGoal, int activityPoints, int activityAmount) {
        this.activityName = activityName;
        this.activityUnit = activityUnit;
        this.activityGoal = activityGoal;
        this.activityPoints = activityPoints;
        this.activityAmount = activityAmount;
    }

    public Activity(long id, String activityName, String activityUnit, Goal activityGoal, int activityPoints, int activityAmount) {
        this.id = id;
        this.activityName = activityName;
        this.activityUnit = activityUnit;
        this.activityGoal = activityGoal;
        this.activityPoints = activityPoints;
        this.activityAmount = activityAmount;
    }

    public Activity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityUnit() {
        return activityUnit;
    }

    public void setActivityUnit(String activityUnit) {
        this.activityUnit = activityUnit;
    }

    public Goal getActivityGoal() {
        return activityGoal;
    }

    public void setActivityGoal(Goal goal) {
        this.activityGoal = goal;
    }

    public int getActivityPoints() {
        return activityPoints;
    }

    public void setActivityPoints(int activityPoints) {
        this.activityPoints = activityPoints;
    }

    public int getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(int activityAmount) {
        this.activityAmount = activityAmount;
    }
}
