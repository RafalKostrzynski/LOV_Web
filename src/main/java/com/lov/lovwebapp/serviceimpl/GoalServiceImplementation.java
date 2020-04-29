package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.model.Reward;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.PenaltyService;
import com.lov.lovwebapp.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImplementation implements GoalService {
    private GoalRepo goalRepo;
    private PenaltyService penaltyService;
    private RewardService rewardService;

    @Autowired
    public GoalServiceImplementation(GoalRepo goalRepo,PenaltyService penaltyService,RewardService rewardService) {
        this.goalRepo = goalRepo;
        this.penaltyService=penaltyService;
        this.rewardService=rewardService;
    }

    @Override
    public List<Goal> getAllGoals(long userId) {
        List<Goal> goalList = goalRepo.findAllByUserId(userId);
        goalList.remove(0);
        return goalList;
    }

    @Override
    public Goal getGoal(Long id) {
        Optional<Goal> goal = goalRepo.findById(id);
        return goal.orElse(null);
    }

    @Override
    public void checkGoalExpiration(User user) {
        List<Goal>goalList = goalRepo.findAllByUserId(user.getId());
        for(Goal goal:goalList){
            if(goal.getGoalEndDate().isBefore(LocalDate.now())){
                checkActiveRewardAndPenalty(goal);
                deleteGoal(goal.getId());
            }
        }
    }

    private boolean checkActiveRewardAndPenalty(Goal goal){
        boolean active=false;
        return false;

    }

    @Override
    public void addGoal(Goal goal) {
        goal.setGoalStartDate(goal.getGoalStartDate().plusDays(1));
        goal.setGoalEndDate(goal.getGoalEndDate().plusDays(1));
        goal.setFailedActivityCounter(0);
        goal.setSucceededActivityCounter(0);
        goalRepo.save(goal);
    }

    @Override
    public boolean deleteGoal(Long id) {
        if (goalRepo.findById(id).isPresent()) {
            goalRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateGoal(Goal goal) {
        addGoal(goal);
        return goalRepo.findById(goal.getId()).get().equals(goal);
    }
}
