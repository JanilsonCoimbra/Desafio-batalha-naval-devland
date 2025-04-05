package com.ratos.interfaces;

public interface IHandleChain {
    IHandleChain next(IHandleChain nextHandler);

    IComunication validate(IComunication request);
}
