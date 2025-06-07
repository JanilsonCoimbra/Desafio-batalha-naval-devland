package com.rats.services.handlers;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;
public class HandleRegisterCampo implements IHandleChain {
    private IHandleChain nextHandler;
    private final ServiceBus serviceBus;

    public HandleRegisterCampo(ServiceBus serviceBus) {
        this.serviceBus = serviceBus;
    }

    @Override
    public IHandleChain next(IHandleChain nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    @Override
    public ICommunication validate(ICommunication request) {
        try {
            if (request.getEvento() == EventsEnum.CampoLiberadoParaRegistro ||
                request.getEvento() == EventsEnum.RegistrarNovamente) {
                registrarCampo(request);
                return request;
            }
            if (nextHandler != null) {
                return nextHandler.validate(request);
            }
        } catch (Exception e) {
            HandleLog.title("Error in HandleRegisterCampo: " + e.getMessage());
        }
        return request;
    }

    private void registrarCampo(ICommunication request) {
        HandleLog.title("Campo de batalha encontrado " + request.getCorrelationId());
        Message message = DirectorMessage.createRegisterMessage(request.getCorrelationId());
        ServiceBusMessage messageService = new ServiceBusMessage(message.toString());
        serviceBus.sendMessage(messageService);
    }
}
