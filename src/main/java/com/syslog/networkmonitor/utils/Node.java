package com.syslog.networkmonitor.utils;

import com.syslog.networkmonitor.model.EventModel;

public class Node {

    private final EventModel eventModel;
    private Node next;

    public Node(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public EventModel getEvent() {
        return eventModel;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
