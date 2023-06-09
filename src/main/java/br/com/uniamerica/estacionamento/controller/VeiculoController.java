package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Cor;
import br.com.uniamerica.estacionamento.entity.Tipo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
        return veiculo == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(veiculo);
    }

    @GetMapping
    public ResponseEntity <?> findByIdRequest(@RequestParam("id") final Long id){
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
        return veiculo == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(veiculo);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> listaCompleta(){return ResponseEntity.ok(this.veiculoRepository.findAll());}
    @GetMapping("/ativo")
    public ResponseEntity <?> listaAtivo(){return ResponseEntity.ok(this.veiculoRepository.findByAtivo(true));}
    @GetMapping("/cores")
    public ResponseEntity<Cor[]> getCores() {
        return ResponseEntity.ok().body(Cor.values());
    }
    @GetMapping("/tipos")
    public ResponseEntity<Tipo[]> getTipo() {
        return ResponseEntity.ok().body(Tipo.values());
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody @Validated final Veiculo veiculo){
        try{
            this.veiculoService.cadastraVeiculo(veiculo);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity <?> editar(@PathVariable("id") final Long id, @RequestBody @Validated Veiculo veiculo){
        try{
            this.veiculoService.atualizaVeiculo(id, veiculo);
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Registro atualizado com sucesso!");
    }

    @DeleteMapping
    public ResponseEntity <?> deletar(@RequestParam("id") final Long id){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        try{
            this.veiculoRepository.delete(veiculoBanco);
        }
        catch(RuntimeException e){
            if(!veiculoBanco.isAtivo()){
                throw new RuntimeException("Esse registro já está desativado!");
            }
            veiculoBanco.setAtivo(false);
            this.veiculoRepository.save(veiculoBanco);
            return ResponseEntity.internalServerError().body("[REGISTRO DESATIVADO]" + e.getCause().getCause().getMessage());
        }
        veiculoBanco.setAtualizacao(LocalDateTime.now());
        return ResponseEntity.ok("Registro deletado com sucesso!");
    }
}
