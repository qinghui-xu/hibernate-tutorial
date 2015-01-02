package org.hibernate.tutorial;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
import org.hibernate.tutorial.util.HibernateUtil;

/**
 * Type «EventManager».
 */
public class EventManager {

    public static void main(String[] args) {
        EventManager mgr = new EventManager();

        if (args[0].equals("store")) {
            mgr.createAndStoreEvent("My Event", new Date());
        } else if (args[0].equals("list")) {
            List<Event> events = mgr.listEvents();
            for (int i = 0; i < events.size(); i++) {
                Event theEvent = events.get(i);
                System.out.println("Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate());
            }
        } else if (args[0].equals("addpersontoevent")) {
            Long eventId = mgr.createAndStoreEvent("Event to associate", new Date());
            Long personId = mgr.createAndStorePerson("Foo", "Bar", 18);
            mgr.addPersonToEvent(personId, eventId);
            System.out.println("Added person " + personId + " to event " + eventId);
        }

        HibernateUtil.getSessionFactory().close();
    }

    private Long createAndStoreEvent(String title, Date theDate) {
        Event event = newEvent(title, theDate);

        Session session = openSession();
        try {
            Long eventId = (Long) session.save(event);
            session.getTransaction().commit();
            return eventId;
        } finally {
            closeSession(session);
        }
    }

    private Event newEvent(String title, Date theDate) {
        Event event = new Event();
        event.setTitle(title);
        event.setDate(theDate);
        return event;
    }

    private Long createAndStorePerson(String firstname, String lastname, int age) {
        Person person = newPerson(firstname, lastname, age);

        Session session = openSession();
        try {
            Long personId = (Long) session.save(person);
            session.getTransaction().commit();
            return personId;
        } finally {
            closeSession(session);
        }
    }

    private Person newPerson(String firstname, String lastname, int age) {
        Person person = new Person();
        person.setAge(age);
        person.setFirstname(firstname);
        person.setLastname(lastname);
        return person;
    }

    private void addPersonToEvent(Long personId, Long eventId) {
        Session session = openSession();
        try {
            Person person = (Person) session.load(Person.class, personId);
            Event event = (Event) session.load(Event.class, eventId);
            person.getEvents().add(event);
            session.getTransaction().commit();
        } finally {
            closeSession(session);
        }
    }

    private List<Event> listEvents() {
        Session session = openSession();
        try {
            return session.createQuery("from Event").list();
        } finally {
            session.getTransaction().rollback();
        }
    }

    private static Session openSession() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return session;
    }

    private static void closeSession(Session session) {
        if (session.isOpen()) {
            session.getTransaction().rollback();
        }
    }

}
