package com.ratos.services.handlers;

import com.ratos.interfaces.EventsEnum;
import com.ratos.interfaces.IComunication;
import com.ratos.interfaces.IHandleChain;

public class HandleAttackEnemy implements IHandleChain {
    private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public IComunication validate(IComunication request) {

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
