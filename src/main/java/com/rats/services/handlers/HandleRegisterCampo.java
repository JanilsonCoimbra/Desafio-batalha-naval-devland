package com.rats.services.handlers;
import java.util.List;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;
import com.rats.validations.CalculadoraDeBatalha;

public class HandleRegisterCampo implements IHandleChain {
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication payload) {

                try{
                    if (payload.getEvento() == EventsEnum.CampoLiberadoParaRegistro) {
                        HandleLog.title("Campo de batalha encontrado "+payload.getCorrelationId()); 

                        List<Object> shipPositions = CalculadoraDeBatalha.generateInitialPositionShip(Configs.SHIP_LIST_POSITION);
                        HandleLog.title("Posições de navios: " + shipPositions.toString());
                        if (!shipPositions.isEmpty() && shipPositions.size() >= 3) {
                            Configs.POSITION_X = String.valueOf(shipPositions.get(0));
                            Configs.POSITION_Y = String.valueOf(shipPositions.get(1));
                            Configs.ORIENTATION = String.valueOf(shipPositions.get(2));
                        } else {
                            HandleLog.title("Erro: Lista de posições de navios está vazia ou incompleta.");
                        }
    
                        Message message  = DirectorMessage.createRegisterMessage(payload.getCorrelationId());
                        
                        ServiceBusMessage messageService = new ServiceBusMessage(message.toString());
    
                        ServiceBus service = ServiceBus.getInstance();
                        service.sendMessage(messageService);
    
                        return payload;
                    }
    
                    if(payload.getEvento() == EventsEnum.RegistrarNovamente) {
                        HandleLog.title("Novo Registro "+payload.getCorrelationId());  
    
    
                        Message message  = DirectorMessage.createRegisterMessage( payload.getCorrelationId());
                        
                        ServiceBusMessage messageService = new ServiceBusMessage(message.toString());
    
                        ServiceBus service = ServiceBus.getInstance();
                        service.sendMessage(messageService);
    
                    }
    
                    if (nextHandler != null) {
                        return nextHandler.validate(payload);
                    }
                    
                } catch (Exception e) {
                    HandleLog.title("Error in HandleRegisterCampo: " + e.getMessage());
                }
            
            return payload;
        }
}
