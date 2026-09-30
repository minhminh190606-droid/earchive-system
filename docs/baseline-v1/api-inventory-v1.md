\# API Inventory v1



\- Dự án: Hệ thống lưu trữ điện tử

\- Người thực hiện: Trần Quyền Minh

\- Đợt: Báo cáo 1 — 27/09/2026 đến 03/10/2026

\- Ngày cập nhật: 30/09/2026

\- Phạm vi: API đã triển khai trong backend hiện tại.



\## 1. Tổng quan



Backend hiện có 1 endpoint do nhóm tự viết: GET /api/health.

Endpoint này kiểm tra khả năng phản hồi HTTP của backend,

không truy vấn database và không kiểm tra toàn bộ hệ thống.



Swagger UI đã gọi endpoint thành công, nhận HTTP 200 và JSON.



\## 2. Danh sách API hiện có



| Thuộc tính | Giá trị |

|---|---|

| Module | Kiểm tra trạng thái backend |

| Controller | HealthController |

| Phương thức Java | health() |

| HTTP method | GET |

| Path | /api/health |

| URL local | http://localhost:8080/api/health |

| Path parameter | Không có |

| Query parameter | Không có |

| Request body | Không có |

| Xác thực | Chưa yêu cầu đăng nhập hoặc token |

| Phân quyền | Chưa triển khai kiểm soát quyền |

| HTTP status thành công | 200 OK |

| Response Content-Type | application/json |

| Truy vấn database | Không |

| Ghi hoặc đọc file | Không |



\## 3. Response thực tế



```json

{

&#x20; "status": "UP",

&#x20; "message": "Backend hệ thống lưu trữ điện tử đang hoạt động",

&#x20; "application": "earchive"

}

```



| Trường | Kiểu | Ý nghĩa |

|---|---|---|

| status | String | Giá trị UP được trả về khi phương thức xử lý thành công |

| message | String | Thông báo backend đang hoạt động |

| application | String | Tên ứng dụng earchive |



Thứ tự các trường JSON không ảnh hưởng ý nghĩa phản hồi.



Response hiện được tạo bằng Map<String, String>.

Chưa có DTO riêng cho phản hồi này.



\## 4. Endpoint phục vụ tài liệu



Các đường dẫn sau do thư viện springdoc cung cấp,

không tính là API nghiệp vụ do nhóm tự viết.



| Method | Path | Mục đích |

|---|---|---|

| GET | /v3/api-docs | Xuất tài liệu OpenAPI dạng JSON |

| GET | /swagger-ui/index.html | Mở giao diện Swagger UI |

| GET | /v3/api-docs/swagger-config | Cấu hình phục vụ Swagger UI |



Swagger còn sử dụng các tài nguyên tĩnh phụ trợ.

Bảng trên chỉ liệt kê các đường dẫn tài liệu chính.



\## 5. Phần chưa triển khai



Chưa có endpoint nghiệp vụ cho:



\- Đăng nhập, token và phân quyền.

\- Quản lý người dùng và quyền truy cập.

\- Quản lý hồ sơ, metadata và tài liệu.

\- Upload, preview và download.

\- Quản lý phiên bản và restore.

\- Search và Saved Search.

\- Audit và Statistics.

\- OCR.

\- Chữ ký số và kiểm tra tính toàn vẹn.



Tên endpoint, DTO, permission và mã lỗi cho các chức năng

trên chưa được chốt. Cần đối chiếu yêu cầu BA, giao diện

frontend và thiết kế database trong đợt tiếp theo.



Chưa có quy ước phản hồi lỗi chung hoặc xử lý thống nhất

các mã 401, 403, 404, 409.



\## 6. Bằng chứng



\- Source: backend/src/main/java/com/team5/earchive/controller/HealthController.java

\- OpenAPI: docs/baseline-v1/openapi.json

\- Ảnh thực thi: docs/baseline-v1/swagger-health-200.png



Kết quả đã quan sát: GET /api/health trả HTTP 200,

Content-Type application/json và status UP.

