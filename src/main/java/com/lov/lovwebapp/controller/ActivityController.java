package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.*;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class ActivityController {

    private ActivityService activityService;
    private UserService userService;
    private GoalService goalService;
    private UserRepo userRepo;
    private RewardService rewardService;
    private PenaltyService penaltyService;

    @Autowired
    public ActivityController(ActivityService activityService, UserService userService, GoalService goalService, UserRepo userRepo,RewardService rewardService,PenaltyService penaltyService) {
        this.activityService = activityService;
        this.userService = userService;
        this.goalService = goalService;
        this.userRepo = userRepo;
        this.rewardService=rewardService;
        this.penaltyService=penaltyService;

        User user = new User("admin", "admin@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 1, true);

        User user2 = new User("admin2", "admin2@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 980, true);

        User user3 = new User("admin3", "admin3@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 521, true);

        User user4 = new User("admin4", "admin4@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 1500, true);

        User user5 = new User("admin5", "admin5@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 17, true);

        User user6 = new User("admin6", "admin6@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 767, true);

        User user7 = new User("admin7", "admin7@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 417, true);

        User user8 = new User("admin8", "admin8@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 217, true);

        User user9 = new User("admin9", "admin9@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 327, true);

        User user10 = new User("admin10", "admin10@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 71, true);

        User user11 = new User("admin11", "admin11@wp.pl",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m",
                "$2y$10$JpNwyaj/Hl8oklQDx9pewu8Tyi9TgH5UfPUIeB4biIE3st7dGi60m", 2, true);

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
        if(activity.getFrequency().equals("weekly") && DAYS.between(LocalDate.now(),activity.getActivityGoal().getGoalEndDate())<7){
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
    public String updateActivity(@PathVariable Long id, Activity activity,Model model, Principal principal) {
        if(activity.getFrequency().equals("weekly") && DAYS.between(LocalDate.now(),activity.getActivityGoal().getGoalEndDate())<7){
            model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
            model.addAttribute("activity", activityService.getActivity(id));
            return "editactivityfail";
        }
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
