package com.dailycodework.demoshops.controller;


import com.dailycodework.demoshops.dto.ImageDto;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Image;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.image.IImageService;
import com.dailycodework.demoshops.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping( "${api.prefix}/images") // here we are put the value which we declared in application properties

public class ImageController {

    @Autowired
    private final ImageService imageService;


    @PostMapping("/Upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
// here we are used try and catch block for catching error which occurs when you want to get information and this information controller
        // did not find .
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);

            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));

        }
        catch (Exception e) {

//            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("UploadFailed!", e.getMessage()));
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("UploadFailed!", e.getMessage()));
        }

    }


    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {

        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource =  new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
        // here we just create a ByteArray from the Blob and we created a ByteArrayResource then we
        // return the resource as a file which is just set the attachment everything so this the one of the flexible way
        // to actually images in RestApi in any frontend we are using right here;


    }


    // now here we are creating the update the image

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {

        try {
            Image image = imageService.getImageById(imageId);

            if (image != null) {
                imageService.updateImage(file, imageId);

                return ResponseEntity.ok(new ApiResponse("Update success!", null));

            }
        }
        catch (ResourceNotFoundException e)
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

            }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));



    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {

        try {
            Image image = imageService.getImageById(imageId);

            if (image != null) {
                imageService.deleteImageById(imageId);

                return ResponseEntity.ok(new ApiResponse("Delete success!", null));

            }
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));



    }

    // Now here we are done with imageController implemented;



}
