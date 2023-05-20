package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private MarcaService marcaService;

    @GetMapping("/{id}")
    public ResponseEntity <?> findByIdPath(@PathVariable("id") final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);
        return marca == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(marca);
    }

    @GetMapping
    public ResponseEntity <?> findByIdRequest(@RequestParam("id") final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);
        return marca == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(marca);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> listaCompleta(){return ResponseEntity.ok(this.marcaRepository.findAll());}

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody @Valid final Marca marca){
        try{
            this.marcaService.cadastrarMarca(marca);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Registro realizado");
    }

    @PutMapping
    public ResponseEntity <?> editar(@RequestParam("id") @Valid final Long id, @RequestBody @Valid final Marca marca){
        try{
            this.marcaService.atualizarMarca(id, marca);
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getMessage());
        }
        marca.setAtualizacao(LocalDateTime.now());
        return ResponseEntity.ok("Registro atualizado");
    }

    @DeleteMapping
    public ResponseEntity <?> deletar(@RequestParam("id") final Long id){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        try{
            this.marcaRepository.delete(marcaBanco);
        }
        catch(RuntimeException e){
            marcaBanco.setAtivo(false);
            this.marcaRepository.save(marcaBanco);
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        marcaBanco.setAtualizacao(LocalDateTime.now());
        return ResponseEntity.ok("Registro deletado");
    }
}
