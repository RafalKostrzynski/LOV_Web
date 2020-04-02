package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.repo.ActivityRepo;
import com.lov.lovwebapp.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImplementation implements ActivityService {

    private ActivityRepo activityRepo;

    @Autowired
    public ActivityServiceImplementation(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
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
    public boolean updateActivity(Activity activity) {
        addActivity(activity);
        return activityRepo.findById(activity.getId()).get().equals(activity);
    }
}
