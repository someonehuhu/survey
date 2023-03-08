package by.yatsukovich.repository;

import by.yatsukovich.domain.Response;

import java.util.List;

public interface ResponseRepository extends CRUDRepository<Long, Response>{
    List<Response> getResponsesBySurvey(Long surveyId);



}
