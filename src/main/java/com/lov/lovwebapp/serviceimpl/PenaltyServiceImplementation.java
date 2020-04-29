package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.model.Reward;
import com.lov.lovwebapp.repo.PenaltyRepo;
import com.lov.lovwebapp.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenaltyServiceImplementation implements PenaltyService {

    private PenaltyRepo penaltyRepo;

    @Autowired
    public PenaltyServiceImplementation(PenaltyRepo penaltyRepo) {
        this.penaltyRepo = penaltyRepo;
    }

    @Override
    public List<Penalty> getAllPenalties(long userId) {
        return penaltyRepo.findAllByGoal_User_Id(userId);
    }

    @Override
    public List<Penalty> getAllActivePenalties() {
        return penaltyRepo.findAllByGoal_User_Id(0);
    }

    @Override
    public List<Penalty> getPenaltiesByGoalName(String goalName) {
        return penaltyRepo.findAllByGoal_GoalName(goalName);
    }

    @Override
    public Penalty getPenalty(Long id) {
        Optional<Penalty> penalty = penaltyRepo.findById(id);
        return penalty.orElse(null);
    }

    @Override
    public void addPenalty(Penalty penalty) {
        penalty.setFailedInARow(0);
        penaltyRepo.save(penalty);
    }

    @Override
    public void setFailedInARow(List<Penalty> penaltyList, boolean done) {
        for(Penalty penalty:penaltyList) {
            int value = penalty.getFailedInARowLimit();
            if (done) {
                penalty.setFailedInARow(++value);
            } else {
                if (value >= 0) penalty.setFailedInARow(0);
            }
        }
        penaltyRepo.saveAll(penaltyList);
    }

    @Override
    public boolean deletePenalty(Long id) {
        if (penaltyRepo.findById(id).isPresent()) {
            penaltyRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePenalty(Penalty penalty) {
        addPenalty(penalty);
        return penaltyRepo.findById(penalty.getId()).get().equals(penalty);
    }
}
