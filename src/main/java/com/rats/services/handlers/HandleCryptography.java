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
        public ICommunication validate(ICommunication payload) {
            
                if(!Configs.CRIPTOGRAFY_KEY_STRING.isEmpty()) {
                        HandleLog.title("Descriptografando mensagem.");
                        String conteudoDecripted = Cryptography.decryptString(payload.getConteudo().toString(), Configs.CRIPTOGRAFY_KEY_STRING);
                        payload.setConteudo(conteudoDecripted);
                        return nextHandler.validate(payload);
                }
                
                HandleLog.title("Chave de criptografia nao configurada.");

                if (nextHandler != null) {
                    return nextHandler.validate(payload);
                }
            
            return payload;
        }
}
