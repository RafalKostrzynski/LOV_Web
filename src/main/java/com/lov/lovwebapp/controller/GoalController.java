package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.Goal;
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
public class GoalController {

    private GoalService goalService;
    private UserService userService;

    @Autowired
    public GoalController(GoalService goalService, UserService userService) {
        this.goalService = goalService;
        this.userService = userService;
    }

    @RequestMapping("/goals")
    public String goals(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        return "goals";
    }

    @RequestMapping(value = "goals/delete/{id}", method = RequestMethod.GET)
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "redirect:/goals";
    }


    @RequestMapping("/redirectToGoals")
    public ModelAndView redirectToGoals() {
        return new ModelAndView("redirect:/goals");
    }

    @RequestMapping("/redirectToMain")
    public ModelAndView redirectToMain() {
        return new ModelAndView("redirect:/main");
    }

    @RequestMapping("/redirectToAddGoal")
    public ModelAndView redirectToAddGoal() {
        return new ModelAndView("redirect:/addgoal");
    }

    @RequestMapping("/addgoal")
    public ModelAndView addgoal() {
        return new ModelAndView("addgoal", "goal", new Goal());
    }

    @RequestMapping("/addgoalnoactivity")
    public ModelAndView addGoalNoActivity(Model model) {
        boolean activityExist = false;
        model.addAttribute("activityExist",activityExist);
        return new ModelAndView("addgoal", "goal", new Goal());
    }

    @RequestMapping(value = "goals/editgoal/{id}", method = RequestMethod.POST)
    public String updateGoal(@PathVariable Long id, Goal goal, Principal principal) {
        goal.setUser(userService.getUserByName(principal.getName()));
        //goal.setId(id);
        goalService.updateGoal(goal);
        return "redirect:/goals";
    }


    @RequestMapping("/editgoal/{id}")
    public String editGoal(@PathVariable Long id, Model model) {
        model.addAttribute("goal", goalService.getGoal(id));
        return "editgoal";
    }

    @RequestMapping("/savegoal")
    public ModelAndView saveGoal(Goal goal, Principal principal) {
        goal.setUser(userService.getUserByName(principal.getName()));
        goalService.addGoal(goal);
        return new ModelAndView("redirect:/goals");
    }

}