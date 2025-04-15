package com.rats.services.handlers;
import java.util.Map;
import com.rats.configs.Configs;
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
            try {
                if (payload.getEvento() == EventsEnum.LiberacaoAtaque && payload.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                    String correlationId = payload.getCorrelationId();
    
                    ShotState shotLevelOne = new ShotLevelOne();
                    ShotState shotLevelTwo = new ShotLevelTwo();
                    ShotState shotLevelThree = new ShotLevelThird();
    
                    Map<Integer, ShotState> map = Map.of(
                        0, shotLevelOne,
                        1, shotLevelTwo,
                        2, shotLevelThree
                    );
                    map.get(shipModel.getShootLevel()).handleShot(correlationId);                
                }
            } catch (Exception e) {
                throw new RuntimeException("Error in HandleAttackEnemy: " + e.getMessage());
            }

            if (nextHandler != null) {
                return nextHandler.validate(payload);
            }
            return payload;
        }
}
