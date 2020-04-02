package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.Goal;
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

@Controller
public class ActivityController {

    private ActivityService activityService;
    private UserService userService;
    private GoalService goalService;

    @Autowired
    public ActivityController(ActivityService activityService, UserService userService, GoalService goalService) {
        this.activityService = activityService;
        this.userService = userService;
        this.goalService = goalService;
    }

    @RequestMapping("/activities")
    public String goals(Model model, Principal principal) {
        model.addAttribute("activityList", activityService.getAllActivities(userService.getUserByName(principal.getName()).getId()));
        return "activities";
    }

    @RequestMapping("/redirectToAddActivity")
    public ModelAndView redirectToAddActivity() {
        return new ModelAndView("redirect:/addactivity");
    }

    @RequestMapping("/addactivity")
    public ModelAndView addactivity(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("goalId", 0);
        return new ModelAndView("addactivity", "activity", new Activity());
    }

    @RequestMapping("/saveactivity")
    public ModelAndView saveActivity(Activity activity, Principal principal) {
//        goal.setUser(userService.getUserByName(principal.getName()));
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
}
