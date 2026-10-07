\# Báo cáo tiến độ 2 Backend



\- Dự án: Hệ thống lưu trữ điện tử

\- Thành viên: Trần Quyền Minh

\- Vai trò: Backend Developer / Technical Lead

\- Đợt: 04/10/2026 đến 10/10/2026

\- Ngày cập nhật: 07/10/2026

\- Trạng thái: Bản tiến độ để bàn giao kiểm tra; chưa hoàn thành đối chiếu frontend và Sequence Diagram.



\## 1. Phần được giao trong đợt



\- Chuẩn hóa API contract: DTO, validation, naming, HTTP status và error response.

\- Chuẩn hóa security rule theo Actor/Role/Permission; phân biệt 401, 403, 404, 409.

\- Đối chiếu API với màn hình frontend và Sequence Diagram, xử lý endpoint dư/thiếu.

\- Chuẩn hóa Global Exception Handling và logging backend.



\## 2. Phần đã hoàn thành



\### API Contract



\- Lập Business API Contract v1 cho các module nghiệp vụ dự kiến.

\- Quy định trường dữ liệu, validation, phân trang, upload/download và HTTP status ở mức thiết kế.

\- Thay Map bằng HealthResponse DTO cho API health đang triển khai.

\- Khai báo application/json, mô tả, ví dụ và trường bắt buộc trong Swagger.



\### Security Matrix



\- Chốt thiết kế ADMIN, ARCHIVIST, USER và các quyền tài nguyên.

\- Chốt quy tắc kết hợp role với ACL.

\- Phân biệt 401 UNAUTHORIZED, 403 FORBIDDEN, 404 RESOURCE\_NOT\_FOUND và 409 CONFLICT.

\- Backend/Technical Lead chốt theo ủy quyền của BA.

\- Chưa triển khai xác thực và phân quyền trong code.



\### Error Response và Global Exception Handling



\- Có ApiErrorResponse và FieldErrorResponse.

\- Có GlobalExceptionHandler.

\- Chuẩn hóa timestamp, status, code, message, path và fieldErrors.

\- Kiểm tra thực tế API health 200, endpoint không tồn tại 404 và sai phương thức 405.

\- Bốn kiểm thử MockMvc đạt: validation 400, JSON sai 400, sai phương thức 405 và lỗi 500.

\- Kiểm thử 500 xác nhận response không lộ chi tiết exception hoặc query string.



\### Logging



\- Đặt mức log mặc định INFO.

\- Tắt in SQL và các nhóm log giá trị tham số JDBC.

\- Handler dùng WARN cho lỗi 4xx và ERROR cho lỗi 5xx.

\- Log handler chỉ chứa method, status, code và exceptionType.

\- Đã lưu bằng chứng kiểm tra các dòng log.

\- Log hiện ở console; chưa có request ID hoặc hệ thống thu thập log.



\### Swagger và OpenAPI



\- Đặt tên EArchive Backend API, phiên bản v1.

\- Swagger mô tả đúng API health đang triển khai.

\- Xuất openapi-v1.json.

\- Maven clean verify thành công tại bước chuẩn hóa health/OpenAPI.

\- Sau thay đổi logging, đã chạy lại bốn kiểm thử handler và đạt.



\### Đối chiếu API



\- Đã lập bảng API Alignment v1.

\- Chưa nhận màn hình frontend và Sequence Diagram.

\- Chưa hoàn thành đối chiếu hoặc xác định endpoint dư/thiếu.



\## 3. File source và bằng chứng bàn giao



Repository:

https://github.com/minhminh190606-droid/earchive-system



Thư mục tài liệu:

docs/report-2/



| Thành phần | File |

|---|---|

| API Contract nghiệp vụ dự kiến | business-api-contract-v1.md |

| Security Matrix đã chốt thiết kế | security-matrix-v1.md |

| Error Response Convention | api-error-contract-v1.md |

| Quy ước logging | backend-logging-convention-v1.md |

| Danh sách chờ đối chiếu | api-alignment-v1.md |

| OpenAPI từ code đang chạy | openapi-v1.json |

| Bằng chứng HTTP 200 | health-200.txt |

| Bằng chứng HTTP 404 | error-404.txt |

| Bằng chứng HTTP 405 | error-405.txt |

| Kết quả kiểm thử handler ban đầu | exception-handler-test-results.txt |

| Kết quả kiểm thử tại bước chuẩn hóa OpenAPI | api-contract-test-results.txt |

| Kết quả kiểm thử và log sau thay đổi logging | logging-test-output.txt |

| Báo cáo tổng hợp | report-2.md |



Source nằm trong backend/:

\- DTO HealthResponse, ApiErrorResponse, FieldErrorResponse.

\- HealthController.

\- GlobalExceptionHandler.

\- OpenApiConfig.

\- GlobalExceptionHandlerTest.

\- application.properties.



Repository riêng tư; người kiểm tra cần quyền truy cập.



\## 4. Vướng mắc và giới hạn



\- Chưa có màn hình frontend và Sequence Diagram để đối chiếu.

\- API nghiệp vụ mới ở mức thiết kế; chưa có Controller/Service/Repository tương ứng.

\- Validation nghiệp vụ mới được quy định trong contract.

\- Kiểm thử validation sử dụng controller trong src/test, không phải API nghiệp vụ chạy thật.

\- 401/403, 404 tài nguyên theo ACL và 409 nghiệp vụ chưa triển khai.

\- 404 đã chạy là ENDPOINT\_NOT\_FOUND, không phải kiểm tra quyền VIEW.

\- Swagger hiện chỉ có health; không trình bày API dự kiến như API đã triển khai.

\- Cần đối chiếu kiểu ID, metadata và quan hệ bảng với ERD/SQL trước khi viết Entity.

\- Log tóm tắt không có stack trace nên hạn chế việc điều tra nguyên nhân.



\## 5. Phần cần bổ sung và chuyển tiếp



Cần hoàn tất trong phạm vi đợt 2:

\- Nhận màn hình frontend và Sequence Diagram.

\- Đối chiếu API, ghi mismatch và cập nhật contract.

\- Xác nhận các điểm tích hợp với database.

\- Cập nhật báo cáo và trạng thái bàn giao sau khi kiểm tra.



Phần triển khai ở đợt tiếp theo:

\- Authentication/JWT và kiểm tra role/ACL.

\- Document Core và Versioning.

\- Áp dụng DTO, validation và mã lỗi vào API nghiệp vụ.

\- Bổ sung kiểm thử các luồng thành công, lỗi và quyền truy cập.



Đề nghị BA kiểm tra phần đã bàn giao và ghi nhận phần đối chiếu đang chờ.

Báo cáo không xác nhận đã hoàn thành toàn bộ đợt 2 hoặc đã được nghiệm thu.

