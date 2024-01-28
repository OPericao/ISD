package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.compra.Compra;
import es.udc.ws.app.model.partido.Partido;
import es.udc.ws.app.thrift.ThriftCompraDto;
import es.udc.ws.app.thrift.ThriftPartidoDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CompraToThriftCompraDtoConversor {

    public static ThriftCompraDto toThriftCompraDto(Compra compra) {

        return new ThriftCompraDto(compra.getIdCompra(), compra.getEmail(), compra.getTarjeta().substring(compra.getTarjeta().length()-4), compra.getCantidad(), compra.getFechaCompra().toString(), compra.getIdPartido(),
                compra.isRecogida());

    }

    public static List<ThriftCompraDto> toThriftCompraDtos(List<Compra> compras) {

        List<ThriftCompraDto> dtos = new ArrayList<>(compras.size());

        for (Compra compra : compras) {
            dtos.add(toThriftCompraDto(compra));
        }
        return dtos;
    }

    public static Compra toCompra(ThriftCompraDto compra) {
        return new Compra(compra.getIdCompra(), compra.getEmail(), compra.getTarjeta(), compra.getCantidad(), LocalDateTime.parse(compra.getFechaCompra()), compra.getIdPartido(), compra.isRecogida());
    }
}
