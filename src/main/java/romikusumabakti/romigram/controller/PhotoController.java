package romikusumabakti.romigram.controller;

import org.imgscalr.Scalr;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import romikusumabakti.romigram.model.Account;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @PostMapping()
    public String saveUser(@RequestParam("photo") MultipartFile photo, @AuthenticationPrincipal Account account) throws IOException {

        String photoName = account.getId() + "-" + Instant.now().toEpochMilli();

        Path path = Paths.get("src/main/resources/static/photos/temp/" + photoName + ".jpg");
        Files.write(path, photo.getBytes());

        BufferedImage inputImage = ImageIO.read(path.toFile());

        int size, x = 0, y = 0;
        if (inputImage.getWidth() > inputImage.getHeight()) {
            size = inputImage.getHeight();
            x = (inputImage.getWidth() - size) / 2;
        } else {
            size = inputImage.getWidth();
            y = (inputImage.getHeight() - size) / 2;
        }

        BufferedImage croppedImage = Scalr.crop(inputImage, x, y, size, size);
        inputImage.flush();

        BufferedImage resizedImage = Scalr.resize(croppedImage, 512);
        croppedImage.flush();

        ImageIO.write(resizedImage, "jpg", path.toFile());
        resizedImage.flush();

        return photoName;

    }

}
