# Backend Architecture Baseline v1



- Dự án: Hệ thống lưu trữ điện tử

- Thành viên: Trần Quyền Minh

- Vai trò: Backend Developer / Technical Lead

- Đợt báo cáo: 27/09/2026 đến 03/10/2026

- Ngày ghi nhận hiện trạng: 30/09/2026



## 1. Mục đích



Ghi nhận kiến trúc và các thành phần backend hiện có,

phân biệt phần đã triển khai với kiến trúc dự kiến.



Backend hiện ở giai đoạn khởi tạo: đã chạy ứng dụng,

kết nối PostgreSQL và cung cấp API kiểm tra trạng thái.

Các module nghiệp vụ chưa được triển khai.



## 2. Công nghệ hiện có



| Thành phần | Công nghệ và cấu hình |
|---|---|
| Ngôn ngữ | Java 21 |
| Framework | Spring Boot 3.5.16 |
| API | Spring Web MVC, JSON |
| Truy cập dữ liệu | Spring Data JPA, Hibernate; đã cấu hình, chưa có Entity/Repository nghiệp vụ |
| Database | PostgreSQL 17 chạy trong Docker |
| Database local | earchive\_db |
| Container | earchive-postgres |
| Tài liệu API | springdoc-openapi 2.8.17, Swagger UI |
| Build | Maven Wrapper |
| Kiểm thử | spring-boot-starter-test |
| Quản lý mã nguồn | Git và GitHub |
| Môi trường chạy | Backend chạy trên máy phát triển; PostgreSQL chạy trong Docker |



Spring Boot đã được chuyển từ 4.1.1 sang 3.5.16

theo công nghệ nhóm chốt. Thay đổi đã được gộp vào main

qua Pull Request #1.



## 3. Kiến trúc đang hoạt động



Luồng của API GET /api/health:



1\. Trình duyệt hoặc Swagger UI gửi HTTP GET đến backend.

2\. Spring MVC chuyển yêu cầu đến HealthController.health().

3\. Controller tạo Map chứa status, message và application.

4\. Spring chuyển kết quả thành JSON và trả HTTP 200.



Luồng này không gọi Service, Repository, database

hoặc File Storage.



Kết nối PostgreSQL được khởi tạo khi ứng dụng khởi động.

Log trước đó đã xác nhận kết nối thành công.



## 4. Kiến trúc mục tiêu



Backend dự kiến tổ chức theo Modular Monolith và kiến trúc

nhiều tầng:



- Controller: nhận request và trả response.

- DTO: mô tả dữ liệu vào/ra API.

- Service: xử lý nghiệp vụ và điều phối.

- Repository: truy cập dữ liệu.

- Entity: ánh xạ dữ liệu với bảng PostgreSQL.

- File Storage: lưu nội dung file tài liệu.

- Security: xác thực và kiểm tra quyền truy cập.



Luồng nghiệp vụ dự kiến:

Controller → Service → Repository → PostgreSQL.



Các nghiệp vụ tài liệu sẽ tích hợp File Storage qua

thành phần xử lý lưu trữ file.



Đây là kiến trúc mục tiêu; hiện chưa có luồng nghiệp vụ

hoàn chỉnh chạy qua tất cả các tầng.



## 5. Kiểm kê source và package hiện có



Danh sách file Java đã kiểm tra trong src/main/java:



| Package | Lớp | Trách nhiệm |
|---|---|---|
| com.team5.earchive | EarchiveApplication | Khởi động Spring Boot |
| com.team5.earchive.controller | HealthController | Xử lý GET /api/health |



Chưa ghi nhận lớp Java trong các tầng Service, Repository,

Entity, DTO, Config hoặc Exception.



Package trống nếu có không được tính là module đã triển khai.



## 6. Backend Module Inventory



