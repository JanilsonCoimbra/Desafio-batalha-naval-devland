package com.rats.services.handlers;
import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ShipModel shipModel = ShipModel.getShipModel();
    
    
    @Override
    public IHandleChain next(IHandleChain nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }
    
    @Override
    public ICommunication validate(ICommunication payload) {
        
        if (payload.getEvento() == EventsEnum.ResultadoAtaqueEfetuado && payload.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
                    
                    AttackResultContent messageReceived = objectMapper.readValue(payload.getConteudo().toString(), AttackResultContent.class);

                    if(messageReceived.getDistanciaAproximada() == 5 && shipModel.getShootLevel() == 0){
                        messageReceived.getPosicao().getX();
                        messageReceived.getPosicao().getY();
                        Configs.FIRST_SET_SHOOT_FIVE = new ArrayList<>();
                        Configs.FIRST_SET_SHOOT_FIVE.add(Math.toIntExact(messageReceived.getPosicao().getX() - 1));
                        Configs.FIRST_SET_SHOOT_FIVE.add(Math.toIntExact(messageReceived.getPosicao().getY()));

                        if (nextHandler != null) {
                            return nextHandler.validate(payload);
                        }
                    }
                    if (messageReceived.getDistanciaAproximada() >= 1 && messageReceived.getDistanciaAproximada() != 5 && messageReceived.getDistanciaAproximada() <= 7 && shipModel.getShootLevel() == 0) {
                        HandleLog.title("SET SHOOT LEVEL 1: ");
                        shipModel.setShootLevel(1);
                        shipModel.distanceApproximate = String.valueOf(messageReceived.getDistanciaAproximada());

                        List<long[]> wrappedPositions = CalculadoraDeBatalha.calcularPosicoesPossiveis(messageReceived.getPosicao().getX(), messageReceived.getPosicao().getY(), messageReceived.getDistanciaAproximada());
                        Configs.POSITION_X_RED_SHOOT = Math.toIntExact(messageReceived.getPosicao().getX());
                        Configs.POSITION_Y_RED_SHOOT = Math.toIntExact(messageReceived.getPosicao().getY());
                    
                        Configs.SECOND_SET_SHOOT = new ArrayList<>();
                        wrappedPositions.forEach(item -> {
                            System.out.println("Posicoes possiveis level 1: "+Arrays.toString(item));
                            Configs.SECOND_SET_SHOOT.add(Arrays.asList((int)item[0], (int)item[1]));
                        });

                        if (nextHandler != null) {
                            return nextHandler.validate(payload);
                        }
                    }

                    if (messageReceived.getDistanciaAproximada() >= 1 && messageReceived.getDistanciaAproximada() <= 7 && shipModel.getShootLevel() == 1) {
                        List<long[]> tempSetShoot = CalculadoraDeBatalha.calcularPosicoesPossiveis(messageReceived.getPosicao().getX(), messageReceived.getPosicao().getY(), messageReceived.getDistanciaAproximada());
                        List<List<Integer>> tempSetShootList = new ArrayList<>();
                        tempSetShoot.forEach(item -> {
                            System.out.println("Posicoes possiveis level 1: "+Arrays.toString(item));
                            tempSetShootList.add(Arrays.asList((int)item[0], (int)item[1]));
                        });
                        List<List<Integer>> commonElements = new ArrayList<>();
                        for (List<Integer> list1 : Configs.SECOND_SET_SHOOT) {
                            for (List<Integer> list2 : tempSetShootList) {
                                if (list1.equals(list2)) {
                                    commonElements.add(list1);
                                }
                            }
                        }
                        if (!commonElements.isEmpty()) {
                            Configs.SECOND_SET_SHOOT = commonElements;
                            System.out.println("Elementos em comum encontrados: " + commonElements);
                        } else {
                            System.out.println("Nenhum elemento em comum encontrado.");
                        }

                    }

                    if (messageReceived.getDistanciaAproximada() == 1000 && shipModel.getShootLevel() == 1) {
                        List<List<Integer>> isClose = new ArrayList<>();

                        Configs.SECOND_SET_SHOOT.forEach(item -> {
                            if (CalculadoraDeBatalha.calcularDistanciaEntrePontos(Math.toIntExact(messageReceived.getPosicao().getX()), Math.toIntExact(messageReceived.getPosicao().getY()), item.get(0), item.get(1)) <= 7) {
                                isClose.add(item);
                            }
                        });
                        Configs.SECOND_SET_SHOOT.removeIf(item -> isClose.contains(item));
                    }
                    
                    if (messageReceived.isAcertou() && (shipModel.getShootLevel() == 1 || shipModel.getShootLevel() == 0)) {
                        shipModel.setShootLevel(2);

                        payload.getPontuacaoNavios().forEach((key, value) -> {
                            System.out.println("Naviooo : " + key + " - Pontuação: " + value);
                            if(!key.equals(Configs.SUBSCRIPTION_NAME)) {
                                Configs.enemyScore = value;
                            }
                        });

                        System.out.println("Acertou a posição e o nível de tiro é 1 ou 0: ");
                        HandleLog.title("Acertou: " + messageReceived.isAcertou());
                        
                        List<long[]> wrappedPositions = CalculadoraDeBatalha.calculateThirdSetShoot(messageReceived.getPosicao().getX(), messageReceived.getPosicao().getY());
                        
                        Configs.THIRD_SET_SHOOT = new ArrayList<>();
                        wrappedPositions.forEach(item -> {
                            System.out.println("Posicoes possiveis level 2: "+Arrays.toString(item));
                            if((int)item[0] >= 1 && (int)item[0] <= 100 && (int)item[1] >= 1 && (int)item[1] <= 30) 
                                Configs.THIRD_SET_SHOOT.add(Arrays.asList((int)item[0], (int)item[1]));
                        }); 
                    }
                } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                    throw new RuntimeException("Error processing JSON", e);
                }
            }
            if (nextHandler != null) {
                return nextHandler.validate(payload);

            }
            
            return payload;
        }

}
