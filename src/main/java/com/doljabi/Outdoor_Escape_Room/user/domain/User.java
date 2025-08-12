package com.doljabi.Outdoor_Escape_Room.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"USER\"")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String externalId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public User(Provider provider, String externalId){
        this.name = "guest";
        this.provider = provider;
        this.externalId = externalId;
    }
}
