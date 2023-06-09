package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping("/{id}")
    public ResponseEntity <?> findByIdPath (@PathVariable("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);
        return configuracao == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(configuracao);
    }

    @GetMapping
    public ResponseEntity <?> findByIdRequest(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);
        return configuracao == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(configuracao);
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody @Validated final Configuracao configuracao){
        try{
            this.configuracaoService.cadastraConfiguracao(configuracao);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity <?> editar(@PathVariable("id") final Long id, @RequestBody @Validated final Configuracao configuracao){
        try{
            this.configuracaoService.atualizaConfiguracao(id, configuracao);
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Registro atualizado com sucesso!");
    }
}
