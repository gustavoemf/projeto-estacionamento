package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;
}
