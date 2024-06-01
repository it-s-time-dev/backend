package Itstime.planear.shop.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.config.MapperConfig;
import Itstime.planear.shop.dto.request.S3UploadRequestDto;
import Itstime.planear.shop.dto.response.S3UploadResponseDto;
import Itstime.planear.shop.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
@Tag(name = "S3 서비스 API 모음", description = "이미지 관리 및 조회 편의성을 위해 개발한 API 입니다.")
public class S3Controller {

    private final S3Service s3Service;
    private final ObjectMapper objectMapper;

    @PostMapping("/upload")
    @Operation(summary = "S3 이미지 업로드 (다중 이미지 업로드 가능)", description = "bodyPart : 부위명 ex) 'TOP', 'HAIR'.., itemId : 아이템 고유ID")
    public ApiResponse<S3UploadResponseDto> uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") S3UploadRequestDto dto
            ){
        return s3Service.uploadFile(multipartFile, dto);
    }

    @PostMapping("/upload2")
    @Operation(summary = "S3 이미지 업로드 (다중 이미지 업로드 가능)", description = "bodyPart : 부위명 ex) 'TOP', 'HAIR'.., itemId : 아이템 고유ID")
    public ApiResponse<List<S3UploadResponseDto>> uploadFileList(
            @RequestPart(value = "file") List<MultipartFile> multipartFileList,
            @RequestParam(value = "dtos") String dtosJson
    ) throws JsonProcessingException {
        List<S3UploadRequestDto> dtoList = objectMapper.readValue(dtosJson, new TypeReference<List<S3UploadRequestDto>>(){});
        List<S3UploadResponseDto> responseDtoList = s3Service.uploadFiles(multipartFileList, dtoList);
        return ApiResponse.success(responseDtoList);
    }

}
