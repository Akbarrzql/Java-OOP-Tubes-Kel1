package com.tubes.pbo.service;

import java.util.List;
import java.util.Optional;

import com.tubes.pbo.model.Destinasi;

public interface DestinasiService {

    List<Destinasi> getAllDestinasi();

    Optional<Destinasi> getDestinasiById(Integer id);

    Destinasi saveDestinasi(Destinasi destinasi);

    Destinasi updateDestinasi(Destinasi destinasi);

    void deleteDestinasi(Integer id);
}