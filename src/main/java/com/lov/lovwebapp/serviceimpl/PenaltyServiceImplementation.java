package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Penalty;
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
    public Penalty getPenalty(Long id) {
        Optional<Penalty> penalty = penaltyRepo.findById(id);
        return penalty.orElse(null);
    }

    @Override
    public void addPenalty(Penalty penalty) {
        penaltyRepo.save(penalty);
    }

    @Override
    public boolean deletePenalty(Long id) {
        return false;
    }

    @Override
    public boolean updatePenalty(Penalty penalty) {
        return false;
    }
}
