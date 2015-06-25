package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "beacon")
@Entity
public class Beacon extends AbstractNamedEntity {
    @JsonView(View.Client.class)
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private UUID uuid;

    @JsonView(View.Client.class)
    @NotNull
    private int major;

    @JsonView(View.Client.class)
    @NotNull
    private int minor;

    @JoinTable(name = "beacon_app", joinColumns = {
            @JoinColumn(name = "beacon_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "app_id", nullable = false, updatable = false)
    })
    @ManyToMany
    private Set<App> apps = new HashSet<App>();

    @JsonView(View.Sync.class)
    @ManyToMany(mappedBy = "beacons", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Event> events = new HashSet<Event>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
