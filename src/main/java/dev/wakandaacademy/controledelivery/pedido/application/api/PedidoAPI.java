package dev.wakandaacademy.controledelivery.pedido.application.api;

import dev.wakandaacademy.controledelivery.cliente.application.api.ClienteDetalhadoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pedido")
public interface PedidoAPI {
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    PedidoIdResponse postNovoPedido(@RequestHeader(name = "Authorization", required = true) String token,
                                    @RequestBody @Valid PedidoRequest pedidoRequest);

    @GetMapping("/{idPedido}")
    @ResponseStatus(code = HttpStatus.OK)
    PedidoDetalhadoResponse consultaPedido(@RequestHeader(name = "Authorization", required = true) String token,
                                           @PathVariable UUID idPedido);

    @GetMapping("/listaPedidos/{idCliente}")
    @ResponseStatus(code = HttpStatus.OK)
    List<PedidoDetalhadoResponse> listaPedidosCliente(
            @RequestHeader(name = "Authorization", required = true) String token,
            @PathVariable UUID idCliente);

    @PatchMapping("/alteraPedido/{idPedido}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void alteraPedido(@RequestHeader(name = "Authorization", required = true) String token,
                      @PathVariable UUID idPedido,
                      @RequestBody @Valid EditaPedidoRequest pedidoRequest);

    @DeleteMapping("/deletaPedido/{idPedido}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deletaPedido(@RequestHeader(name = "Authorization", required = true) String token,
                      @PathVariable UUID idPedido);

    @PatchMapping(value = "/enviado/{idPedido}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void mudaStatusParaEnviado(@RequestHeader (name = "Authorization", required = true) String token,
                               @PathVariable UUID idPedido);

    @PatchMapping(value = "/entregue/{idPedido}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void mudaStatusParaEntregue(@RequestHeader (name = "Authorization", required = true) String token,
                                @PathVariable UUID idPedido);

    @PatchMapping(value = "/cancelado/{idPedido}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void mudaStatusParaCancelado(@RequestHeader (name = "Authorization", required = true) String token,
                                @PathVariable UUID idPedido);
}
