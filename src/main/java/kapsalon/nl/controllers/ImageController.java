package kapsalon.nl.controllers;

import kapsalon.nl.models.entity.Barber;
import kapsalon.nl.models.entity.ImageData;
import kapsalon.nl.repo.BarberRepository;
import kapsalon.nl.repo.ImageDataRepository;
import kapsalon.nl.services.ImageDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/images")

public class ImageController {

    private final ImageDataService imageDataService;

    private  final ImageDataRepository imageDataRepository;
    private  final BarberRepository barberRepository;
    public ImageController(ImageDataService imageDataService, ImageDataRepository imageDataRepository, BarberRepository barberRepository) {
        this.imageDataService = imageDataService;
        this.imageDataRepository = imageDataRepository;
        this.barberRepository = barberRepository;
    }


    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile multipartFile, @RequestParam Long id) throws IOException {
        String image = imageDataService.uploadImage(multipartFile, id);
        return  ResponseEntity.ok("file has been uploaded " + image);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Object> downloadImage(@PathVariable("id") Long id) throws IOException{
        byte[] image = imageDataService.downloadImage(id);
        Optional<Barber> barber = barberRepository.findById(id);
        Optional<ImageData> dbImageData = imageDataRepository.findById(barber.get().getImageData().getId());
        MediaType mediaType = MediaType.valueOf(dbImageData.get().getType());
        return  ResponseEntity.ok().contentType(mediaType).body(image);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
//        return imageDataService.downloadImage(id);
//    }
}
