# API Error Contract v1

- Dự án: Hệ thống lưu trữ điện tử

- Ngày tạo: 01/10/2026

- Trạng thái: Thiết kế; chưa triển khai đầy đủ trong backend.

## 1. Cấu trúc phản hồi lỗi

Các API trả lỗi bằng JSON với những trường sau:

| Trường | Kiểu | Ý nghĩa |
|---|---|---|
| timestamp | string | Thời điểm lỗi theo UTC, định dạng ISO 8601 |
| status | integer | Mã HTTP, trùng với status của response |
| code | string | Mã lỗi ổn định để frontend xử lý |
| message | string | Thông báo dễ hiểu cho người dùng |
| path | string | Đường dẫn yêu cầu, không chứa query string |
| fieldErrors | array | Danh sách lỗi validation; rỗng nếu không có |

Mỗi phần tử fieldErrors gồm field và message.

Không trả lại giá trị đầu vào bị từ chối.

Ví dụ lỗi validation:

{

&#x20; "timestamp": "2026-10-01T03:00:00Z",

&#x20; "status": 400,

&#x20; "code": "VALIDATION_ERROR",

&#x20; "message": "Dữ liệu đầu vào không hợp lệ.",

&#x20; "path": "/api/documents",

&#x20; "fieldErrors": \[

&#x20;   {

&#x20;     "field": "title",

&#x20;     "message": "Tiêu đề không được để trống."

&#x20;   }

&#x20; ]

}

Đây là ví dụ thiết kế; API /api/documents chưa được triển khai.

## 2. Quy ước mã lỗi

| HTTP | code | Trường hợp |
|---|---|---|
| 400 | VALIDATION_ERROR | Dữ liệu không đạt ràng buộc validation |
| 400 | INVALID_REQUEST | JSON sai định dạng, sai kiểu hoặc thiếu tham số |
| 401 | UNAUTHORIZED | Chưa xác thực hoặc token không hợp lệ |
| 403 | FORBIDDEN | Không đủ vai trò hoặc quyền thực hiện thao tác |
| 404 | RESOURCE_NOT_FOUND | Tài nguyên không tồn tại hoặc không có VIEW |
| 404 | ENDPOINT_NOT_FOUND | Đường dẫn API không tồn tại |
| 405 | METHOD_NOT_ALLOWED | Phương thức HTTP không được hỗ trợ |
| 409 | CONFLICT | Xung đột dữ liệu hoặc trạng thái nghiệp vụ |
| 413 | PAYLOAD_TOO_LARGE | Nội dung upload vượt giới hạn cho phép |
| 415 | UNSUPPORTED_MEDIA_TYPE | Content-Type không được hỗ trợ |
| 500 | INTERNAL_ERROR | Lỗi không dự kiến trong hệ thống |

## 3. Quy tắc xử lý

- Frontend xử lý theo HTTP status và code, không dựa vào message.

- fieldErrors luôn là một mảng, kể cả khi rỗng.

- Không trả stack trace, câu SQL, mật khẩu, token hoặc đường dẫn file nội bộ.

- Lỗi 500 dùng thông báo chung: "Hệ thống gặp lỗi. Vui lòng thử lại sau."

- Chi tiết lỗi 500 được ghi trong log backend; không ghi mật khẩu hoặc token.

- Không tiết lộ sự tồn tại của tài nguyên khi người dùng không có VIEW.

- Phản hồi thành công giữ cấu trúc riêng của từng API.

## 4. Phạm vi triển khai

- Bổ sung DTO lỗi và Global Exception Handling trong đợt 2.

- Bổ sung xử lý 401 và 403 khi tích hợp Spring Security.

- Bổ sung xử lý lỗi upload khi triển khai chức năng upload.

- Chỉ đánh dấu hoàn thành từng loại lỗi sau khi kiểm thử.


