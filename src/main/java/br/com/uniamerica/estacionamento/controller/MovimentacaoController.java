package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.config.Validacoes;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private MovimentacaoService movimentacaoService;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        return movimentacao == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(movimentacao);
    }

    @GetMapping
    public ResponseEntity <?> findByIdRequest(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        return movimentacao == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> listaCompleta(){return ResponseEntity.ok(this.movimentacaoRepository.findAll());}

    @GetMapping("/aberta")
    public ResponseEntity <?> listaAberta(){return ResponseEntity.ok(this.movimentacaoRepository.findByAberta());}

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody @Validated final Movimentacao movimentacao){
        try{
            this.movimentacaoService.cadastraMovimentacao(movimentacao);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok(Validacoes.geraReciboCadastro(movimentacaoRepository.findById(movimentacao.getId()).get().getEntrada(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getSaida(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getCondutor().getNome(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getVeiculo().getPlaca(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getTempo(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getTempoMulta(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getTempoDesconto(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getValorTotal()));
    }

    @PutMapping
    public ResponseEntity <?> editar(@RequestParam("id") final Long id, @RequestBody @Validated Movimentacao movimentacao){
        try{
            this.movimentacaoService.atuaizaMovimentacao(id, movimentacao);
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok(Validacoes.geraReciboCadastro(movimentacaoRepository.findById(movimentacao.getId()).get().getEntrada(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getSaida(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getCondutor().getNome(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getVeiculo().getPlaca(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getTempo(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getTempoMulta(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getTempoDesconto(),
                movimentacaoRepository.findById(movimentacao.getId()).get().getValorTotal()));
    }

    @DeleteMapping
    public ResponseEntity <?> deletar(@RequestParam("id") final Long id){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(!movimentacaoBanco.isAtivo()){
            throw new RuntimeException("esse registro já está desativado");
        }
        movimentacaoBanco.setAtivo(false);
        this.movimentacaoRepository.save(movimentacaoBanco);
        movimentacaoBanco.setAtualizacao(LocalDateTime.now());
        return ResponseEntity.ok("Registro desativado");
    }
}
