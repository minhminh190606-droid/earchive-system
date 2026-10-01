package com.team5.earchive.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Phản hồi kiểm tra ứng dụng backend")
public record HealthResponse(
        @Schema(
                description = "Trạng thái ứng dụng",
                example = "UP",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String status,

        @Schema(
                description = "Thông báo trạng thái",
                example = "Backend hệ thống lưu trữ điện tử đang hoạt động",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String message,

        @Schema(
                description = "Tên ứng dụng",
                example = "earchive",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String application
) {
}