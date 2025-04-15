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
                    if (messageReceived.getDistanciaAproximada() >= 1 && messageReceived.getDistanciaAproximada() != 5  && messageReceived.getDistanciaAproximada() <= 7 && shipModel.getShootLevel() == 0) {
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
                        
                        List<long[]> wrappedPositions = new ArrayList<>();

                        long x = messageReceived.getPosicao().getX();
                        long y = messageReceived.getPosicao().getY();

                        switch (Configs.enemyScore) {
                            case 85:
                                wrappedPositions.add(new long[] {x - 2, y});
                                wrappedPositions.add(new long[] {x, y - 2});
                                wrappedPositions.add(new long[] {x + 2, y});
                                wrappedPositions.add(new long[] {x, y + 2});
                                break;
                            default:
                                wrappedPositions.add(new long[] {x - 1, y});
                                wrappedPositions.add(new long[] {x, y - 1});
                                wrappedPositions.add(new long[] {x + 1, y});
                                wrappedPositions.add(new long[] {x, y + 1});
                                wrappedPositions.add(new long[] {x - 2, y});
                                wrappedPositions.add(new long[] {x, y - 2});
                                wrappedPositions.add(new long[] {x + 2, y});
                                wrappedPositions.add(new long[] {x, y + 2});
                                break;
                        }

                        wrappedPositions.sort((a, b) -> {
                            int result = 0;
                        
                            // Comparação para eixo X
                            if (Configs.POSITION_X_RED_SHOOT > x) {
                                if (a[0] < x && b[0] >= x) return -1;
                                if (a[0] >= x && b[0] < x) return 1;
                            } else if (Configs.POSITION_X_RED_SHOOT < x) {
                                if (a[0] > x && b[0] <= x) return -1;
                                if (a[0] <= x && b[0] > x) return 1;
                            }
                        
                            // Comparação para eixo Y
                            if (Configs.POSITION_Y_RED_SHOOT > y) {
                                if (a[1] < y && b[1] >= y) return -1;
                                if (a[1] >= y && b[1] < y) return 1;
                            } else if (Configs.POSITION_Y_RED_SHOOT < y) {
                                if (a[1] > y && b[1] <= y) return -1;
                                if (a[1] <= y && b[1] > y) return 1;
                            }
                        
                            return result;
                        });
                        
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
