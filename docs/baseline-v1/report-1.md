# Báo cáo tiến độ 1 — Backend







- Dự án: Hệ thống lưu trữ điện tử



- Thành viên: Trần Quyền Minh



- Vai trò: Backend Developer / Technical Lead



- Đợt: 27/09/2026 đến 03/10/2026



- Ngày cập nhật: 30/09/2026



- Trạng thái: Đã đưa source và tài liệu lên main, sẵn sàng bàn giao; chưa xác nhận BA nghiệm thu.





## 1. Phần được giao trong đợt







- Rà soát package/module backend hiện có.



- Kiểm kê REST endpoint và contract hiện trạng.



- Ghi nhận kiến trúc và điểm chưa hoàn chỉnh.



- Kiểm tra hiện trạng các module trong phạm vi phân công.



- Xuất Swagger/OpenAPI baseline.



- Lập danh sách vấn đề và phần việc chuyển sang đợt tiếp theo.







## 2. Phần đã hoàn thành







- Khởi tạo backend Java 21 và kết nối PostgreSQL chạy bằng Docker.



- Chuyển Spring Boot từ 4.1.1 sang 3.5.16 theo công nghệ nhóm chốt.



- Gộp thay đổi phiên bản vào main qua Pull Request #1.



- Tách cấu hình riêng vào .env và cung cấp .env.example.



- Bổ sung README hướng dẫn chạy.



- Có API GET /api/health trả JSON và HTTP 200.



- Maven clean verify thành công ở bước chuyển Spring Boot 3.



- Bổ sung Swagger UI và gọi thử API health thành công.



- Lưu OpenAPI JSON và ảnh bằng chứng thực thi.



- Lập tài liệu kiến trúc, kiểm kê API và danh sách phần còn thiếu.







Các module nghiệp vụ chưa triển khai được ghi rõ trong



Backend Module Inventory, không tính là đã hoàn thành.







## 3. File/source/evidence bàn giao







Repository:



https://github.com/minhminh190606-droid/earchive-system







| Thành phần | Đường dẫn |
|---|---|
| Source backend | backend/ |
| Hướng dẫn chạy | README.md |
| Kiến trúc và kiểm kê module | docs/baseline-v1/backend-architecture-baseline-v1.md |
| Kiểm kê API | docs/baseline-v1/api-inventory-v1.md |
| OpenAPI baseline | docs/baseline-v1/openapi.json |
| Ảnh thực thi API HTTP 200 | docs/baseline-v1/swagger-health-200.png |
| Vấn đề và bước tiếp theo | docs/baseline-v1/issues-and-next-steps-v1.md |
| Báo cáo tổng hợp | docs/baseline-v1/report-1.md |







Source Swagger và bộ tài liệu baseline đã được gộp vào main

qua Pull Request #2 ngày 30/09/2026.



Pull Request: https://github.com/minhminh190606-droid/earchive-system/pull/2



Bộ tài liệu sẵn sàng gửi BA; chưa xác nhận BA nghiệm thu.







## 4. Vướng mắc hoặc cần BA xác nhận







- Chưa nhận ERD/script SQL nghiệp vụ đã chốt.



- Cần thống nhất actor, role, permission và business rule.



- Cần đối chiếu API dự kiến với màn hình frontend và UML.



- Cần làm rõ phạm vi chi tiết OCR và Signature/Integrity.







## 5. Phần chuyển sang đợt tiếp theo







- Chuẩn hóa API Contract v1 và DTO request/response.



- Chuẩn hóa validation, naming, HTTP status và error response.



- Lập Security Matrix v1 theo yêu cầu BA.



- Bổ sung Global Exception Handling và chuẩn hóa logging.



- Đối chiếu API với frontend, database và Sequence Diagram.



- Đồng bộ Swagger với code và contract đã thống nhất.






