package Itstime.planear.shop.utils;

public class AwsCommonUtils {
    public static final String FILE_EXTENSION_SEPARATOR = ".";
    public static String buildFileName(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR); // 확장자 시작 인덱스
        String fileExtension = originalFileName.substring(fileExtensionIndex); // ex) ".jpg", ".jpeg", ".png"
        String fileName = originalFileName.substring(0, fileExtensionIndex); // 파일명
        return fileName + fileExtension; // ex) 1_모자1.jpg
    }

    public static String getFileName(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        return originalFileName.substring(0, fileExtensionIndex); //파일 이름
    }
}
