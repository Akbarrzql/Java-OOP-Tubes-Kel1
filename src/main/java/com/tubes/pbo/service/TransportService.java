package com.tubes.pbo.service;

import com.tubes.pbo.model.Transport;
import com.tubes.pbo.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public List<Transport> getAllTransport() {
        return transportRepository.findAll();
    }

    public void saveTransport(Transport transport) {
        transportRepository.save(transport);
    }

    public Transport getTransportById(int id) {
        return transportRepository.findById(id).orElse(null);
    }

    public void deleteTransportById(int id) {
        transportRepository.deleteById(id);
    }
}