| Module | Hiện trạng | Phần còn thiếu |
|---|---|---|
| Health | Đã có GET /api/health | Chỉ kiểm tra phản hồi backend, chưa kiểm tra các thành phần phụ thuộc |
| Auth/Security | Chưa triển khai | Login, JWT và bảo vệ endpoint |
| User \& Access Control | Chưa triển khai | User, role, permission, RBAC/ACL |
| Document/Record | Chưa triển khai | Hồ sơ, metadata, upload, preview, download và storage |
| Document Versioning | Chưa triển khai | Tạo phiên bản, danh sách phiên bản và restore |
| Search/Saved Search | Chưa triển khai | Tìm kiếm, lọc, sắp xếp, phân trang và lưu điều kiện |
| Audit | Chưa triển khai | Ghi nhận truy cập và sử dụng tài liệu |
| Statistics | Chưa triển khai | Tổng hợp số liệu cho dashboard |
| OCR | Chưa triển khai | Xử lý OCR và quy tắc fallback theo yêu cầu BA |
| Signature/Integrity | Chưa triển khai | SHA-256, RSA, X.509 và kiểm tra toàn vẹn |
| API Documentation | Swagger đã chạy trên máy | Hoàn thiện tài liệu và đưa baseline lên GitHub |
| Error Handling | Chưa có xử lý lỗi chung tự viết | Global Exception Handling và cấu trúc phản hồi lỗi |



## 7. Cấu hình và dữ liệu



- compose.yaml khai báo PostgreSQL và volume lưu dữ liệu.

- PostgreSQL được công bố tại 127.0.0.1:5432 trên máy phát triển.

- Backend chạy tại cổng 8080.

- .env chứa thông tin cấu hình riêng trên máy.

- .gitignore loại trừ .env.

- .env.example cung cấp cấu hình mẫu để thành viên tự thiết lập.

- application.properties đọc cấu hình từ .env.

- spring.jpa.hibernate.ddl-auto=validate: kiểm tra ánh xạ

&#x20; Entity với database, không tự tạo hoặc sửa bảng.



Chưa có Entity nghiệp vụ trong source đã kiểm tra.

Chưa nhận được ERD hoặc script SQL nghiệp vụ đã chốt

từ thành viên phụ trách database.



Chưa có File Storage nghiệp vụ.

Volume của PostgreSQL chỉ phục vụ dữ liệu database,

không thay thế kho lưu file tài liệu.



## 8. Kiểm thử và bằng chứng hiện có



- Maven clean verify đã báo BUILD SUCCESS sau khi chuyển

&#x20; sang Spring Boot 3.

- API GET /api/health đã trả JSON với status UP.

- Swagger UI thực thi GET /api/health nhận HTTP 200.

- Đã có file xuất OpenAPI và ảnh thực thi trong baseline-v1.



BUILD SUCCESS nêu trên thuộc lần kiểm tra chuyển phiên bản.

Không xem đây là bằng chứng đã kiểm thử toàn bộ chức năng

nghiệp vụ hoặc các module bảo mật chưa triển khai.



## 9. Điểm chưa hoàn chỉnh cần xử lý



1\. Chưa triển khai các tầng nghiệp vụ Service/Repository/Entity.

2\. Chưa có thiết kế database nghiệp vụ để đối chiếu JPA.

3\. Chưa triển khai xác thực và phân quyền.

4\. Chưa có DTO và quy ước phản hồi lỗi chung.

5\. Chưa có File Storage hoặc nghiệp vụ quản lý tài liệu.

6\. Chưa có API nghiệp vụ để đối chiếu với frontend và UML.

7\. Cần chốt actor, permission, business rule và phạm vi

&#x20;  chi tiết với BA trước khi triển khai.

8\. Cần commit/push và bàn giao tài liệu Swagger/baseline.



## 10. Tài liệu liên quan



- README.md: hướng dẫn chạy backend.

- docs/baseline-v1/api-inventory-v1.md: kiểm kê API.

- docs/baseline-v1/openapi.json: tài liệu OpenAPI đã xuất.

- docs/baseline-v1/swagger-health-200.png: bằng chứng thực thi.


