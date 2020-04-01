package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Goal;

import java.util.List;

public interface GoalService {
    List<Goal> getAllGoals(long userId);
    Goal getGoal(Long id);
    void addGoal(Goal goal);
    boolean deleteGoal(Long id);
    boolean updateGoal(Goal goal);
}
