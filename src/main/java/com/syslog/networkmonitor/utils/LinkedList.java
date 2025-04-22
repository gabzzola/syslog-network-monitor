package com.syslog.networkmonitor.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LinkedList {

    private Node head;

    public LinkedList() {}

    public Node getHead() {
        return head;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node findLastNode() {
        if (isEmpty()) return null;

        Node current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    public Node addToStart(Node node) {
        if (node == null) return null;

        node.setNext(head);
        head = node;
        return node;
    }

    public Node addToEnd(Node node) {
        if (node == null) return null;

        if (isEmpty()) {
            head = node;
        } else {
            findLastNode().setNext(node);
        }
        return node;
    }

    public boolean removeByCode(int code) {
        if (isEmpty()) return false;

        if (head.getEvent().code() == code) {
            head = head.getNext();
            return true;
        }

        Node previous = head;
        Node current = head.getNext();

        while (current != null) {
            if (current.getEvent().code() == code) {
                previous.setNext(current.getNext());
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    public void printList() {
        if (isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.println(current.getEvent());
            current = current.getNext();
        }
    }

    public void exportToTxt() {
        if (isEmpty()) {
            System.out.println("Nenhum evento encontrado.");
            return;
        }

        File filePath = FileUtils.getDesktopFile("logs.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Node current = head;

            while (current != null) {
                writer.write(current.getEvent().toString());
                writer.newLine();

                current = current.getNext();
            }

            System.out.println("Logs exportados com sucesso para: " + filePath);

        } catch(IOException e) {
            System.err.println("Erro ao exportar logs: " + e.getMessage());
        }
    }
}
