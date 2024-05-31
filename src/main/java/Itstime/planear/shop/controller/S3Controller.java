package Itstime.planear.shop.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.shop.dto.request.S3UploadRequestDto;
import Itstime.planear.shop.dto.response.ItemListResponseDto;
import Itstime.planear.shop.dto.response.S3UploadResponseDto;
import Itstime.planear.shop.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ApiResponse<S3UploadResponseDto> uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") S3UploadRequestDto dto
            ){
        return s3Service.uploadFile(multipartFile, dto);
    }
}
