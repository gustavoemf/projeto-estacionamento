package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private CondutorService condutorService;

    @GetMapping("/{id}")
    public ResponseEntity <?> findByIdPath (@PathVariable("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);
        return condutor == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(condutor);
    }

    @GetMapping
    public ResponseEntity <?> findByIdRequest(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);
        return condutor == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(condutor);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> listaCompleta(){return ResponseEntity.ok(this.condutorRepository.findAll());}

    @GetMapping("/ativo")
    public ResponseEntity <?> listaAtivo(){return ResponseEntity.ok(this.condutorRepository.findByAtivo(true));}

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody @Validated final Condutor condutor){
        try{
            this.condutorService.cadastraCondutor(condutor);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Registro realizado");
    }

    @PutMapping
    public ResponseEntity <?> editar(@RequestParam("id") final Long id, @RequestBody @Validated final Condutor condutor){
        try{
            this.condutorService.atualizaCondutor(id, condutor);
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Registro atualizado");
    }

    @DeleteMapping
    public ResponseEntity <?> deletar(@RequestParam("id") final Long id){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        try{
                this.condutorRepository.delete(condutorBanco);
        }
        catch(DataIntegrityViolationException e){
            condutorBanco.setAtivo(false);
            this.condutorRepository.save(condutorBanco);
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        condutorBanco.setAtualizacao(LocalDateTime.now());
        return ResponseEntity.ok("Registro deletado");
    }
}
