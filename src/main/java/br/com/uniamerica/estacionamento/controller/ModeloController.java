package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloRepository modeloRepository;

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

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Modelo modelo){
        try{
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Registro realizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity <?> editar(@RequestParam("id") final Long id, @RequestBody Modelo modelo){
        try{
            final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
            if(modeloBanco == null || !modeloBanco.getId().equals(modelo.getId())){
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }
            this.modeloRepository.save(modeloBanco);
            return ResponseEntity.ok("Registro atualizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity <?> deletar(@RequestParam("id") final Long id){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        try{
            this.modeloRepository.delete(modeloBanco);
            return ResponseEntity.ok("Registro deletado");
        }
        catch(RuntimeException e){
            modeloBanco.setAtivo(false);
            this.modeloRepository.save(modeloBanco);
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
    }
}
