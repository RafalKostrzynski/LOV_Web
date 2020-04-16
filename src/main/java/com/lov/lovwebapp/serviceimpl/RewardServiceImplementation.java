package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Reward;
import com.lov.lovwebapp.repo.RewardRepo;
import com.lov.lovwebapp.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardServiceImplementation implements RewardService {

    private RewardRepo rewardRepo;

    @Autowired
    public RewardServiceImplementation(RewardRepo rewardRepo) {
        this.rewardRepo = rewardRepo;
    }

    @Override
    public List<Reward> getAllRewards(long userId) {
        return rewardRepo.findAllByGoal_User_Id(userId);
    }

    @Override
    public Reward getReward(Long id) {
        Optional<Reward> reward = rewardRepo.findById(id);
        return reward.orElse(null);
    }

    @Override
    public void addReward(Reward reward) {
        rewardRepo.save(reward);
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
