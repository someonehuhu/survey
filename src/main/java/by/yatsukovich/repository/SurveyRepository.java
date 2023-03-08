package by.yatsukovich.repository;

import by.yatsukovich.domain.Survey;

import java.sql.Timestamp;
import java.util.List;

public interface SurveyRepository extends CRUDRepository<Long, Survey> {

    List<Survey> getSurveysWithExpiringMailings(Timestamp currentDate);


}
