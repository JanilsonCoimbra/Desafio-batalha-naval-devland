package com.rats.services.handlers;
import java.util.Arrays;
import java.util.List;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.interfaces.ShotState;
import com.rats.models.ShipModel;
import com.rats.services.states.ShotLevelOne;
import com.rats.services.states.ShotLevelThird;
import com.rats.services.states.ShotLevelTwo;

public class HandleAttackEnemy implements IHandleChain {


    private IHandleChain nextHandler;
    ShipModel shipModel = ShipModel.getShipModel();
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication payload) {

            if (payload.getEvento() == EventsEnum.LiberacaoAtaque && payload.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {

                String correlationId = payload.getCorrelationId();
                shoot(correlationId);
                
            }

            if (nextHandler != null) {
                return nextHandler.validate(payload);
            }
            return payload;
        }

        private void shoot(String correlationId) {
                ShotState shotLevelOne = new ShotLevelOne();
                ShotState shotLevelTwo = new ShotLevelTwo();
                ShotState shotLevelThree = new ShotLevelThird();
                
                if (shipModel.getShootLevel() == 0) {
                    shotLevelOne.handleShot(correlationId);
                } else if (shipModel.getShootLevel() == 1) {
                    shotLevelTwo.handleShot(correlationId);
                } else if (shipModel.getShootLevel() == 2) {
                    shotLevelThree.handleShot(correlationId);
                }
        }

        private List<Integer> firstLevelShoot() {
            Integer xAtack;
            Integer yAtack;
            
            try {
                if (Configs.FIRST_SET_SHOOT_FIVE != null && !Configs.FIRST_SET_SHOOT_FIVE.isEmpty()) {
                    xAtack = Configs.FIRST_SET_SHOOT_FIVE.get(0).intValue();
                    yAtack = Configs.FIRST_SET_SHOOT_FIVE.get(1).intValue();
                    Configs.FIRST_SET_SHOOT_FIVE.remove(0);

                    return Arrays.asList(xAtack, yAtack);
                }
                xAtack = Configs.FIRST_SET_SHOOT.get(0).get(0);
                yAtack = Configs.FIRST_SET_SHOOT.get(0).get(1);
                Configs.FIRST_SET_SHOOT.remove(0);
                return Arrays.asList(xAtack, yAtack);
            } catch (Exception e) {
                HandleLog.title("First level shoot: " + e.getMessage());
                throw new RuntimeException("Error in first level shoot");
            }
        }

        private List<Integer> secondLevelShoot() {
            try{
                HandleLog.title("Atack: Second level shoot: " + Configs.SECOND_SET_SHOOT.toString());

                Long xAttackLong = Configs.SECOND_SET_SHOOT.get(0).get(0).longValue();
                Long yAttackLong = Configs.SECOND_SET_SHOOT.get(0).get(1).longValue();

                if (xAttackLong == null || yAttackLong == null) {
                    throw new RuntimeException("Error in second level shoot: xAttack or yAttack is null");
                    
                }
                //Long ton Integer
                Integer xAtack = xAttackLong.intValue();
                Integer yAtack = yAttackLong.intValue();
                Configs.SECOND_SET_SHOOT.remove(0);
                return Arrays.asList(xAtack, yAtack);
            } catch (Exception e) {
                HandleLog.title("Second level shoot: " + e.getMessage());
                throw new RuntimeException("Error in second level shoot");
            }
        }

        private List<Integer> thirdLevelShoot() {
            try{
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
                return Arrays.asList(xAttack, yAttack);
            } catch (Exception e) {
                HandleLog.title("Third level shoot: " + e.getMessage());
                throw new RuntimeException("Error in third level shoot");
            }
        }
}
