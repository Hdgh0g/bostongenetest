package com.hdgh0g.bostongenetest.domain;

import com.hdgh0g.bostongenetest.api.v1.requests.AppealRequest;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "appeals")
@Data
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String text;
    private String username;
    @Enumerated(EnumType.STRING)
    private AppealStatus status;
    @Column(insertable = false)
    private Date creationDateTime;
    @OneToOne(cascade = CascadeType.ALL)
    private AppealAnswer answer;
    @OneToOne(cascade = CascadeType.ALL)
    private Translation translation;

    public static Appeal fromRequest(AppealRequest appealRequest) {
        Appeal appeal = new Appeal();
        appeal.setText(appealRequest.getText());
        appeal.setStatus(AppealStatus.OPEN);
        return appeal;
    }
}
