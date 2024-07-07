package Itstime.planear.statusmessage.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.statusmessage.dto.StatusCreateRequest;
import Itstime.planear.statusmessage.dto.StatusResponse;
import Itstime.planear.statusmessage.service.StatusMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StatusMessageController {
    private final StatusMessageService statusMessageService;

    @GetMapping("/status")
    public ApiResponse<StatusResponse> getStatus(
            @RequestHeader(name = "user-no", required = false) Long memberId
    ) {
        return ApiResponse.success(statusMessageService.getCurrentStatus(memberId));
    }

    @PostMapping("/status")
    public ApiResponse<Object> saveStatus(
            @RequestBody StatusCreateRequest request,
            @RequestHeader(name = "user-no") Long memberId
    ) {
        statusMessageService.createStatusMessage(request, memberId);
        return ApiResponse.success(null);
    }
}
