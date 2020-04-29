package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Reward;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.repo.RewardRepo;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardServiceImplementation implements RewardService {

    private RewardRepo rewardRepo;
    private GoalRepo goalRepo;

    @Autowired
    public RewardServiceImplementation(RewardRepo rewardRepo,GoalRepo goalRepo) {
        this.rewardRepo = rewardRepo;
        this.goalRepo=goalRepo;
    }

    @Override
    public List<Reward> getAllRewards(long userId) {
        return rewardRepo.findAllByGoal_User_Id(userId);
    }

    @Override
    public List<Reward> getAllActiveRewards() {
        return rewardRepo.findAllByGoal_User_Id(0);
    }

    @Override
    public List<Reward> getRewardsByGoalNameAndUserName(String goalName,long userId) {
        return rewardRepo.findAllByGoal_GoalNameAndGoal_User_Id(goalName,userId);
    }

    @Override
    public Reward getReward(Long id) {
        Optional<Reward> reward = rewardRepo.findById(id);
        return reward.orElse(null);
    }

    @Override
    public void addReward(Reward reward) {
        reward.setPercentage(0);
        rewardRepo.save(reward);
    }

    @Override
    public void setPercentage(List<Reward> rewardList,int succeededActivities, int allActivities) {
        for(Reward reward:rewardList){
            reward.setPercentage((int)(((double)succeededActivities / (double) allActivities) * 100));
            if(reward.getPercentage()>reward.getPercentageLimit())reward.setGoal(goalRepo.findById(1L).get());
        }
        rewardRepo.saveAll(rewardList);
    }

    @Override
    public boolean deleteReward(Long id) {
        if (rewardRepo.findById(id).isPresent()) {
            rewardRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReward(Reward reward) {
        addReward(reward);
        return rewardRepo.findById(reward.getId()).get().equals(reward);
    }
}
