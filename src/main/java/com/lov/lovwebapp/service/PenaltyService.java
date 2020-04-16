package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.model.Reward;

import java.util.List;

public interface PenaltyService {

    List<Penalty> getAllPenalties(long userId);

    Penalty getPenalty(Long id);

    void addPenalty(Penalty penalty);

    boolean deletePenalty(Long id);

    boolean updatePenalty(Penalty penalty);
}
