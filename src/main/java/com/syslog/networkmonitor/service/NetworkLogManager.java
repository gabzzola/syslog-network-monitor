package com.syslog.networkmonitor.service;

import com.syslog.networkmonitor.enums.ConnectionType;
import com.syslog.networkmonitor.model.EventModel;
import com.syslog.networkmonitor.utils.LinkedList;
import com.syslog.networkmonitor.utils.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetworkLogManager {

    private static final String COMMAND = "journalctl -u NetworkManager";
    private final LinkedList networkEventList;

    public NetworkLogManager() {
        this.networkEventList = new LinkedList();
    }

    public LinkedList getList() {
        return networkEventList;
    }

    public void loadNetworkEvents() {
        int code = 1;
        ConnectionType lastType = null;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(COMMAND.split(" "));
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("NetworkManager")) continue;

                String[] parts = line.split(" ", 4);
                if (parts.length < 4) continue;
                String dateTime = parts[0] + " " + parts[1] + " " + parts[2];

                ConnectionType type = null;
                if (line.contains("Activation: successful")) {
                    type = ConnectionType.CONNECTION;
                } else if (line.contains("state change: activated -> unavailable") || line.contains("state change: unavailable -> disconnected")) {
                    type = ConnectionType.DISCONNECTION;
                }

                if (type != null && type != lastType) {
                    EventModel event = new EventModel(code++, dateTime, type);
                    networkEventList.addToEnd(new Node(event));
                    lastType = type;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar eventos: " + e.getMessage());
        }
    }
}
