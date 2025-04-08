package com.rats.services.handlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;

public class HandleAttackResult implements IHandleChain {


    private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {

            if (request.getEvento() == EventsEnum.ResultadoAtaqueEfetuado && request.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                
                System.out.println("------------------------------------------------------------");
                System.out.println("Atack Result: Processing message.");
                System.out.println("------------------------------------------------------------");  

                if(isGoodShoot(request.getConteudo().toString())) {
                    Configs.SHOOT_LEVEL = 1;
                    //TODO: serializar o conteudo
                    // Configs.DISTANCE_APPROXIMATE = request.getConteudo().get("distancia aproximada");
                    // Configs.SECOND_SET_SHOOT = calcularPosicoesPossiveis(Configs.X_GOOD_SHOOT, Configs.Y_GOOD_SHOOT, Configs.DISTANCE_APPROXIMATE);
                }
                
            }

            if (nextHandler != null) {
                return nextHandler.validate(request);

            }
            
            return request;
        }

        private boolean isGoodShoot(String result) {
            // TODO: Se distancia aproximada != de 1000 true
            return false;
        }

        public static List<long[]> calcularPosicoesPossiveis(long x, long y, double raio) {
        List<long[]> posicoes = new ArrayList<>();
        long raioInt = Math.round(raio);
        // Percorrer todas as posições dentro do raio
        for (long i = -raioInt; i <= raio; i++) {
            for (long j = -raioInt; j <= raio; j++) {
                long novoX = x + j;
                long novoY = y + i;
                
                double distanciaTeste = Math.round(Math.sqrt((double) (i * i + j * j)) * 100.0) / 100.0;
                double raioArredondado = Math.round(raio * 100.0) / 100.0;
                // Verificar se a posição está dentro dos limites da matriz
                if (novoX >= 0 && novoX < 30 && novoY >= 0 && novoY < 100) {
                    // Verificar se a posição está dentro do raio
                    if (distanciaTeste == raioArredondado) {
                        posicoes.add(new long[]{novoX, novoY});
                    }
                }
            }
        }

        return posicoes;
    }
}
