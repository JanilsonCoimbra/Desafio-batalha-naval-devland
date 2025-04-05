package com.ratos.services.handlers;

import com.ratos.interfaces.EventsEnum;
import com.ratos.interfaces.IComunication;
import com.ratos.interfaces.IHandleChain;

public class HandleCripto implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public IComunication validate(IComunication request) {
                System.out.println("------------------------------------------------------------");
                System.out.println("Cripto: Processing message.");
                System.out.println("------------------------------------------------------------");


                if (nextHandler != null) {
                    return nextHandler.validate(request);

                }
            
            return request;
        }
}
