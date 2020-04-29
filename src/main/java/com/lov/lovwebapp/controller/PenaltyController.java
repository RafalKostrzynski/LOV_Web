package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.PenaltyService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class PenaltyController {

    private UserService userService;
    private PenaltyService penaltyService;
    private GoalService goalService;

    @Autowired
    public PenaltyController(UserService userService, PenaltyService penaltyService, GoalService goalService) {
        this.userService = userService;
        this.penaltyService = penaltyService;
        this.goalService = goalService;
    }

    @RequestMapping("/penalties")
    public String penalties(Model model, Principal principal) {
        model.addAttribute("penaltyList", penaltyService.getAllPenalties(userService.getUserByName(principal.getName()).getId()));
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "penalties";
    }

    @RequestMapping("/redirectToAddPenalty")
    public ModelAndView redirectToAddActivity(Principal principal) {
        List<Goal> goalList = goalService.getAllGoals(userService.getUserByName(principal.getName()).getId());
        if (!goalList.isEmpty()) return new ModelAndView("redirect:/addpenalty");
        return new ModelAndView("redirect:/addgoalnoactivity?warning=a_penalty&endpoint=penalty");
    }

    @RequestMapping("/addpenalty")
    public ModelAndView addPenalty(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        //model.addAttribute("goalId", 0);
        return new ModelAndView("addpenalty", "penalty", new Penalty());
    }

    @RequestMapping("/savepenalty")
    public ModelAndView savePenalty(Penalty penalty) {
        penaltyService.addNewPenalty(penalty);
        return new ModelAndView("redirect:/penalties");
    }

    @RequestMapping("/redirectToPenalties")
    public ModelAndView redirectToPenalties() {
        return new ModelAndView("redirect:/penalties");
    }

    @RequestMapping(value = "/penalties/delete/{id}", method = RequestMethod.GET)
    public String deletePenalty(@PathVariable Long id) {
        penaltyService.deletePenalty(id);
        return "redirect:/penalties";
    }

    @RequestMapping(value = "penalties/editpenalty/{id}", method = RequestMethod.POST)
    public String updateReward(@PathVariable Long id, Penalty penalty) {
        penaltyService.updatePenalty(penalty);
        return "redirect:/penalties";
    }

    @RequestMapping("/editpenalty/{id}")
    public String editReward(@PathVariable Long id, Model model,Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("penalty", penaltyService.getPenalty(id));
        return "editpenalty";
    }
}
