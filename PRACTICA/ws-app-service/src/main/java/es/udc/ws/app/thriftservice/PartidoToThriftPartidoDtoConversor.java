package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.partido.Partido;
import es.udc.ws.app.thrift.ThriftPartidoDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartidoToThriftPartidoDtoConversor {
    public static Partido toPartido(ThriftPartidoDto partido) {
        return new Partido(partido.getIdPartido(), partido.getNombre(), LocalDateTime.parse(partido.getFechaPartido()),
                Double.valueOf(partido.getPrecio()).floatValue(), partido.getMaxEntradas(), partido.getEntradasVendidas());
    }

    public static List<ThriftPartidoDto> toThriftPartidoDtos(List<Partido> partidos) {

        List<ThriftPartidoDto> dtos = new ArrayList<>(partidos.size());

        for (Partido partido : partidos) {
            dtos.add(toThriftPartidoDto(partido));
        }
        return dtos;

    }

    public static ThriftPartidoDto toThriftPartidoDto(Partido partido) {

        return new ThriftPartidoDto(partido.getIdPartido(), partido.getNombre(),
                partido.getFechaPartido().toString(), partido.getPrecio(),
                partido.getMaxEntradas(), partido.getEntradasVendidas());

    }
}
