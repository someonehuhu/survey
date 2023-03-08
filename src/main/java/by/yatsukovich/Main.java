package by.yatsukovich;

import by.yatsukovich.domain.Survey;
import by.yatsukovich.domain.User;
import by.yatsukovich.repository.SurveyRepository;
import by.yatsukovich.repository.UserRepository;
import by.yatsukovich.service.SurveyService;
import by.yatsukovich.util.RandomValuesGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("by.yatsukovich");

        SurveyService surveyService = applicationContext.getBean(SurveyService.class);

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);

        SurveyRepository surveyRepository = applicationContext.getBean(SurveyRepository.class);

        RandomValuesGenerator generator = applicationContext.getBean("getRandomGenerator", RandomValuesGenerator.class);

        String mail = StringUtils.join(generator.generateRandomString(), "@mail.ru");

        User createdUser = userRepository.create(User.builder()
                .mail(mail)
                .build()
        );

        Survey survey = surveyRepository.create(Survey.builder()
                .user(createdUser)
                .shareLink(generator.generateRandomString())
                //.validityDate(new Timestamp(System.currentTimeMillis()))
                .build());
        Survey readSurvey = surveyRepository.findById(survey.getId());
        readSurvey.setShareLink(StringUtils.join("http...", generator.generateRandomString()));
        surveyRepository.update(readSurvey);
        surveyRepository.delete(readSurvey.getId());

        System.out.println(surveyRepository.findById(readSurvey.getId()));// throws exception ->OK




    }

}