package kapsalon.nl.services;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.entity.Barber;
import kapsalon.nl.models.entity.ImageData;
import kapsalon.nl.repo.BarberRepository;
import kapsalon.nl.repo.ImageDataRepository;
import kapsalon.nl.repo.UserRepository;
import kapsalon.nl.utils.ImageUtil;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataService {
    private final ImageDataRepository imageDataRepository;
    private final UserRepository userRepository;

    private final BarberRepository barberRepository;
    public ImageDataService(ImageDataRepository imageDataRepository, UserRepository userRepository, BarberRepository barberRepository) {
        this.imageDataRepository = imageDataRepository;
        this.userRepository = userRepository;
        this.barberRepository = barberRepository;
    }

     public String uploadImage(MultipartFile multipartFile, Long id) throws IOException {
         Optional<Barber> barber = barberRepository.findById(id);
         Barber barber1 = barber.get();

         ImageData imageData = new ImageData();
         imageData.setName(multipartFile.getName());
         imageData.setType(multipartFile.getContentType());
         imageData.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
         imageData.setBarber(barber1);

         ImageData savedImage = imageDataRepository.save(imageData);
         barber1.setImageData(savedImage);
         barberRepository.save(barber1);
         return savedImage.getName();
     }


     public byte[] downloadImage(Long id) throws IOException{
        Optional<Barber> barber = barberRepository.findById(id);
        Barber barber1 = barber.get();
        ImageData imageData = barber1.getImageData();
        return ImageUtil.decompressImage(imageData.getImageData());
     }
//    public ResponseEntity<byte[]> downloadImage(Long id) {
//        Optional<ImageData> imageDataOptional = imageDataRepository.findById(id);
//
//        if (imageDataOptional.isPresent()) {
//            ImageData imageData = imageDataOptional.get();
//            byte[] imageDataBytes = imageData.getImageData();
//            String fileName = imageData.getName();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG); // of MediaType.IMAGE_PNG, afhankelijk van het type van de afbeelding
//            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
//
//            return ResponseEntity.ok().headers(headers).body(imageDataBytes);
//        } else {
//            throw new RecordNotFoundException("Image with id " + id + " not found.");
//        }
//    }

}
