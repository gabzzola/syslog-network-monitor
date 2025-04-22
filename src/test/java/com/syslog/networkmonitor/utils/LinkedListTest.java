package com.syslog.networkmonitor.utils;

import com.syslog.networkmonitor.enums.ConnectionType;
import com.syslog.networkmonitor.model.EventModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LinkedListTest {

    private LinkedList list;
    private Node nodeA;
    private Node nodeB;
    private Node nodeC;

    @Before
    public void setUp() {
        list = new LinkedList();
        nodeA = createNode(1, "abr 20 12:00:00", ConnectionType.CONNECTION);
        nodeB = createNode(2, "abr 20 12:00:10", ConnectionType.DISCONNECTION);
        nodeC = createNode(3, "abr 20 12:00:20", ConnectionType.CONNECTION);
    }

    private Node createNode(int code, String dateTime, ConnectionType type) {
        return new Node(new EventModel(code, dateTime, type));
    }

    @Test
    public void deveRetornarNullQuandoAdicionarNodeNuloNoInicioDaLista() {
        Node result = list.addToStart(null);
        Assert.assertNull(result);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void deveAdicionarNodeNoInicioDaListaQuandoListaEstiverVazia() {
        Node result = list.addToStart(nodeA);
        Assert.assertEquals(nodeA, result);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void deveAdicionarMultiplosNodesNoInicioDaListaQuandoListaEstiverVazia() {
        list.addToStart(nodeA);
        list.addToStart(nodeB);
        list.addToStart(nodeC);

        Node head = list.getHead();
        Assert.assertEquals(nodeC, head);
        Assert.assertEquals(nodeB, head.getNext());
        Assert.assertEquals(nodeA, head.getNext().getNext());
    }

    @Test
    public void deveRetornarTrueQuandoAListaEstiverVazia() {
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void deveRetornarFalseQuandoAListaNaoEstiverVazia() {
        list.addToStart(nodeA);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void deveRetornarNullQuandoProcurarOUltimoNodeComListaVazia() {
        Node result = list.findLastNode();
        Assert.assertNull(result);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void deveRetornarUltimoNodeQuandoListaTiverMultiplosNodes() {
        list.addToStart(nodeA);
        list.addToStart(nodeB);
        list.addToStart(nodeC);

        Node result = list.findLastNode();
        Assert.assertEquals(nodeA, result);
    }

    @Test
    public void deveRetornarNullQuandoAdicionarNodeNuloNoFinalDaLista() {
        Node result = list.addToEnd(null);
        Assert.assertNull(result);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void deveAdicionarNodeNoFinalDaListaQuandoListaEstiverVazia() {
        Node result = list.addToEnd(nodeA);
        Assert.assertEquals(nodeA, result);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void deveAdicionarMultiplosNodesNoFinalDaListaQuandoListaEstiverVazia() {
        list.addToEnd(nodeA);
        list.addToEnd(nodeB);
        list.addToEnd(nodeC);

        Node head = list.getHead();
        Assert.assertEquals(nodeA, head);
        Assert.assertEquals(nodeB, head.getNext());
        Assert.assertEquals(nodeC, head.getNext().getNext());
    }

    @Test
    public void deveRetornarFalseQuandoTentarRemoverNodeEmListaVazia() {
        boolean result = list.removeByCode(1);
        Assert.assertFalse(result);
    }

    @Test
    public void deveRetornarFalseQuandoTentarRemoverUmNodeInexistente() {
        list.addToStart(nodeA);
        boolean result = list.removeByCode(5);
        Assert.assertFalse(result);
    }

    @Test
    public void deveRetornarTrueQuandoRemoverONodeCabecaDaLista() {
        list.addToEnd(nodeA);
        list.addToEnd(nodeB);
        list.addToEnd(nodeC);

        boolean result = list.removeByCode(1);

        Node head = list.getHead();
        Assert.assertTrue(result);
        Assert.assertEquals(nodeB, head);
        Assert.assertEquals(nodeC, head.getNext());
    }

    @Test
    public void deveRetornarTrueQuandoRemoverNodeDoMeioDaLista() {
        list.addToEnd(nodeA);
        list.addToEnd(nodeB);
        list.addToEnd(nodeC);

        boolean result = list.removeByCode(2);

        Node head = list.getHead();
        Assert.assertTrue(result);
        Assert.assertEquals(nodeA, head);
        Assert.assertEquals(nodeC, head.getNext());
    }

    @Test
    public void deveRetornarTrueQuandoRemoverUltimoNodeDaLista() {
        list.addToEnd(nodeA);
        list.addToEnd(nodeB);
        list.addToEnd(nodeC);

        boolean result = list.removeByCode(3);

        Node head = list.getHead();
        Assert.assertTrue(result);
        Assert.assertEquals(nodeA, head);
        Assert.assertEquals(nodeB, head.getNext());
    }

    @Test
    public void deveImprimirUmaMensagemQuandoAListaEstiverVazia() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        list.printList();

        System.setOut(System.out);

        String expectedOutput = "Nenhum evento encontrado.\n";

        Assert.assertEquals(expectedOutput, output.toString());

    }

    @Test
    public void deveImprimirTodosOsNodesDaLista() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        list.addToStart(nodeA);
        list.addToStart(nodeB);

        list.printList();

        System.setOut(System.out);

        String expectedOutput =
                nodeB.getEvent().toString() + System.lineSeparator() +
                nodeA.getEvent().toString() + System.lineSeparator();

        Assert.assertEquals(expectedOutput, output.toString());
    }
}
