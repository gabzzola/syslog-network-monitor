package com.syslog.networkmonitor.enums;

public enum ConnectionType {

    CONNECTION("Conexão"),
    DISCONNECTION("Desconexão");

    private final String description;

    ConnectionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
