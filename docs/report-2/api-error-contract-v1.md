# API Error Contract v1

* Dự án: Hệ thống lưu trữ điện tử
* Ngày tạo: 01/10/2026
* Trạng thái: Thiết kế; Đã triển khai DTO lỗi và Global Exception Handling; đã kiểm tra các trường hợp bên dưới. Các phần còn lại tiếp tục bổ sung.

## 1\. Cấu trúc phản hồi lỗi

Các API trả lỗi bằng JSON với những trường sau:

|Trường|Kiểu|Ý nghĩa|
|-|-|-|
|timestamp|string|Thời điểm lỗi theo UTC, định dạng ISO 8601|
|status|integer|Mã HTTP, trùng với status của response|
|code|string|Mã lỗi ổn định để frontend xử lý|
|message|string|Thông báo dễ hiểu cho người dùng|
|path|string|Đường dẫn yêu cầu, không chứa query string|
|fieldErrors|array|Danh sách lỗi validation; rỗng nếu không có|

Mỗi phần tử fieldErrors gồm field và message.

Không trả lại giá trị đầu vào bị từ chối.

Ví dụ lỗi validation:

{

&#x20; "timestamp": "2026-10-01T03:00:00Z",

&#x20; "status": 400,

&#x20; "code": "VALIDATION\_ERROR",

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

## 2\. Quy ước mã lỗi

|HTTP|code|Trường hợp|
|-|-|-|
|400|VALIDATION\_ERROR|Dữ liệu không đạt ràng buộc validation|
|400|INVALID\_REQUEST|JSON sai định dạng, sai kiểu hoặc thiếu tham số|
|401|UNAUTHORIZED|Chưa xác thực hoặc token không hợp lệ|
|403|FORBIDDEN|Không đủ vai trò hoặc quyền thực hiện thao tác|
|404|RESOURCE\_NOT\_FOUND|Tài nguyên không tồn tại hoặc không có VIEW|
|404|ENDPOINT\_NOT\_FOUND|Đường dẫn API không tồn tại|
|405|METHOD\_NOT\_ALLOWED|Phương thức HTTP không được hỗ trợ|
|409|CONFLICT|Xung đột dữ liệu hoặc trạng thái nghiệp vụ|
|413|PAYLOAD\_TOO\_LARGE|Nội dung upload vượt giới hạn cho phép|
|415|UNSUPPORTED\_MEDIA\_TYPE|Content-Type không được hỗ trợ|
|500|INTERNAL\_ERROR|Lỗi không dự kiến trong hệ thống|

## 3\. Quy tắc xử lý

* Frontend xử lý theo HTTP status và code, không dựa vào message.
* fieldErrors luôn là một mảng, kể cả khi rỗng.
* Không trả stack trace, câu SQL, mật khẩu, token hoặc đường dẫn file nội bộ.
* Lỗi 500 dùng thông báo chung: "Hệ thống gặp lỗi. Vui lòng thử lại sau."
* Chi tiết lỗi 500 được ghi trong log backend; không ghi mật khẩu hoặc token.
* Không tiết lộ sự tồn tại của tài nguyên khi người dùng không có VIEW.
* Phản hồi thành công giữ cấu trúc riêng của từng API.
* ## \## 4. Trạng thái triển khai và kiểm thử
* ## 
* ## Đã triển khai:
* ## \- DTO ApiErrorResponse và FieldErrorResponse.
* ## \- GlobalExceptionHandler cho lỗi Spring MVC và lỗi không dự kiến.
* ## 
* ## Đã kiểm tra khi chạy ứng dụng:
* ## \- GET /api/health trả HTTP 200.
* ## \- Đường dẫn không tồn tại trả HTTP 404, ENDPOINT\_NOT\_FOUND.
* ## \- POST /api/health trả HTTP 405, METHOD\_NOT\_ALLOWED và header Allow: GET.
* ## 
* ## Đã kiểm tra bằng 4 bài kiểm thử MockMvc độc lập:
* ## \- Tiêu đề trống trả 400, VALIDATION\_ERROR và fieldErrors.
* ## \- JSON sai định dạng trả 400, INVALID\_REQUEST.
* ## \- Sai phương thức trả 405 và header Allow.
* ## \- Lỗi không dự kiến trả 500, INTERNAL\_ERROR; response không lộ chi tiết nội bộ hoặc query string.
* ## 
* ## Bằng chứng:
* ## \- health-200.txt
* ## \- error-404.txt
* ## \- error-405.txt
* ## \- exception-handler-test-results.txt
* ## 
* ## Chưa hoàn thành:
* ## \- Xử lý 401 và 403 khi tích hợp Spring Security.
* ## \- Lỗi tài nguyên nghiệp vụ 404 và xung đột 409.
* ## \- Kiểm thử lỗi upload 413 và Content-Type 415.
* ## \- Validation trên các API nghiệp vụ thực tế.
* ## 
* ## Các endpoint /test/... chỉ thuộc mã kiểm thử.

