package org.hibernate.tutorial.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.tutorial.EventManager;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.util.StringUtil;

public class EventManagerServlet extends HttpServlet {

    /** Field «serialVersionUID». */
    private static final long serialVersionUID = 5310901423996754115L;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private EventManager manager = new EventManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Event Manager</title></head><body>");

            if ("store".equals(request.getParameter("action"))) {
                String eventTitle = request.getParameter("eventTitle");
                String eventDate = request.getParameter("eventDate");
                if (StringUtil.isEmpty(eventTitle) || StringUtil.isEmpty(eventDate)) {
                    out.println("<b><i>Please enter event title and date.</i></b>");
                } else {
                    manager.createAndStoreEvent(eventTitle, dateFormat.parse(eventDate));
                    out.println("<b><i>Added event.</i></b>");
                }

                printEventForm(out);
                listEvents(out, dateFormat);
            }
        } catch (ParseException e) {
            throw new ServletException(e);
        }
    }

    private void listEvents(PrintWriter out, SimpleDateFormat dateformat2) {
        List<Event> events = manager.listEvents();
        if (events.size() > 0) {
            out.println("<h2>Events in database:</h2>");
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>Event title</th>");
            out.println("<th>Event date</th>");
            out.println("</tr>");
            for (Event event : events) {
                out.println("<td>" + event.getTitle() + "</td>");
                out.println("<td>" + dateFormat.format(event.getDate()) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    }

    private void printEventForm(PrintWriter out) {
        out.println("<h2>Add new event:</h2>");
        out.println("<form>");
        out.println("Title: <input name='eventTitle' length='50'/><br/>");
        out.println("Date (e.g. 24.12.2009): <input name='eventDate' length='10'/><br/>");
        out.println("<input type='submit' name='action' value='store'/>");
        out.println("</form>");
    }

}
