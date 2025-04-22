package com.syslog.networkmonitor.model;

import com.syslog.networkmonitor.enums.ConnectionType;

public record EventModel(int code, String dateTime, ConnectionType type) {

    @Override
    public String toString() {
        return String.format("Código=%d, Data/Hora=%s, Tipo=%s", code, dateTime, type.getDescription());
    }
}
