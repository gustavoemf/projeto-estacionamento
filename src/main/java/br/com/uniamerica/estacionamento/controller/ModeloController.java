package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private ModeloService modeloService;

    @GetMapping("/{id}")
    public ResponseEntity <?> findByIdPath(@PathVariable("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);
        return modelo == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(modelo);
    }

    @GetMapping
    public ResponseEntity <?> findByIdRequest(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);
        return modelo == null ? ResponseEntity.badRequest().body("Nenhum valor encontrado") : ResponseEntity.ok(modelo);
    }

    @GetMapping("/lista")
    public ResponseEntity <?> listaCompleta(){return ResponseEntity.ok(this.modeloRepository.findAll());}
    @GetMapping("/ativo")
    public ResponseEntity <?> listaAtivo(){return ResponseEntity.ok(this.modeloRepository.findByAtivo(true));}

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody @Validated final Modelo modelo){
        try{
            this.modeloService.cadastraModelo(modelo);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro " + e.getMessage());
        }
        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity <?> editar(@PathVariable("id") final Long id, @RequestBody @Validated final Modelo modelo){
        try{
            this.modeloService.atualizaModelo(id, modelo);
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
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        try{
            this.modeloRepository.delete(modeloBanco);
        }
        catch(RuntimeException e){
            if(!modeloBanco.isAtivo()){
                throw new RuntimeException("Esse registro já está desativado");
            }
            modeloBanco.setAtivo(false);
            this.modeloRepository.save(modeloBanco);
            return ResponseEntity.internalServerError().body("[REGISTRO DESATIVADO]" + e.getCause().getCause().getMessage());
        }
        modeloBanco.setAtualizacao(LocalDateTime.now());
        return ResponseEntity.ok("Registro deletado com sucesso!");
    }
}
