package com.tubes.pbo.service.impl;

import com.tubes.pbo.model.Transport;
import com.tubes.pbo.repository.TransportRepository;
import com.tubes.pbo.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {

    @Autowired
    private TransportRepository transportRepository;

    @Override
    public List<Transport> getAllTransport() {
        return transportRepository.findAll();
    }

    @Override
    public Optional<Transport> getTransportById(Integer id) {
        return transportRepository.findById(id);
    }

    @Override
    public Transport saveTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public Transport updateTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public void deleteTransport(Integer id) {
        transportRepository.deleteById(id);
    }
}