package com.doljabi.Outdoor_Escape_Room.problem.application;

import com.doljabi.Outdoor_Escape_Room.problem.domain.Problem;
import com.doljabi.Outdoor_Escape_Room.problem.domain.ProblemRepository;
import com.doljabi.Outdoor_Escape_Room.problem.presentation.dto.response.ProblemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public List<ProblemResponse> getAllOrderedResponses() {
        return problemRepository.findAllByOrderByNumberAsc()
                .stream()
                .map(ProblemResponse::from)
                .toList();
    }
}
