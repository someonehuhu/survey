package by.yatsukovich.domain.enums.convert;

import by.yatsukovich.domain.enums.QuestionType;

import java.util.stream.Stream;
public class QuestionTypeConverter {

    public QuestionType convertToQuestionType(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(QuestionType.values())
                .filter(questionType -> questionType.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
