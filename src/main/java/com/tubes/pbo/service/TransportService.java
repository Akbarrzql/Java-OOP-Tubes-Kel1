package com.tubes.pbo.service;

import com.tubes.pbo.model.Transport;
import java.util.List;
import java.util.Optional;

public interface TransportService {
    List<Transport> getAllTransport();
    Optional<Transport> getTransportById(Integer id);
    Transport saveTransport(Transport transport);
    Transport updateTransport(Transport transport);
    void deleteTransport(Integer id);
}