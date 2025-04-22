package com.syslog.networkmonitor.service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SyslogConsoleUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final NetworkLogManager networkLogManager = new NetworkLogManager();

    public static void main(String[] args) {
        networkLogManager.loadNetworkEvents();

        while (true) {
            int option = displayMenu();

            switch (option) {
                case 1:
                    listEvents();
                    break;
                case 2:
                    removeEventByCode();
                    break;
                case 3:
                    exportLogs();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static int displayMenu() {
        System.out.println("\n===== MONITOR DE EVENTOS DE REDE =====");
        System.out.println("1. Listar eventos");
        System.out.println("2. Remover evento por código");
        System.out.println("3. Exportar logs para área de trabalho");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        int option = -1;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine();
        }
        return option;
    }

    private static void listEvents() {
        networkLogManager.getList().printList();
        System.out.println("Eventos carregados com sucesso!");
    }

    private static void removeEventByCode() {
        System.out.print("Informe o código do evento que deseja remover: ");
        int code = -1;
        try {
            code = scanner.nextInt();
            if (networkLogManager.getList().removeByCode(code)) {
                System.out.println("Evento removido.");
            } else {
                System.out.println("Código não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número válido.");
            scanner.nextLine();
        }
    }

    private static void exportLogs() {
        networkLogManager.getList().exportToTxt();
    }
}
