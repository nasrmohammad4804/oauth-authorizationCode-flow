package com.nasr.jwtauthorizationserver.domain;

import com.nasr.jwtauthorizationserver.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    private String clientId;

    private String scope;

    @Column(columnDefinition = "TEXT")
    private String secret;

    private String redirectUri;

    @ElementCollection
    @JoinTable(name = "client_authorities")
    private Set<String> authorities;

    private long TokenValiditySeconds;

    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        TokenValiditySeconds = new Date().getTime()/1000 +tokenValiditySeconds;
    }

    private String grantType;

}
