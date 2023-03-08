package by.yatsukovich.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private Long id;

    private String mail;

    @ToString.Exclude
    private List<Survey> surveys;

    @ToString.Exclude
    private List<Response> responses;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
