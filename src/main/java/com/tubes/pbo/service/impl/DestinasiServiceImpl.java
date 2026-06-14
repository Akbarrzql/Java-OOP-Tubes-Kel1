package com.tubes.pbo.service.impl;

import com.tubes.pbo.model.Destinasi;
import com.tubes.pbo.repository.DestinasiRepository;
import com.tubes.pbo.service.DestinasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinasiServiceImpl implements DestinasiService {

    @Autowired
    private DestinasiRepository destinasiRepository;

    @Override
    public List<Destinasi> getAllDestinasi() {
        return destinasiRepository.findAll();
    }

    @Override
    public Optional<Destinasi> getDestinasiById(Integer id) {
        return destinasiRepository.findById(id);
    }

    @Override
    public Destinasi saveDestinasi(Destinasi destinasi) {
        return destinasiRepository.save(destinasi);
    }

    @Override
    public Destinasi updateDestinasi(Destinasi destinasi) {
        return destinasiRepository.save(destinasi);
    }

    @Override
    public void deleteDestinasi(Integer id) {
        destinasiRepository.deleteById(id);
    }
}