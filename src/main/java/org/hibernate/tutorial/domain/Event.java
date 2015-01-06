package org.hibernate.tutorial.domain;

import java.util.Date;
import java.util.Set;

/**
 * Type «Event».
 */
public class Event {
    private Long id;

    private String title;
    private Date date;

    private Set<Person> participants;

    /**
     * @return «id» value.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set «id».
     *
     * @param id new value of «id».
     */
    private void setId(Long id) {
        this.id = id;
    }

    /**
     * @return «title» value.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set «title».
     *
     * @param title new value of «title».
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return «date» value.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set «date».
     *
     * @param date new value of «date».
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return «participants» value.
     */
    public Set<Person> getParticipants() {
        return participants;
    }

    /**
     * Set «participants».
     *
     * @param participants new value of «participants».
     */
    public void setParticipants(Set<Person> participants) {
        this.participants = participants;
    }

}
