package es.udc.ws.app.thriftservice;

import es.udc.ws.app.model.compra.Compra;
import es.udc.ws.app.model.partido.Partido;
import es.udc.ws.app.model.partidoService.PartidoServiceFactory;
import es.udc.ws.app.model.partidoService.exceptions.InvalidMatchDateException;
import es.udc.ws.app.model.partidoService.exceptions.NotEnoughTicketsException;
import es.udc.ws.app.model.partidoService.exceptions.TicketAlreadyGivenException;
import es.udc.ws.app.model.partidoService.exceptions.WrongCreditCardException;
import es.udc.ws.app.thrift.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.spi.CalendarNameProvider;

public class ThriftPartidoServiceImpl implements ThriftPartidoService.Iface{
    @Override
    public ThriftPartidoDto addPartido(ThriftPartidoDto partidoDto) throws ThriftInputValidationException {

        Partido partido = PartidoToThriftPartidoDtoConversor.toPartido(partidoDto);

        try {
            Partido addedPartido = PartidoServiceFactory.getService().addPartido(partido);
            return PartidoToThriftPartidoDtoConversor.toThriftPartidoDto(addedPartido);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

    }

    @Override
    public List<ThriftPartidoDto> findByDate(String fecha) throws ThriftInputValidationException {

        try {
            List<Partido> partidos = PartidoServiceFactory.getService()
                .findByDate(LocalDateTime.now().withNano(0),LocalDateTime.parse(fecha));

            return PartidoToThriftPartidoDtoConversor.toThriftPartidoDtos(partidos);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public ThriftPartidoDto findById(long idPartido) throws ThriftInstanceNotFoundException {

        try {

            Partido partido = PartidoServiceFactory.getService().findById(idPartido);
            return PartidoToThriftPartidoDtoConversor.toThriftPartidoDto(partido);

        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        }

    }

    @Override
    public ThriftCompraDto buyTicket(long idPartido, String email, String tarjeta, int cantidad) throws ThriftInvalidMatchDateException, ThriftInstanceNotFoundException, ThriftInputValidationException, ThriftNotEnoughTicketsException {

        try {

            Compra compra = PartidoServiceFactory.getService().buyTicket(idPartido, email, tarjeta, cantidad);
            return CompraToThriftCompraDtoConversor.toThriftCompraDto(compra);

        } catch (InvalidMatchDateException e) {
            throw new ThriftInvalidMatchDateException(e.getIdPartido(), e.getFechaPartido().toString());
        } catch (NotEnoughTicketsException e){
            throw new ThriftNotEnoughTicketsException(e.getIdPartido(), e.getCantidad());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public List<ThriftCompraDto> findPurchase(String email) throws ThriftInputValidationException {

        try {
            List<Compra> compras = PartidoServiceFactory.getService()
                    .findPurchase(email);

            return CompraToThriftCompraDtoConversor.toThriftCompraDtos(compras);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

    @Override
    public void pickupTicket(long idCompra, String tarjeta) throws ThriftWrongCreditCardException, ThriftInstanceNotFoundException, ThriftInputValidationException, ThriftTicketAlreadyGivenException {

        try {
            PartidoServiceFactory.getService().pickupTicket(idCompra,tarjeta);

        } catch (TicketAlreadyGivenException e) {
            throw new ThriftTicketAlreadyGivenException(e.getIdCompra());
        } catch (WrongCreditCardException e){
            throw new ThriftWrongCreditCardException(e.getIdCompra());
        } catch (InstanceNotFoundException e) {
            throw new ThriftInstanceNotFoundException(e.getInstanceId().toString(),
                    e.getInstanceType().substring(e.getInstanceType().lastIndexOf('.') + 1));
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }
    }

}
