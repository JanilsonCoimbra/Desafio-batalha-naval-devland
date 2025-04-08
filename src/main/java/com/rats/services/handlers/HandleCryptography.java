package com.rats.services.handlers;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.services.Cryptography;

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

                if(!Configs.CRIPTOGRAFY_KEY_STRING.isEmpty()) {
                    String conteudoDecripted = Cryptography.decryptString(request.getConteudo(), Configs.CRIPTOGRAFY_KEY_STRING);
                    request.setConteudo(conteudoDecripted);
                    return nextHandler.validate(request);
                }
                
                HandleLog.title("Chave de criptografia não configurada.");

                if (nextHandler != null) {
                    return nextHandler.validate(request);
                }
            
            return request;
        }
}
