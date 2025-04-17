package com.rats.services;

import java.util.Arrays;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import com.rats.configs.Configs;

public class NonBlockingKeyListenerService {

    public NonBlockingKeyListenerService() {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            NonBlockingReader reader = terminal.reader();

            System.out.println("Pressione uma tecla (1-6) ou 'q' para sair:");

            while (true) {
                int key = reader.read();

                if (key == 'q' || key == 'Q') {
                    System.out.println("Saindo do programa...");
                    break;
                }

                switch (key) {
                    case '1':
                        executeFunction1();
                        break;
                    case '2':
                        executeFunction2();
                        break;
                    case '3':
                        executeFunction3();
                        break;
                    case '4':
                        executeFunction4();
                        break;
                    case '5':
                        executeFunction5();
                        break;
                    case '6':
                        executeFunction6();
                        break;
                }
            }

            reader.close();
            terminal.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeFunction1() {
        System.out.println("Função 1 executada");
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(11, 4));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(28, 4));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(4, 11));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(21, 9));
    }

    private void executeFunction2() {
        System.out.println("Função 2 executada");
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(39, 27));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(56, 27));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(47, 20));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(64, 20));
    }

    private void executeFunction3() {
        System.out.println("Função 3 executada");
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(81, 20));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(98, 20));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(73, 27));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(90, 27));
    }

    private void executeFunction4() {
        System.out.println("Função 4 executada");
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(13, 20));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(30, 20));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(5, 27));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(22, 27));
    }

    private void executeFunction5() {
        System.out.println("Função 5 executada");
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(45, 4));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(62, 4));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(38, 9));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(55, 9));
    }

    private void executeFunction6() {
        System.out.println("Função 6 executada");
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(72, 9));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(89, 9));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(79, 4));
        Configs.FIRST_SET_SHOOT_DIRECTED.add(Arrays.asList(96, 4));
    }
}