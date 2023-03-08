package by.yatsukovich.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum QuestionType {
    TEXT(1), DROP_DOWN_LIST(2), RADIO_BUTTON(3);

    private final Integer code;

}
