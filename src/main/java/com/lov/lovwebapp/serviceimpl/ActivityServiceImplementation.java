package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.ActivityRepo;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@EnableScheduling
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
        Goal activityGoal = activity.getActivityGoal();
        activity.setStartDate(LocalDate.now());
        if(activity.getFrequency().equals("daily")) {
            activity.setCounter((int) DAYS.between(LocalDate.now(), activityGoal.getGoalEndDate()));
            activity.setEndDateTime(LocalDateTime.now().plusDays(1));
            activity.setCounterString(getBeginningOfCounter(activity) + "/" + DAYS.between(activity.getStartDate(),
                    activity.getActivityGoal().getGoalEndDate()));
        }
        else if(activity.getFrequency().equals("weekly")) {
            activity.setCounter(((int)DAYS.between(LocalDate.now(), activityGoal.getGoalEndDate()))/7);
            activity.setEndDateTime(LocalDateTime.now().plusDays(7));
            activity.setCounterString(getBeginningOfCounterWeekly(activity) + "/"
                    + (DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())/7));
        }
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
        addDuplicateActivity(activity);
        setStringCounter(activity);
        User user = userService.getUserByID(activity.getActivityGoal().getUser().getId());
        int points = user.getPoints();
        points = points - activity.getActivityPoints();
        if (points < 0) points = 0;
        user.setPoints(points);
        userService.saveUser(user);
        if(activity.getCounter() <= 0)
        activityRepo.deleteById(id);
    }

    private Activity setStringCounter(Activity activity){
        if(activity.getFrequency().equals("daily")) {
            activity.setCounterString(getBeginningOfCounter(activity) + "/" + DAYS.between(activity.getStartDate(),
                    activity.getActivityGoal().getGoalEndDate()));
        }
        else if(activity.getFrequency().equals("weekly")) {
            activity.setCounterString(getBeginningOfCounterWeekly(activity) + "/"
                    + (DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())/7));
        }
        return activity;
    }

    @Override
    public void deleteCompletedActivity(long id) {
        Activity activity = activityRepo.findById(id).get();
        addDuplicateActivity(activity);
        setStringCounter(activity);
        User user = userService.getUserByID(activity.getActivityGoal().getUser().getId());
        int points = user.getPoints();
        user.setPoints(points + activity.getActivityPoints());
        userService.saveUser(user);
        if(activity.getCounter() <= 0)
        activityRepo.deleteById(id);
    }


    @Override
    public boolean updateActivity(Activity activity) {
        setStringCounter(activity);
        addActivity(activity);
        return activityRepo.findById(activity.getId()).get().equals(activity);
    }


    public void addDuplicateActivity(Activity activity) {
        int counter = activity.getCounter();
        if (counter > 0) {
            activity.setCounter(counter - 1);
            activity.setEndDateTime(activity.getEndDateTime().plusDays(1));

            //TODO gdzies to gowniane startDate sie dekrementuje
            activity.setStartDate(activity.getStartDate().plusDays(1));
            activityRepo.save(activity);
        }
    }

    @Override
    public void deleteExpiredActivity(Principal principal) {
        List<Activity> activityList = activityRepo.findAllByActivityGoal_User_Id(userService.getUserByName(principal.getName()).getId());
        for (Activity activity : activityList) {
            if (activity.getEndDateTime().isAfter(LocalDateTime.now())) {
                deleteFailedActivity(activity.getId());
            }
        }
    }

    private String getBeginningOfCounter(Activity activity) {
        long result = DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())-activity.getCounter();
        return String.valueOf(++result);
    }

    private String getBeginningOfCounterWeekly(Activity activity) {
        long result = (DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())/7)-activity.getCounter() ;
        return String.valueOf(++result);
    }
}
