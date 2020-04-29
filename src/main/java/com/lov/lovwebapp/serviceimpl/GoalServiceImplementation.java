package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.*;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalServiceImplementation implements GoalService {
    private GoalRepo goalRepo;
    private PenaltyService penaltyService;
    private ActivityService activityService;

    @Autowired
    public GoalServiceImplementation(GoalRepo goalRepo,PenaltyService penaltyService,ActivityService activityService) {
        this.goalRepo = goalRepo;
        this.penaltyService=penaltyService;
        this.activityService=activityService;
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
    public boolean checkGoalExpiration(User user) {
        List<Goal>goalList = goalRepo.findAllByUserId(user.getId());
        List<Activity> activityList = activityService.getAllActivities(user.getId());
        List<Penalty> penaltyList = penaltyService.getAllPenalties(user.getId());
        int check=0;
        for(Goal goal:goalList){
            if(goal.getGoalEndDate().isBefore(LocalDate.now())){
                check += checkActivePenalty(goal,activityList,penaltyList);
                deleteGoal(goal.getId());
            }
        }
        return check != 0;
    }

    private int checkActivePenalty(Goal goal, List<Activity> activityList,List<Penalty> penaltyList){
        List<Activity> goalsActivityList = activityList.stream().filter(e->e.getActivityGoal().equals(goal)).collect(Collectors.toList());
        List<Penalty> goalPenaltyList = penaltyList.stream().filter(e->e.getGoal().equals(goal)).collect(Collectors.toList());
        if(!goalPenaltyList.isEmpty() && !goalsActivityList.isEmpty()) {
            int failedActivity = 0;
            int penaltyIndex = 0;
            for (Activity activity : goalsActivityList) {
                failedActivity += activity.getCounter();
                Penalty penalty = goalPenaltyList.get(penaltyIndex);
                if (failedActivity > penalty.getFailedInARowLimit()) {
                    penalty.setGoal(getGoal(1L));
                    penaltyService.addPenalty(penalty);
                    penaltyIndex++;
                }
            }
            return penaltyIndex;
        }
        return 0;
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
