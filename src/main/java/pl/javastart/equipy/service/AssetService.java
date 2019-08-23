package pl.javastart.equipy.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.equipy.model.*;
import pl.javastart.equipy.repository.AssetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {

    private AssetRepository assetRepository;
    private AssetMapper assetMapper;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    public List<AssetDto> getAllAssets() {
        return assetRepository.findAll().stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AssetDto> findAllByNameOrSerialNumber(String text) {
        return assetRepository.findAllByNameOrSerialNumber(text).stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AssetDto> findById(Long id) {
        return assetRepository.findById(id)
                .map(assetMapper::toDto);
    }

    public AssetDto saveNewAsset(AssetDto assetDto) {
        assetRepository.findBySerialNumberIgnoreCase(assetDto.getSerialNumber())
                .ifPresent(asset ->
                {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Podany numer seryjny już istnieje");
                });
        return mapAndSaveAsset(assetDto);
    }

    public AssetDto updateAsset(AssetDto assetDto) {
        assetRepository.findBySerialNumberIgnoreCase(assetDto.getSerialNumber())
                .ifPresent(a ->
                {
                    if (!a.getId().equals(assetDto.getId())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Podany numer seryjny już istnieje");
                    }
                });
        return mapAndSaveAsset(assetDto);
    }

    private AssetDto mapAndSaveAsset(AssetDto asset) {
        Asset assetEntity = assetMapper.toEntity(asset);
        Asset savedAsset = assetRepository.save(assetEntity);
        return assetMapper.toDto(savedAsset);
    }

    public List<AssetAssignmentsDto> getAllAssetAssignments(Long id){
        return assetRepository.findById(id)
                .map(Asset::getAssignments)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "zasób nie istnieje"))
                .stream()
                .map(AssetAssignmentsMapper::toDto)
                .collect(Collectors.toList());
    }
}
