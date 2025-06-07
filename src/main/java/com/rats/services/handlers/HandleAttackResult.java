package com.rats.services.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.AttackResultContent;
import com.rats.models.ShipModel;
import com.rats.validations.CalculadoraDeBatalha;

public class HandleAttackResult implements IHandleChain {

    private IHandleChain nextHandler;
    private final ShipModel shipModel;
    private final ObjectMapper objectMapper;

    public HandleAttackResult(ShipModel shipModel) {
        this.shipModel = shipModel;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public IHandleChain next(IHandleChain nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    @Override
    public ICommunication validate(ICommunication request) {
        if (isAttackResultForThisShip(request)) {
            HandleLog.title("Ataque recebido: ");
            processAttackResult(request);
        }

        if (nextHandler != null) {
            return nextHandler.validate(request);
        }

        return request;
    }

    private boolean isAttackResultForThisShip(ICommunication request) {
        return request.getEvento() == EventsEnum.ResultadoAtaqueEfetuado
                && request.getNavioDestino().equals(Configs.getInstance().SUBSCRIPTION_NAME);
    }

    private void processAttackResult(ICommunication request) {
        try {
            AttackResultContent messageReceived = objectMapper.readValue(request.getConteudo().toString(), AttackResultContent.class);

            if (messageReceived.getDistanciaAproximada() <= 7) {
                shipModel.setShootLevel(1);
                System.out.println("Distancia menor que 7: " + messageReceived.getDistanciaAproximada());

                shipModel.distanceApproximate = String.valueOf(messageReceived.getDistanciaAproximada());
                List<Long[]> wrappedPositions = new ArrayList<>();

                CalculadoraDeBatalha.calcularPosicoesPossiveis(
                        messageReceived.getPosicao().getX(),
                        messageReceived.getPosicao().getY(),
                        messageReceived.getDistanciaAproximada()
                ).forEach(pos -> wrappedPositions.add(Arrays.stream(pos).boxed().toArray(Long[]::new)));

                shipModel.secondSetShoot.add(wrappedPositions);
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
    }
}
