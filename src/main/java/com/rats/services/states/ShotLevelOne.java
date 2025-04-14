package com.rats.services.states;

import java.util.Arrays;

import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ShotState;

public class ShotLevelOne extends ShotState {
    @Override
    public void handleShot(String correlationId) {
        // Lógica para lidar com o ataque no nível 1
            Integer xAtack;
            Integer yAtack;
            
            try {
                if (Configs.FIRST_SET_SHOOT_FIVE != null && !Configs.FIRST_SET_SHOOT_FIVE.isEmpty()) {
                    xAtack = Configs.FIRST_SET_SHOOT_FIVE.get(0).intValue();
                    yAtack = Configs.FIRST_SET_SHOOT_FIVE.get(1).intValue();
                    Configs.FIRST_SET_SHOOT_FIVE.remove(0);

                    sendMessageShot(xAtack, yAtack, correlationId);
                    HandleLog.title("First level shot executed: " + xAtack + ", " + yAtack);
                    return;
                }
                xAtack = Configs.FIRST_SET_SHOOT.get(0).get(0);
                yAtack = Configs.FIRST_SET_SHOOT.get(0).get(1);
                Configs.FIRST_SET_SHOOT.remove(0);
                HandleLog.title("First level shot executed: " + xAtack + ", " + yAtack);
            } catch (Exception e) {
                HandleLog.title("First level shoot: " + e.getMessage());
                throw new RuntimeException("Error in first level shoot");
            }
            sendMessageShot(xAtack, yAtack, correlationId);
    }
    
}
