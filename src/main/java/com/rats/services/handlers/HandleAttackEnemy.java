package com.rats.services.handlers;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;

public class HandleAttackEnemy implements IHandleChain {
    private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {

            if (request.getEvento() == EventsEnum.LiberacaoAtaque) {
                
                System.out.println("------------------------------------------------------------");
                System.out.println("Atack: Processing message.");
                System.out.println("------------------------------------------------------------");                
            }

            if (nextHandler != null) {
                return nextHandler.validate(request);

            }
            
            return request;
        } 
}
