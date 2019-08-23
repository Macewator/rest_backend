package pl.javastart.equipy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javastart.equipy.model.AssetAssignmentsDto;
import pl.javastart.equipy.model.AssetDto;
import pl.javastart.equipy.service.AssetService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAssets(@RequestParam(required = false) String text){
        List<AssetDto> assets;
        if(text != null){
            assets = assetService.findAllByNameOrSerialNumber(text);
        }else{
            assets = assetService.getAllAssets();
        }
        return ResponseEntity.ok(assets);
    }

    @PostMapping
    public ResponseEntity<?> saveNewAsset(@RequestBody AssetDto asset){
        if(asset.getId() != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dodawany zasób nie może mieć ustawonego id");
        }
        AssetDto savedAsset = assetService.saveNewAsset(asset);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAsset.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAsset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAssetById(@PathVariable Long id){
        Optional<AssetDto> assetDto = assetService.findById(id);
        return assetDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Long id, @RequestBody AssetDto assetDto){
        if(!id.equals(assetDto.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "niepoprawne id przesłanego zasobu");
        }
        AssetDto updatedAsset = assetService.updateAsset(assetDto);
        return ResponseEntity.ok(updatedAsset);
    }

    @GetMapping(path = "/{assetId}/assignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetAssignmentsDto>> getAllAssetAssignments(@PathVariable Long assetId){
        return ResponseEntity.ok(assetService.getAllAssetAssignments(assetId));
    }
}
