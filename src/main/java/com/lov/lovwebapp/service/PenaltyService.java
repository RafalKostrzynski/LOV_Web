package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.model.Reward;

import java.util.List;

public interface PenaltyService {

    List<Penalty> getAllPenalties(long userId);

    List<Penalty> getAllActivePenalties();

    List<Penalty> getPenaltiesByGoalName(String goalName);

    Penalty getPenalty(Long id);

    void addPenalty(Penalty penalty);

    void setFailedInARow(List<Penalty> penalty, boolean done);

    boolean deletePenalty(Long id);

    boolean updatePenalty(Penalty penalty);

}
