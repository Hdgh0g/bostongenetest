package com.hdgh0g.bostongenetest.domain;

import com.hdgh0g.bostongenetest.api.v1.requests.AnswerRequest;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "appeal_answers")
@Data
public class AppealAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String text;
    private String username;
    @Column(insertable = false)
    private Date creationDateTime;
    @OneToOne
    private Translation translation;

    public static AppealAnswer fromRequest(AnswerRequest request, String username) {
        AppealAnswer appealAnswer = new AppealAnswer();
        appealAnswer.setText(request.getText());
        appealAnswer.setUsername(username);
        return appealAnswer;
    }
}
