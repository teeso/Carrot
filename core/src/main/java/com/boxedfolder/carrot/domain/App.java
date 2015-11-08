package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.general.AbstractNamedEntity;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "app")
@Entity
public class App extends AbstractNamedEntity {
    @JsonIgnore
    @Column(name = "app_key")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID applicationKey;

    @ManyToMany(mappedBy = "apps", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "app", cascade = CascadeType.REMOVE)
    private Set<AnalyticsLog> logs;

    @JsonView(View.General.class)
    public UUID getApplicationKey() {
        return applicationKey;
    }

    @JsonIgnore
    public void setApplicationKey(UUID applicationKey) {
        this.applicationKey = applicationKey;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
