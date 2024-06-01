package Itstime.planear.shop.service;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.shop.dto.request.S3UploadRequestDto;
import Itstime.planear.shop.dto.response.S3UploadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Itstime.planear.shop.utils.AwsCommonUtils.buildFileName;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ApiResponse<S3UploadResponseDto> uploadFile(MultipartFile multipartFile, S3UploadRequestDto dto){
        validateFileExists(multipartFile); // 업로드 파일 유효성 검증
        String fileName = buildFileName(dto.itemId(), multipartFile.getOriginalFilename());
        String filePath = dto.bodyPart() + "/" + fileName;  // 부위별로 폴더 구조를 생성을 위해

        try (InputStream inputStream = multipartFile.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .acl("public-read")
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));

        }catch (IOException e){
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(filePath)
                .build();
        return ApiResponse.success(new S3UploadResponseDto(s3Client.utilities().getUrl(getUrlRequest).toString())) ;
    }

    private void validateFileExists(MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            log.error("파일이 없습니다.");
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }
    }

    public List<S3UploadResponseDto> uploadFiles(List<MultipartFile> multipartFileList, List<S3UploadRequestDto> dtoList) {
        if (multipartFileList.size() != dtoList.size()) {
            throw new PlanearException("파일 수와 DTO 수가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return IntStream.range(0, multipartFileList.size())
                .mapToObj(i -> uploadFileTest(multipartFileList.get(i), dtoList.get(i)))
                .collect(Collectors.toList());
    }

    public S3UploadResponseDto uploadFileTest(MultipartFile multipartFile, S3UploadRequestDto dto){
        validateFileExists(multipartFile); // 업로드 파일 유효성 검증
        String fileName = buildFileName(dto.itemId(), multipartFile.getOriginalFilename());
        String filePath = dto.bodyPart() + "/" + fileName;  // 부위별로 폴더 구조를 생성을 위해

        try (InputStream inputStream = multipartFile.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .acl("public-read")
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));

        } catch (IOException e) {
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.BAD_REQUEST);
        }

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(filePath)
                .build();
        return new S3UploadResponseDto(s3Client.utilities().getUrl(getUrlRequest).toString());
    }
}
