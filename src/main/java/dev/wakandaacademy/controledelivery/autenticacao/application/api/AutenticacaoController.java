package dev.wakandaacademy.controledelivery.autenticacao.application.api;

import dev.wakandaacademy.controledelivery.autenticacao.application.service.AutenticacaoAppplicationService;
import dev.wakandaacademy.controledelivery.autenticacao.application.service.AutenticacaoService;
import dev.wakandaacademy.controledelivery.config.security.domain.ValidaConteudoAuthorizationHeader;
import dev.wakandaacademy.controledelivery.handler.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Log4j2
@RequiredArgsConstructor
public class AutenticacaoController implements AutenticacaoAPI {
    private final AutenticacaoAppplicationService autenticacaoAppplicationService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public TokenResponse autentica(AutenticacaoRequest autenticacaoRequest) {
        log.info("[inicio] Iniciando Metodo autenciacao em AutenticacaoController");
        var token = autenticacaoAppplicationService.autentica(autenticacaoRequest.getUserPassToken());
        log.info("[finaliza] Retornando Token para o cliente");
        return new TokenResponse(token);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public TokenResponse reativaAutenticacao(String tokenExpirado) throws AuthenticationException {
        log.info("[inicio] Iniciando Metodo revalidaAutenciacao em AutenticacaoController");
        String tokenExpiradoValido = validaTokenExpirado(Optional.of(tokenExpirado));
        var token = autenticacaoAppplicationService.reativaToken(tokenExpiradoValido);
        log.info("[finaliza] Retornando Token atualizado para o cliente");
        return new TokenResponse(token);
    }

    private String validaTokenExpirado(Optional<String> tokenExpirado) {
        String tokenExp = tokenExpirado.filter(new ValidaConteudoAuthorizationHeader())
                .orElseThrow(() -> APIException.build(HttpStatus.BAD_REQUEST, "Token Invalido!"));
        return tokenExp.substring(7, tokenExp.length());
    }
}
