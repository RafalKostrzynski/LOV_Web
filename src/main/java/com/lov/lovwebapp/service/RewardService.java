package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Reward;

import java.util.List;

public interface RewardService {

    List<Reward> getAllRewards(long userId);

    Reward getReward(Long id);

    void addReward(Reward reward);

    boolean deleteReward(Long id);

    boolean updateReward(Reward reward);
}
