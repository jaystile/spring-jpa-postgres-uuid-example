package com.uuid_guide.server.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_profile")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserProfile {

    @Id
    @Column(name = "id")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Column(name = "displayName")
    private String displayName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
