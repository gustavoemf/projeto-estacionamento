package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
}
