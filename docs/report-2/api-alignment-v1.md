\# API Alignment v1



\- Dự án: Hệ thống lưu trữ điện tử

\- Người thực hiện: Trần Quyền Minh

\- Ngày cập nhật: 07/10/2026

\- Trạng thái: Đã lập danh sách đối chiếu; chưa nhận màn hình frontend và Sequence Diagram.



\## 1. Mục đích



Đối chiếu API Contract với màn hình frontend và Sequence Diagram

để phát hiện endpoint, dữ liệu hoặc luồng xử lý dư/thiếu.



Hiện chỉ GET /api/health đã triển khai.

API nghiệp vụ dưới đây là thiết kế trong business-api-contract-v1.md.



\## 2. Danh sách cần đối chiếu



| Chức năng dự kiến | API trong contract | Nội dung cần kiểm tra | Trạng thái |

|---|---|---|---|

| Đăng nhập | POST /api/auth/login | Username, password, token và lỗi đăng nhập | Chờ đối chiếu |

| Thông tin bản thân | GET /api/users/me | Dữ liệu người dùng và role | Chờ đối chiếu |

| Quản lý tài khoản | GET/POST /api/users; PATCH /api/users/{userId} | Tạo tài khoản, role, khóa/mở khóa và phân trang | Chờ đối chiếu |

| Quản lý hồ sơ | GET/POST /api/records; GET/PATCH /api/records/{recordId} | Metadata, quyền VIEW/EDIT và danh sách | Chờ đối chiếu |

| Upload tài liệu | POST /api/records/{recordId}/documents | Multipart, metadata, loại file và giới hạn dung lượng | Chờ đối chiếu |

| Chi tiết tài liệu | GET/PATCH /api/documents/{documentId} | Metadata và quyền hiệu lực | Chờ đối chiếu |

| Download | GET /api/documents/{documentId}/download | Quyền DOWNLOAD và phản hồi file | Chờ đối chiếu |

| Phiên bản | GET/POST /api/documents/{documentId}/versions | Danh sách và upload phiên bản mới | Chờ đối chiếu |

| Restore | POST /api/documents/{documentId}/versions/{versionId}/restore | Tạo phiên bản mới từ bản cũ | Chờ đối chiếu |

| Cấp quyền | GET/PUT/DELETE dưới /api/records/{recordId}/access và /api/documents/{documentId}/access | Role, ACL và thao tác cấp/thu hồi | Chờ đối chiếu |

| Tìm kiếm | GET /api/documents | Bộ lọc, sort, phân trang và phạm vi VIEW | Chờ đối chiếu |

| Tìm kiếm đã lưu | GET/POST /api/saved-searches; DELETE /api/saved-searches/{searchId} | Lưu điều kiện và chỉ quản lý dữ liệu của mình | Chờ đối chiếu |

| Lịch sử | GET /api/audit-events/me; GET /api/audit-events | Phạm vi cá nhân/toàn hệ thống và phân trang | Chờ đối chiếu |

| Thống kê | GET /api/statistics/summary | Chỉ số dashboard và phạm vi được phép | Chờ đối chiếu |

| Preview, OCR, Signature/Integrity | Chưa có contract chi tiết | Xác định luồng và endpoint cần bổ sung | Chờ thiết kế và đối chiếu |



Các nhóm endpoint rút gọn trong bảng phải được đọc cùng

business-api-contract-v1.md để xác định đường dẫn đầy đủ.



\## 3. Cách thực hiện khi nhận tài liệu



1\. Ghi tên màn hình, phiên bản thiết kế và Sequence Diagram tương ứng.

2\. Đối chiếu thao tác giao diện với method và endpoint.

3\. Đối chiếu trường request/response, validation và phân trang.

4\. Kiểm tra role, ACL và tình huống 401/403/404/409.

5\. Ghi điểm khác biệt, phương án xử lý và người phụ trách.

6\. Cập nhật contract, code và Swagger theo phạm vi đã thống nhất.



\## 4. Kết quả hiện tại



\- Chưa nhận màn hình frontend và Sequence Diagram để đối chiếu.

\- Chưa thể kết luận endpoint dư/thiếu hoặc số lượng mismatch.

\- Các chức năng trong bảng là dự kiến, chưa xác nhận là màn hình thực tế.

\- Chưa tính việc lập bảng này là hoàn thành đối chiếu API.



\## 5. Tài liệu liên quan



\- business-api-contract-v1.md

\- security-matrix-v1.md

\- api-error-contract-v1.md

\- openapi-v1.json

