package com.byt3social.acompanhamento.exceptions;

public class FailedToSaveFileException extends RuntimeException {
    public FailedToSaveFileException() {
        super("Falha ao salvar arquivo");
    }
}
