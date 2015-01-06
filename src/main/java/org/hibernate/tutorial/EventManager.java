package org.hibernate.tutorial;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
import org.hibernate.tutorial.util.HibernateUtil;

/**
 * Type �EventManager�.
 */
public class EventManager {

    public static void main(String[] args) {
        EventManager mgr = new EventManager();

        try {
            if (args[0].equals("store")) {
                mgr.createAndStoreEvent("My Event", new Date());
            } else if (args[0].equals("list")) {
                List<Event> events = mgr.listEvents();
                for (int i = 0; i < events.size(); i++) {
                    Event theEvent = events.get(i);
                    System.out.println("Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate());
                }
                List<Person> personList = mgr.listPeople();
                for (Person person : personList) {
                    System.out.println("Person: " + person.getId());
                }
            } else if (args[0].equals("create_person_with_event")) {
                Long eventId = mgr.createAndStoreEvent("Event to associate", new Date());
                Long personId = mgr.createAndStorePerson("Foo", "Bar", 18);
                mgr.addPersonToEvent2(personId, eventId);
                System.out.println("Added person " + personId + " to event " + eventId);
            } else if (args[0].equals("add_event_to_person")) {
                Long personId = Long.valueOf(args[1]);
                Long eventId = Long.valueOf(args[2]);
                mgr.addPersonToEvent3(personId, eventId);
            } else if (args[0].equals("add_email_to_person")) {
                Long personId = Long.valueOf(args[1]);
                mgr.addEmailToPerson(personId, args[2]);
            }
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }

    public Long createAndStoreEvent(String title, Date theDate) {
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

    public Long createAndStorePerson(String firstname, String lastname, int age) {
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

    public void addPersonToEvent(Long personId, Long eventId) {
        Session session = openSession();
        try {
            // eager fetch events with a joncture.
            Person person = (Person) session.createQuery("select p from Person p left join fetch p.events where p.id = :pid")
                    .setParameter("pid", personId).uniqueResult();
            Event event = (Event) session.load(Event.class, eventId);
            person.getEvents().add(event);
            session.getTransaction().commit();
        } finally {
            closeSession(session);
        }
    }

    public void addPersonToEvent2(Long personId, Long eventId) {
        Session session = openSession();
        Person person = null;
        Event event = null;
        try {
            person = (Person) session.createQuery("select p from Person p left join fetch p.events where p.id = :pid")
                    .setParameter("pid", personId).uniqueResult();
            event = (Event) session.load(Event.class, eventId);
        } finally {
            closeSession(session);
        }

        session = openSession();
        try {
            person.getEvents().add(event);
            session.update(person);
            session.getTransaction().commit();
        } finally {
            closeSession(session);
        }
    }

    public void addPersonToEvent3(Long personId, Long eventId) {
        Session session = openSession();
        Person person = null;
        Event event = null;
        try {
            person = (Person) session.load(Person.class, personId);
            event = (Event) session.load(Event.class, eventId);
        } finally {
            closeSession(session);
        }

        session = openSession();
        try {
            //            session.update(person);
            //            session.update(event);
            // If person is not bind to a session at first, error occurs while trying to call getEvent (on a proxy):
            // failed to lazily initialize a collection of role: org.hibernate.tutorial.domain.Person.events, could not initialize proxy - no Session
            // otherwise, if only person is bind to a new session, event is not recognized by the new session and is added into the events set event its persistent counterpart is already attached to the set. error occurs in the latter case:
            // integrity constraint violation: unique constraint or index violation; SYS_PK_10097 table: PERSON_EVENT
            person.getEvents().add(event);
            session.getTransaction().commit();
        } finally {
            closeSession(session);
        }
    }

    public List<Event> listEvents() {
        Session session = openSession();
        try {
            return session.createQuery("from Event").list();
        } finally {
            closeSession(session);
        }
    }

    public List<Person> listPeople() {
        Session session = openSession();
        try {
            return session.createQuery("from Person").list();
        } finally {
            closeSession(session);
        }
    }

    public void addEmailToPerson(Long personId, String emailAddress) {
        Session session = openSession();
        try {
            Person person = (Person) session.load(Person.class, personId);
            person.getEmailAddresses().add(emailAddress);
            session.getTransaction().commit();
        } finally {
            closeSession(session);
        }
    }

    private static Session openSession() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return session;
    }

    private static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.getTransaction().rollback();
        }
    }

}
