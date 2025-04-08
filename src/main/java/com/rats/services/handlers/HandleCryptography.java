package com.rats.services.handlers;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;

public class HandleCryptography implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {
                HandleLog.title("Validando criptografia");  

                if (nextHandler != null) {
                    return nextHandler.validate(request);

                }
            
            return request;
        }
}
