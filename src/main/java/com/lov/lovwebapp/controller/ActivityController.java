package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class ActivityController {

    private ActivityService activityService;
    private UserService userService;
    private GoalService goalService;
    private UserRepo userRepo;

    @Autowired
    public ActivityController(ActivityService activityService, UserService userService, GoalService goalService, UserRepo userRepo) {
        this.activityService = activityService;
        this.userService = userService;
        this.goalService = goalService;
        this.userRepo = userRepo;

        User user = new User("admin", "admin@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 0, true);

        userRepo.save(user);
////
////        Goal goal = new Goal("goalName", LocalDate.now(), LocalDate.now(), user);
////        Goal goal2 = new Goal("goalName2", LocalDate.now(), LocalDate.now(), user);
////
////        goalService.addGoal(goal);
////        goalService.addGoal(goal2);
////
////        Activity activity = new Activity("activityName", "activityUnit", goal, 5, 5);
////        Activity activity2 = new Activity("activityName2", "activityUnit2", goal2, 5, 5);
////
////        activityService.addActivity(activity);
////        activityService.addActivity(activity2);

    }



    @RequestMapping("/activities")
    public String activities(Model model, Principal principal) {
        model.addAttribute("activityList", activityService.getAllActivities(userService.getUserByName(principal.getName()).getId()));
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "activities";
    }

    @RequestMapping("/redirectToAddActivity")
    public ModelAndView redirectToAddActivity(Principal principal) {
        List<Goal>goalList = goalService.getAllGoals(userService.getUserByName(principal.getName()).getId());
        if(!goalList.isEmpty()) return new ModelAndView("redirect:/addactivity");
        return new ModelAndView("redirect:/addgoalnoactivity?warning=an_activity&endpoint=activity");
    }

    @RequestMapping("/addactivity")
    public ModelAndView addactivity(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("goalId", 0);
        return new ModelAndView("addactivity", "activity", new Activity());
    }

    @RequestMapping("/saveactivity")
    public ModelAndView saveActivity(Activity activity,Model model, Principal principal) {
        if(DAYS.between(LocalDate.now(),activity.getActivityGoal().getGoalEndDate())<7){
            model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
            model.addAttribute("goalId", 0);
            return new ModelAndView("addactivitynotaweek", "activity", new Activity());
        }
        activityService.addActivity(activity);
        return new ModelAndView("redirect:/activities");
    }

    @RequestMapping("/redirectToActivities")
    public ModelAndView redirectToActivities() {
        return new ModelAndView("redirect:/activities");
    }

    @RequestMapping(value = "/activities/delete/{id}", method = RequestMethod.GET)
    public String deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return "redirect:/activities";
    }

    @RequestMapping(value = "/activities/deleteCompleted/{id}", method = RequestMethod.GET)
    public String deleteCompletedActivity(@PathVariable Long id) {
        activityService.deleteCompletedActivity(id);
        return "redirect:/activities";
    }

    @RequestMapping(value = "/activities/deleteFailed/{id}", method = RequestMethod.GET)
    public String deleteFailedActivity(@PathVariable Long id) {
        activityService.deleteFailedActivity(id);
        return "redirect:/activities";
    }

    @RequestMapping(value = "activities/editactivity/{id}", method = RequestMethod.POST)
    public String updateActivity(@PathVariable Long id, Activity activity) {
        activityService.updateActivity(activity);
        return "redirect:/activities";
    }

    @RequestMapping("/editactivity/{id}")
    public String editActivity(@PathVariable Long id, Model model,Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("activity", activityService.getActivity(id));
        return "editactivity";
    }
}
