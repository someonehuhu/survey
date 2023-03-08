package by.yatsukovich.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Response {
    private Long id;

    private User user;

    private Survey survey;

    private Timestamp completionDate;

    //seconds? unit measure must match the Survey.timeLimit
    private Long spentTime;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
