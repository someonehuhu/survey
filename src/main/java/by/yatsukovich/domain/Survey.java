package by.yatsukovich.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Survey {
    private Long id;

    private User user;

    @ToString.Exclude
    private List<Response> responses;

    @ToString.Exclude
    private List<Mailing> mailings;

    private String shareLink;

    private String accessCodeword;

    private Timestamp validityDate;

    private Long timeLimit;//seconds???

    private Integer respondersLimit;//2^31 enough?

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
