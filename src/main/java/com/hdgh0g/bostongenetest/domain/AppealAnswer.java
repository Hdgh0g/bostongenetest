package com.hdgh0g.bostongenetest.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
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
}
