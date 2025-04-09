package com.rats.services.handlers;

import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;

public class HandleDefense implements IHandleChain{

	private IHandleChain nextHandler;
	
    @Override
    public IHandleChain next(IHandleChain nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

	@Override
	public ICommunication validate(ICommunication request) {
        if (nextHandler != null) {
            return nextHandler.validate(request);
        }
        
        return request;
	}
	
	public ICommunication validateAttackRelease(ICommunication request) {		
		return request;
	}
	
	public static Boolean validateAutoAttack(Integer positionX, Integer positionY) {   
		if (positionX == Integer.parseInt(Configs.POSITION_X) && positionY == Integer.parseInt(Configs.POSITION_Y)) {
			System.out.println("------------------------------------------------------------");
	        System.out.println("Ataque bloqueado: [Ataque bloqueado]");
	        System.out.println("------------------------------------------------------------");
                   
	        return false;
	    }
	                
		return true;
	}
	
	public static boolean validateResultAttack(ICommunication request) {
		if (request.getEvento() == EventsEnum.ResultadoAtaqueEfetuado) {
//			if(CorrelationIdValidate.isValid(request.getCorrelationId())) {
//                System.out.println("------------------------------------------------------------");
//                System.out.println("Resultado do ataque ao navio "+request.getNavioDestino()+": [validado]");
//                System.out.println("------------------------------------------------------------");
//			} else {
//				System.out.println("------------------------------------------------------------");
//                System.out.println("Resultado do ataque ao navio "+request.getNavioDestino()+": [fraudulento]");
//                System.out.println("------------------------------------------------------------");
//			}
        }
			
		return true;
	}
}