package es.udc.ws.app.model.partidoService.exceptions;

import java.time.LocalDateTime;

public class InvalidMatchDateException extends Exception{
    private Long idPartido;
    private LocalDateTime fechaPartido;

    public InvalidMatchDateException(Long idPartido, LocalDateTime fechaPartido) {
        super("Partido con id=\"" + idPartido +
                "\" ya ha tenido lugar en la fecha \"" +
                fechaPartido + "\")");
        this.idPartido = idPartido;
        this.fechaPartido = fechaPartido;
    }

    public Long getIdPartido() {
        return idPartido;
    }

    public LocalDateTime getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(LocalDateTime fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }
}
