package com.dailycodework.demoshops.service.image;


import com.dailycodework.demoshops.dto.ImageDto;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.Image;
import com.dailycodework.demoshops.model.Product;
import com.dailycodework.demoshops.repository.ImageRepository;
import com.dailycodework.demoshops.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class ImageService implements IImageService {

    private  final ImageRepository imageRepository;
    private  final ProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("No image found with id :  " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository:: delete,  () -> {
                    throw new ResourceNotFoundException("NO image found with id: " + id);
                });

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
      // get the image
        Product product =   productService.getProductById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();

        for(MultipartFile file : files) {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl +image.getId();
                image.setDownloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);

                saveImage.setDownloadUrl(buildDownloadUrl +saveImage.getId());
// so this one enables us to get the correct ID of the saved image so that when we want
                // to download it we don't have an error;
                imageRepository.save(saveImage);

// we are going to call ImageDtos to actually get the properties we want to return  back to the front
                // end;
                ImageDto imageDto = new ImageDto();
                 imageDto.setId(saveImage.getId());
                 imageDto.setFileName(saveImage.getFileName());
                 imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                 // save to the list
                savedImageDtos.add(imageDto);

            }
            catch (IOException | SQLException e) {

                throw new RuntimeException(e.getMessage());

            }
        }
        return  savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
// so first we will find this image by id;
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (IOException | SQLException e)
        {
            throw    new RuntimeException(e.getMessage());
        }

    }

    public  List<Image> findByProductId(Long id)
    {
        return imageRepository.findByProductId(id);
    }



}
