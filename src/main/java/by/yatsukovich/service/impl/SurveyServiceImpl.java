package by.yatsukovich.service.impl;

import by.yatsukovich.domain.Mailing;
import by.yatsukovich.domain.Survey;
import by.yatsukovich.repository.SurveyRepository;
import by.yatsukovich.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;


}
