## Ataque

    Ataque = 2, // Envio - Enviar quando for atacar o adversário
    LiberacaoAtaque = 3, //Recebimento - Quando Receber este evento você poderá Atacar e no conteúdo tiver a indicação do navio o mesmo poderá Atacar
    NavioAbatido = 8, //Recebimento - informação que o seu navio foi abatido
    ResultadoAtaqueEfetuado = 12,//Recebimento - resultado do ataque Efetuado no evento “Ataque”

## Defesa
    

## RegistroNavio

    RegistroNavio = 1, // Envio - Enum para Ser Enviado durante o registro do Navio
    CampoLiberadoParaRegistro = 10, //Recebimento - ao receber este evento o registre seu navio pela primeira vez
    RegistrarNovamente = 7, //Recebimento - ao receber este evento o registre seu navio novamente

## MonitorarDemaisEventos

    RenderizarTela = 6, //Ignorar - comunicação interna do controlador e renderizador
    Vitoria = 9, // Recebimento - Informações que o seu navio Ganhou a partida
    IniciarPartida = 11,//Recebimento - aviso que a batalha foi iniciada

## criptografia

    TesteCripto = 4, //Envio - Enviar para testar se a criptografia esta sendo feita corretamente
    RespostaTesteCripto = 5, //Recebimento - resposta com o conteúdo descriptografado do evento “TesteCripto”

