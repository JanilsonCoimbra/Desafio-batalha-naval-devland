package com.ratos.interfaces;

public interface IHandleChain {
    IHandleChain next(IHandleChain nextHandler);

    ICommunication validate(ICommunication request);
}
