package com.rats.services.states;

import java.util.Arrays;

import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ShotState;

public class ShotLevelTwo extends ShotState {
    @Override
    public void handleShot(String correlationId) {
         try{
                HandleLog.title("Atack: Second level shoot: " + Configs.SECOND_SET_SHOOT.toString());

                Long xAttackLong = Configs.SECOND_SET_SHOOT.get(0).get(0).longValue();
                Long yAttackLong = Configs.SECOND_SET_SHOOT.get(0).get(1).longValue();

                if (xAttackLong == null || yAttackLong == null) {
                    throw new RuntimeException("Error in second level shoot: xAttack or yAttack is null");
                    
                }
                Integer xAtack = xAttackLong.intValue();
                Integer yAtack = yAttackLong.intValue();
                Configs.SECOND_SET_SHOOT.remove(0);
                HandleLog.title("Atack: Second level shoot executed: " + Arrays.asList(xAtack, yAtack).toString());
                sendMessageShot(xAtack, yAtack, correlationId);
            } catch (Exception e) {
                HandleLog.title("Second level shoot: " + e.getMessage());
                throw new RuntimeException("Error in second level shoot");
            }
    }
    
}
