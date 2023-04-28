package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoRepository veiculoRepository;
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

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Registro realizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity <?> editar(@RequestParam("id") final Long id, @RequestBody Veiculo veiculo){
        try{
            final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
            if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculo.getId())){
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }
            this.veiculoRepository.save(veiculoBanco);
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
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        try{
            this.veiculoRepository.delete(veiculoBanco);
            return ResponseEntity.ok("Registro deletado");
        }
        catch(RuntimeException e){
            veiculoBanco.setAtivo(false);
            this.veiculoRepository.save(veiculoBanco);
            return ResponseEntity.internalServerError().body("Erro " + e.getCause().getCause().getMessage());
        }
    }
}
