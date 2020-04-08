package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.ActivityRepo;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImplementation implements ActivityService {

    private ActivityRepo activityRepo;
    private UserService userService;

    @Autowired
    public ActivityServiceImplementation(ActivityRepo activityRepo, UserService userService) {
        this.activityRepo = activityRepo;
        this.userService = userService;
    }

    @Override
    public List<Activity> getAllActivities(long userId) {
        return activityRepo.findAllByActivityGoal_User_Id(userId);
    }

    @Override
    public Activity getActivity(long id) {
        Optional<Activity> activity = activityRepo.findById(id);
        return activity.orElse(null);
    }

    @Override
    public void addActivity(Activity activity) {
        activityRepo.save(activity);
    }

    @Override
    public boolean deleteActivity(long id) {
        if (activityRepo.findById(id).isPresent()) {
            activityRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteFailedActivity(long id) {
        Activity activity = activityRepo.findById(id).get();
        User user = userService.getUserByID(activity.getActivityGoal().getUser().getId());
        int points = user.getPoints();
        points = points-activity.getActivityPoints();
        if(points<0)points=0;
        user.setPoints(points);
        activityRepo.deleteById(id);
    }

    @Override
    public void deleteCompletedActivity(long id) {
        Activity activity = activityRepo.findById(id).get();
        User user = userService.getUserByID(activity.getActivityGoal().getUser().getId());
        int points = user.getPoints();
        user.setPoints(points+activity.getActivityPoints());
        activityRepo.deleteById(id);
    }


    @Override
    public boolean updateActivity(Activity activity) {
        addActivity(activity);
        return activityRepo.findById(activity.getId()).get().equals(activity);
    }
}
