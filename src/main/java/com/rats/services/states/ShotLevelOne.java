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
                    shotRandon(correlationId);
                    }
                    xAtack = Configs.FIRST_SET_SHOOT_FIVE.get(0);
                    yAtack = Configs.FIRST_SET_SHOOT_FIVE.get(1);
                    Configs.FIRST_SET_SHOOT_FIVE.remove(0);

                    sendMessageShot(xAtack, yAtack, correlationId);
                    HandleLog.title("First level shot executed: " + xAtack + ", " + yAtack);
                    return;
                }

                if (Configs.FIRST_SET_SHOOT != null && !Configs.FIRST_SET_SHOOT.isEmpty()) {
                    Integer size = Configs.FIRST_SET_SHOOT.size();
                    int randomIndex = (int) (Math.random() * size);

                    if (Configs.FIRST_SET_SHOOT.get(randomIndex).size() < 2 || Configs.FIRST_SET_SHOOT.get(randomIndex).get(0) == null || Configs.FIRST_SET_SHOOT.get(randomIndex).get(1) == null) {
                        shotRandon(correlationId);
                    }
                    xAtack = Configs.FIRST_SET_SHOOT.get(randomIndex).get(0);
                    yAtack = Configs.FIRST_SET_SHOOT.get(randomIndex).get(1);
                    Configs.FIRST_SET_SHOOT.remove(randomIndex);
                    sendMessageShot(xAtack, yAtack, correlationId);
                    HandleLog.title("First level shot executed: " + xAtack + ", " + yAtack);
                } else {
                    shotRandon(correlationId);
                }
            } catch (Exception e) {
                HandleLog.title("First level shoot: " + e.getMessage());
                throw new FirstLevelShootException("Error in first level shoot", e);
            }
    }
}