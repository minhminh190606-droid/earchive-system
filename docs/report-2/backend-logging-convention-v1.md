# Backend Logging Convention v1

- Dự án: Hệ thống lưu trữ điện tử

- Ngày cập nhật: 07/10/2026

- Trạng thái: Đã chuẩn hóa log tại GlobalExceptionHandler và kiểm tra bằng MockMvc.

## 1. Mức log

| Mức | Cách sử dụng |
|---|---|
| INFO | Thông tin vận hành thông thường, như khởi động ứng dụng |
| WARN | Yêu cầu bị từ chối do lỗi 4xx |
| ERROR | Lỗi xử lý không dự kiến hoặc lỗi 5xx |
| DEBUG/TRACE | Không bật mặc định; chỉ dùng khi điều tra trong môi trường phát triển |

## 2. Log tại GlobalExceptionHandler

Lỗi 4xx:

request_rejected method=... status=... code=... exceptionType=...

Lỗi 5xx:

request_failed method=... status=... code=... exceptionType=...

Các trường:

- method: phương thức HTTP.

- status: HTTP status của lỗi.

- code: mã lỗi theo API Error Contract.

- exceptionType: tên lớp exception.

Handler không ghi request body, response body, header,

query string, đường dẫn tài nguyên, thông báo exception hoặc stack trace.

## 3. Cấu hình mặc định

- logging.level.root=INFO

- logging.level.com.team5.earchive=INFO

- spring.jpa.show-sql=false

- logging.level.org.hibernate.SQL=OFF

- logging.level.org.hibernate.orm.jdbc.bind=OFF

- logging.level.org.hibernate.orm.jdbc.extract=OFF

Log hiện được xuất ra console.

Chưa cấu hình lưu file, xoay vòng log hoặc hệ thống thu thập log.

## 4. Quy tắc dữ liệu

- Không ghi mật khẩu, token, cookie hoặc private key.

- Không ghi nội dung tài liệu hoặc giá trị tham số SQL.

- Không ghi toàn bộ DTO/request để tránh lộ dữ liệu.

- Khi bổ sung module, phải rà soát log trước khi bàn giao.

- Quy tắc của handler không bảo đảm mọi thư viện bên ngoài đều không ghi dữ liệu nhạy cảm.

## 5. Bằng chứng kiểm tra

File: logging-test-output.txt.

Đã quan sát:

- Validation 400: WARN, VALIDATION_ERROR.

- JSON sai 400: WARN, INVALID_REQUEST.

- Sai phương thức 405: WARN, METHOD_NOT_ALLOWED.

- Lỗi không dự kiến 500: ERROR, INTERNAL_ERROR.

GlobalExceptionHandlerTest:

- Tests run: 4.

- Failures: 0.

- Errors: 0.

- BUILD SUCCESS.

## 6. Giới hạn và phần tiếp theo

- Log tóm tắt không có stack trace nên hạn chế khả năng tìm nguyên nhân lỗi.

- Chưa có request ID để liên kết các log.

- Chưa có log nghiệp vụ vì các API nghiệp vụ chưa triển khai.

- Logging kỹ thuật không thay thế module Audit.

- Mức log 401/403/409 sẽ được áp dụng khi triển khai xử lý tương ứng.

