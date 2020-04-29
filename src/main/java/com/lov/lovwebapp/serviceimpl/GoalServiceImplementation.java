package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImplementation implements GoalService {
    private GoalRepo goalRepo;

    @Autowired
    public GoalServiceImplementation(GoalRepo goalRepo) {
        this.goalRepo = goalRepo;
    }

    @Override
    public List<Goal> getAllGoals(long userId) {
        return goalRepo.findAllByUserId(userId);
    }

    @Override
    public Goal getGoal(Long id) {
        Optional<Goal> goal = goalRepo.findById(id);
        return goal.orElse(null);
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
