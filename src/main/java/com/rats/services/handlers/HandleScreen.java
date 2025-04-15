package com.rats.services.handlers;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;

public class HandleScreen implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication payload) {
            
                if(Configs.SUBSCRIPTION_NAME.equals(payload.getOrigem())) {
                    System.out.println(" Origem: " + payload.getOrigem() + 
                    "\n Evento: " + payload.getEvento() + 
                    "\n Destino: " + payload.getNavioDestino() + 
                    "\n CorrelationId: " + payload.getCorrelationId() + 
                    "\n Conteudo: " + payload.getConteudo().toString().replace("\n", "").replace(" ", ""));
                } 

                if("POSSEIDON".equals(payload.getOrigem()) && !payload.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                    System.out.println(" Origem: " + payload.getOrigem() + 
                    "\n Evento: " + payload.getEvento() + 
                    "\n NavioDestino: " + payload.getNavioDestino() +
                    "\n CorrelationId: " + payload.getCorrelationId() +
                    "\n Pontuacao: " + payload.getPontuacaoNavios().keySet() +
                    "\n Valores: " + payload.getPontuacaoNavios().values());                }
                
                if("POSSEIDON".equals(payload.getOrigem()) && payload.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                    System.out.println(" Origem: " + payload.getOrigem() + 
                    "\n Evento: " + payload.getEvento() + 
                    "\n NavioDestino: " + payload.getNavioDestino() +
                    "\n CorrelationId: " + payload.getCorrelationId() + 
                    "\n Conteudo: " + payload.getConteudo().toString().replace("\n", "").replace(" ", "")+
                    "\n Pontuacao: " + (payload.getPontuacaoNavios().keySet()) +
                    "\n Pontuacao: " + payload.getPontuacaoNavios().values()
                    );                }
                
                if (!payload.getOrigem().equals(Configs.SUBSCRIPTION_NAME) && !payload.getOrigem().equals("POSSEIDON")) {
                    System.out.println(" Origem: " + payload.getOrigem() + 
                    "\n CorrelationId: " + payload.getCorrelationId() + 
                    "\n Evento: " + payload.getEvento());
                }
                
                HandleLog.title("");
                if (nextHandler != null) {
                    return nextHandler.validate(payload);
                }
            
            return payload;
        }
}
