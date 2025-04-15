package com.rats.services.states;

import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ShotState;

public class ShotLevelOne extends ShotState {
    @Override
    public void handleShot(String correlationId) throws FirstLevelShootException {
            Integer xAtack;
            Integer yAtack;
            
            try {
                if (Configs.FIRST_SET_SHOOT_FIVE != null && !Configs.FIRST_SET_SHOOT_FIVE.isEmpty()) {
                    if (Configs.FIRST_SET_SHOOT_FIVE.size() < 2 || Configs.FIRST_SET_SHOOT_FIVE.get(0) == null || Configs.FIRST_SET_SHOOT_FIVE.get(1) == null) {
                        throw new IllegalArgumentException("Invalid data in FIRST_SET_SHOOT_FIVE");
                    }
                    xAtack = Configs.FIRST_SET_SHOOT_FIVE.get(0);
                    yAtack = Configs.FIRST_SET_SHOOT_FIVE.get(1);
                    Configs.FIRST_SET_SHOOT_FIVE.remove(0);

                    sendMessageShot(xAtack, yAtack, correlationId);
                    HandleLog.title("First level shot executed: " + xAtack + ", " + yAtack);
                    return;
                }
                
                if (Configs.FIRST_SET_SHOOT != null && !Configs.FIRST_SET_SHOOT.isEmpty()) {
                    if (Configs.FIRST_SET_SHOOT.get(0).size() < 2 || Configs.FIRST_SET_SHOOT.get(0).get(0) == null || Configs.FIRST_SET_SHOOT.get(0).get(1) == null) {
                        throw new IllegalArgumentException("Invalid data in FIRST_SET_SHOOT");
                    }
                    xAtack = Configs.FIRST_SET_SHOOT.get(0).get(0);
                    yAtack = Configs.FIRST_SET_SHOOT.get(0).get(1);
                    Configs.FIRST_SET_SHOOT.remove(0);
                    sendMessageShot(xAtack, yAtack, correlationId);
                    HandleLog.title("First level shot executed: " + xAtack + ", " + yAtack);
                } else {
                    throw new IllegalStateException("No data available for shooting");
                }
            } catch (Exception e) {
                HandleLog.title("First level shoot: " + e.getMessage());
                throw new FirstLevelShootException("Error in first level shoot", e);
            }
    }
    
}

// Removendo a classe pública duplicada
// A classe FirstLevelShootException foi movida para um arquivo separado.
