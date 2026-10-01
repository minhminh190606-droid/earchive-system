# Danh sách vấn đề và bước tiếp theo v1



- Thành viên: Trần Quyền Minh

- Ngày cập nhật: 30/09/2026

- Phạm vi: Backend — Báo cáo 1



## 1. Vấn đề đã xử lý



| Mã | Vấn đề | Kết quả |
|---|---|---|
| FIX-01 | Backend ban đầu dùng Spring Boot 4.1.1, khác yêu cầu nhóm | Đã chuyển sang 3.5.16, build/test thành công và gộp PR #1 vào main |



## 2. Việc cần xử lý trong Báo cáo 2



| Mã | Hiện trạng | Hành động tiếp theo | Phối hợp |
|---|---|---|---|
| GAP-01 | Chưa có contract API nghiệp vụ được chốt | Lập API Contract v1, thống nhất path, method, DTO, validation và HTTP status | BA, Frontend |
| GAP-02 | Chưa có security rule đã được triển khai | Chốt Actor/Role/Permission, lập Security Matrix v1 và quy tắc 401/403 | BA |
| GAP-03 | Chưa có quy ước lỗi chung | Lập Error Response Convention và Global Exception Handling; phân biệt 400/401/403/404/409 | BA, QA |
| GAP-04 | Chưa đối chiếu API nghiệp vụ với màn hình và UML | Rà soát endpoint cần có, request/response và luồng xử lý | Frontend, UML |
| GAP-05 | Chưa nhận ERD/script SQL nghiệp vụ đã chốt | Thống nhất bảng, cột, quan hệ và ràng buộc trước khi viết Entity | Database, BA |
| GAP-06 | Swagger mới phản ánh API health | Cập nhật Swagger theo contract và code thực tế | Backend |
| GAP-07 | Chưa chuẩn hóa logging backend | Xác định mức log, nội dung lỗi; tránh ghi mật khẩu/token | Backend |



## 3. Chức năng chưa triển khai theo các đợt sau



| Nhóm chức năng | Đợt dự kiến theo phân công |
|---|---|
| Authentication/JWT/RBAC-ACL | Báo cáo 3 |
| Document Core: upload, detail, metadata, preview, download | Báo cáo 3 |
| Document Versioning và restore | Báo cáo 3 |
| Search, Saved Search, Audit, Statistics | Báo cáo 4 |
| OCR/Fallback theo phạm vi được BA xác nhận | Báo cáo 4 |
| Signature và Integrity | Báo cáo 4 |



Các mục trên là phần chưa triển khai, không phải kết luận

rằng chức năng đã có nhưng đang lỗi.



## 4. Nội dung cần BA xác nhận



- Actor, role, permission và quy tắc owner/ACL.

- Thông tin bắt buộc của hồ sơ, tài liệu và metadata.

- Quy tắc upload, preview, download, versioning và restore.

- Phạm vi OCR/Fallback và chữ ký số/kiểm tra toàn vẹn.

- Contract và expected behavior của từng chức năng.



Không tự thay đổi scope, business rule hoặc permission

khi chưa được BA xác nhận.



## 5. Giới hạn hiện tại



- /api/health chỉ kiểm tra khả năng phản hồi HTTP,

&#x20; không kiểm tra tình trạng database hoặc file storage.

- Chưa có chức năng nghiệp vụ và kiểm thử tương ứng.

- Chưa có xác thực hoặc kiểm soát quyền truy cập API.

- Mỗi thành viên chạy PostgreSQL riêng trên máy.

- OpenAPI baseline hiện chỉ mô tả API đã có.


