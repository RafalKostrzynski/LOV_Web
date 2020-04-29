package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Reward;

import java.util.List;

public interface RewardService {

    List<Reward> getAllRewards(long userId);

    List<Reward> getAllActiveRewards();

    List<Reward> getRewardsByGoalName(String goalName);

    Reward getReward(Long id);

    void addReward(Reward reward);

    void setPercentage(List<Reward> rewardList, int allActivities, int succeededActivities);

    boolean deleteReward(Long id);

    boolean updateReward(Reward reward);

}
