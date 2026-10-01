package com.team5.earchive.controller;

import com.team5.earchive.dto.HealthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Kiểm tra trạng thái ứng dụng")
public class HealthController {

    @Operation(
            operationId = "getApplicationHealth",
            summary = "Kiểm tra backend đang hoạt động",
            description = "Trả trạng thái ứng dụng. "
                    + "Endpoint này không truy vấn database "
                    + "và không xác nhận trạng thái các dịch vụ phụ thuộc."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ứng dụng phản hồi thành công",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = HealthResponse.class)
            )
    )
    @GetMapping(
            value = "/api/health",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                "Backend hệ thống lưu trữ điện tử đang hoạt động",
                "earchive"
        );
    }
}