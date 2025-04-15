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
            try {
                if(!Configs.CRIPTOGRAFY_KEY_STRING.isEmpty()) {
                        String conteudoDecripted = Cryptography.decryptString(payload.getConteudo().toString(), Configs.CRIPTOGRAFY_KEY_STRING);
                        payload.setConteudo(conteudoDecripted);
                        return nextHandler.validate(payload);
                }
            } catch (Exception e) {
                HandleLog.title("Erro ao descriptografar : " + e.getMessage());
            }
            
            if (nextHandler != null) {
                return nextHandler.validate(payload);
            }
            
            return payload;
        }
}
