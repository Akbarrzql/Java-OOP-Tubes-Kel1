package com.tubes.pbo.service;

import com.tubes.pbo.model.Provinsi;
import com.tubes.pbo.repository.ProvinsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProvinsiService {

    @Autowired
    private ProvinsiRepository provinsiRepository;

    public Provinsi save(Provinsi provinsi) {
        return provinsiRepository.save(provinsi);
    }

    public List<Provinsi> findAll() {
        return provinsiRepository.findAll();
    }

    public Provinsi update(Integer id, Provinsi data) {
        Provinsi existing = provinsiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provinsi tidak ditemukan"));
        existing.setNama(data.getNama());
        existing.setKota(data.getKota());
        existing.setDeskripsi(data.getDeskripsi());
        return provinsiRepository.save(existing);
    }

    public void delete(Integer id) {
        provinsiRepository.deleteById(id);
    }

    public long countDistinctNama() {
        return provinsiRepository.findAll()
                .stream()
                .map(Provinsi::getNama)
                .distinct()
                .count();
    }

    public String getMostPopular() {
        List<Provinsi> list = provinsiRepository.findAll();
        if (list.isEmpty())
            return "-";
        return list.stream()
                .collect(Collectors.groupingBy(Provinsi::getNama, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("-");
    }
}