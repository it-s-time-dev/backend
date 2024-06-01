package Itstime.planear.shop.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.shop.dto.request.S3UploadRequestDto;
import Itstime.planear.shop.dto.response.S3UploadResponseDto;
import Itstime.planear.shop.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
@Tag(name = "S3 서비스 API 모음", description = "이미지 관리 및 조회 편의성을 위해 개발한 API 입니다.")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    @Operation(summary = "S3 이미지 업로드", description = "bodyPart : 부위명 ex) 'TOP', 'HAIR'.., itemId : 아이템 고유ID")
    public ApiResponse<S3UploadResponseDto> uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") S3UploadRequestDto dto
            ){
        return s3Service.uploadFile(multipartFile, dto);
    }
}
