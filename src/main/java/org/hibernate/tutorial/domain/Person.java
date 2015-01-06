package org.hibernate.tutorial.domain;

import java.util.Set;

/**
 * Type «Person».
 */
public class Person {

    private Long id;
    private String firstname;
    private String lastname;
    private int age;

    private Set<Event> events;
    private Set<String> emailAddresses;

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
     * @return «firstname» value.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set «firstname».
     *
     * @param firstname new value of «firstname».
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return «lastname» value.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set «lastname».
     *
     * @param lastname new value of «lastname».
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return «age» value.
     */
    public int getAge() {
        return age;
    }

    /**
     * Set «age».
     *
     * @param age new value of «age».
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return «events» value.
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Set «events».
     *
     * @param events new value of «events».
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * @return «emailAddresses» value.
     */
    public Set<String> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * Set «emailAddresses».
     *
     * @param emailAddresses new value of «emailAddresses».
     */
    public void setEmailAddresses(Set<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

}
