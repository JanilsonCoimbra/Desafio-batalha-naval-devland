package com.rats.services.states;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ShotState;

public class ShotLevelThird extends ShotState {
    @Override
    public void handleShot(String correlationId) {
        // Lógica para lidar com o ataque no nível 3
         try{
                if (Configs.THIRD_SET_SHOOT == null || Configs.THIRD_SET_SHOOT.isEmpty()) {
                    shootRedPointAgain(correlationId);
                }
                HandleLog.title("Atack: Third level shoot: " + Configs.THIRD_SET_SHOOT.toString());

                Long xAttackLong = Configs.THIRD_SET_SHOOT.get(0).get(0).longValue();
                Long yAttackLong = Configs.THIRD_SET_SHOOT.get(0).get(1).longValue();

                if (xAttackLong == null || yAttackLong == null) {
                    throw new RuntimeException("Error in third level shoot: xAttack or yAttack is null");
                }
                Integer xAttack = xAttackLong.intValue();
                Integer yAttack = yAttackLong.intValue();

                Configs.THIRD_SET_SHOOT.remove(0);

                if (Configs.THIRD_SET_SHOOT.isEmpty()) {
                    HandleLog.title("Atack: Third level shoot: " + Configs.THIRD_SET_SHOOT.toString());
                    throw new RuntimeException("Error in third level shoot: Configs.THIRD_SET_SHOOT is empty");
                }
                
                HandleLog.title("Atack: Random shot at coordinates executed: (" + xAttack + ", " + yAttack + ")");
                sendMessageShot(xAttack, yAttack, correlationId);
            } catch (Exception e) {
                HandleLog.title("Third level shoot: " + e.getMessage());
                throw new RuntimeException("Error in third level shoot");
            }                                                                              
    }
    
}
