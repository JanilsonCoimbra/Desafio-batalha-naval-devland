package com.ratos.services.handlers;
import com.ratos.interfaces.ICommunication;
import com.ratos.interfaces.IHandleChain;

public class HandleCryptography implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {
                System.out.println("------------------------------------------------------------");
                System.out.println("Cryptography: Processing message.");
                System.out.println("------------------------------------------------------------");

                if (nextHandler != null) {
                    return nextHandler.validate(request);

                }
            
            return request;
        }
}
