package com.hdgh0g.bostongenetest.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "appeals")
@Data
public class Appeal {

    @Id
    private UUID id;
    private String text;
    private String username;
    @Enumerated(EnumType.STRING)
    private AppealStatus status;
    private Date creationDateTime;
    @OneToOne
    private AppealAnswer answer;
    @OneToOne
    private Translation translation;
}
