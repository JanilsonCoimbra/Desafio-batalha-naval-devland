package com.rats.configs;

public class HandleLog {

    public static void title(String title) {
        
        if (!Configs.ACTIVATE_LOG) {
            return;
        }
        System.out.println(title);
        System.out.println("------------------------------------------------------------");
    }


    private HandleLog() {
    }
}
