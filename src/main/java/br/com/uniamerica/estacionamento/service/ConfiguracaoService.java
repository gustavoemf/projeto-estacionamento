package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Transactional
    public void cadastraConfiguracao(Configuracao configuracao){
        // Verificar se valorHora é vazio
        // Verificar se valorMinutoMulta é vazio
        // Verificar se vagasCarro é vazio
        // Verificar se vagasMoto é vazio
        // Verificar se vagasVan é vazio
        if(configuracao.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if("".equals(configuracao.getInicioExpediente().toString())){
            throw new RuntimeException("o campo inicioExpediente não pode ser vazio");
        }
        if("".equals(configuracao.getFimExpediente().toString())){
            throw new RuntimeException("o campo fimExpediente não pode ser vazio");
        }
        if("".equals(configuracao.getTempoParaDesconto().toString())){
            throw new RuntimeException("o campo tempoParaDesconto não pode ser vazio");
        }
        if("".equals(configuracao.getTempoGanhoDeDesconto().toString())){
            throw new RuntimeException("o campo tempoGanhoDeDesconto não pode ser vazio");
        }
        this.configuracaoRepository.save(configuracao);
    }

    @Transactional
    public void atualizaConfiguracao(final Long id, Configuracao configuracao){
        final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);
        if(configuracaoBanco==null || !configuracaoBanco.getId().equals(configuracao.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        // Verificar se valorHora é vazio
        // Verificar se valorMinutoMulta é vazio
        // Verificar se vagasCarro é vazio
        // Verificar se vagasMoto é vazio
        // Verificar se vagasVan é vazio
        if("".equals(configuracao.getInicioExpediente().toString())){
            throw new RuntimeException("o campo inicioExpediente não pode ser vazio");
        }
        if("".equals(configuracao.getFimExpediente().toString())){
            throw new RuntimeException("o campo fimExpediente não pode ser vazio");
        }
        if("".equals(configuracao.getTempoParaDesconto().toString())){
            throw new RuntimeException("o campo tempoParaDesconto não pode ser vazio");
        }
        if("".equals(configuracao.getTempoGanhoDeDesconto().toString())){
            throw new RuntimeException("o campo tempoGanhoDeDesconto não pode ser vazio");
        }
        this.configuracaoRepository.save(configuracao);
    }
}
