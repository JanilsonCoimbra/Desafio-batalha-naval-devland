package com.rats.services.handlers;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.services.ServiceBus;

public class HandleEndGame implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication payload) {
            
            if(payload.getEvento() == EventsEnum.Vitoria) {
                    HandleLog.title(" EndGame: Processing message");
                    HandleLog.title("O JOGO ACABOU!");
                    ServiceBus.close();
                }            

                if (nextHandler != null) {
                    return nextHandler.validate(payload);
                }
            
            return payload;
        }
}