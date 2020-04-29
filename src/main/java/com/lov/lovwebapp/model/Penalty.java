package com.lov.lovwebapp.model;

import jdk.internal.jline.internal.Nullable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contents;
    private int failedInARow;
    private int failedInARowLimit;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Goal goal;

    public Penalty() {

    }

    public int getFailedInARowLimit() {
        return failedInARowLimit;
    }

    public void setFailedInARowLimit(int failedInARowLimit) {
        this.failedInARowLimit = failedInARowLimit;
    }

    public int getFailedInARow() {
        return failedInARow;
    }

    public void setFailedInARow(int failedInARow) {
        this.failedInARow = failedInARow;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }


}
