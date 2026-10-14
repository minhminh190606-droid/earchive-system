\# API Alignment v1



\- Dự án: Hệ thống lưu trữ điện tử

\- Người thực hiện: Trần Quyền Minh

\- Ngày cập nhật: 14/10/2026

\- Trạng thái: Đã đối chiếu sơ bộ mã nguồn frontend với thiết kế backend; chưa hoàn tất tích hợp và đối chiếu Sequence Diagram.



\## 1. Mục đích và phạm vi



Đối chiếu giao diện, mã nguồn frontend và API Contract để phát hiện khác biệt về chức năng, dữ liệu, quyền và luồng xử lý.



Tài liệu đầu vào:



\- Front-End Report.zip: mã nguồn frontend, ảnh màn hình và Frontend.mdj.

\- System.mdj: phạm vi hệ thống, actor, kiến trúc và Use Case.

\- business-api-contract-v1.md.

\- security-matrix-v1.md.

\- api-error-contract-v1.md.



Kết quả dưới đây dựa trên đọc mã nguồn và tài liệu; chưa phải kết quả chạy thử tích hợp frontend với backend.



Backend hiện đã triển khai GET /api/health và xử lý lỗi tập trung. Các API nghiệp vụ trong contract vẫn là thiết kế.



Frontend sử dụng React, TypeScript và Vite. Các thao tác nghiệp vụ đang sử dụng dữ liệu mẫu và localStorage.



Chưa tìm thấy ERD, script SQL nghiệp vụ hoặc Sequence Diagram trong bộ file được bàn giao.



\## 2. Đối chiếu chức năng



| Chức năng | Hiện trạng frontend/tài liệu | API backend dự kiến | Kết quả sơ bộ |

|---|---|---|---|

| Đăng nhập | Có LoginPage; sử dụng token giả | POST /api/auth/login | Có giao diện; chưa tích hợp xác thực thật |

| Thông tin bản thân | Có user trong authStore | GET /api/users/me | Cần thay dữ liệu mẫu bằng phản hồi backend |

| Quản lý tài khoản | System.mdj có Use Case; chưa thấy trang quản lý riêng trong source đã nhận | GET/POST /api/users; PATCH /api/users/{userId} | Cần bổ sung hoặc xác nhận phạm vi giao diện |

| Quản lý hồ sơ | Chưa thấy trang quản lý hồ sơ riêng | GET/POST /api/records; GET/PATCH /api/records/{recordId} | Cần làm rõ quan hệ hồ sơ, thư mục và tài liệu |

| Danh sách tài liệu | Có DocumentListPage; lọc, sắp xếp và phân trang dữ liệu cục bộ | GET /api/documents | Cần thống nhất tham số và cấu trúc phản hồi |

| Upload tài liệu | Có UploadPage; lưu thông tin file vào dữ liệu cục bộ | POST /api/records/{recordId}/documents | Cần chọn hồ sơ chứa tài liệu và gửi multipart thực tế |

| Chi tiết và chỉnh sửa | Có DocumentDetailPage và hàm cập nhật metadata | GET/PATCH /api/documents/{documentId} | Cần đối chiếu DTO và quyền hiệu lực |

| Download | Tạo file nội dung mẫu trên trình duyệt | GET /api/documents/{documentId}/download | Chưa tải file gốc từ backend |

| Phiên bản | Có dữ liệu và hàm thêm phiên bản mô phỏng | GET/POST /api/documents/{documentId}/versions | Cần tích hợp lưu file và quản lý phiên bản thật |

| Restore | Chưa xác nhận luồng restore đáp ứng contract | POST /api/documents/{documentId}/versions/{versionId}/restore | Cần đối chiếu thêm; restore phải tạo phiên bản mới |

| Cấp và thu hồi quyền | Chưa thấy luồng ACL đầy đủ trong source đã nhận | Nhóm API access của hồ sơ và tài liệu | Cần thiết kế giao diện và tích hợp kiểm tra quyền |

| Tìm kiếm | Có SearchPage và bộ lọc dữ liệu mẫu | GET /api/documents | Cần thống nhất bộ lọc; backend phải giới hạn theo VIEW |

| Tìm kiếm đã lưu | Có lưu/xóa điều kiện trong localStorage | GET/POST /api/saved-searches; DELETE /api/saved-searches/{searchId} | Cần lưu theo tài khoản và kiểm tra quyền sở hữu |

| Lịch sử sử dụng | Chưa thấy trang audit riêng | GET /api/audit-events/me; GET /api/audit-events | Cần bổ sung hoặc xác nhận phạm vi giao diện |

| Thống kê | Có DashboardPage; tính từ dữ liệu cục bộ | GET /api/statistics/summary | Cần thống nhất chỉ số và phạm vi theo quyền |

| Preview | Có PreviewPage | Chưa có contract chi tiết | Cần xác định định dạng hỗ trợ và cách lấy nội dung |

| OCR, Signature/Integrity | Có mô phỏng chữ ký và mã toàn vẹn; chưa xác nhận luồng OCR | Chưa có contract chi tiết | Cần thiết kế riêng; không coi dữ liệu mô phỏng là xử lý thật |



Các đường dẫn API dự kiến phải được đọc cùng business-api-contract-v1.md. Bảng này không xác nhận các endpoint nghiệp vụ đã được triển khai.



\## 3. Sai khác và hướng xử lý đề xuất



Các phương án dưới đây cần được đồng bộ giữa các thành viên; chưa đánh dấu là đã sửa.



| Mã | Điểm khác biệt | Hướng xử lý đề xuất | Phụ trách phối hợp | Trạng thái |

|---|---|---|---|---|

| AL-01 | Frontend dùng admin/user/viewer; System.mdj có Guest/Member/Admin/Manager; backend dùng ADMIN/ARCHIVIST/USER | Dùng Security Matrix v1 đã chốt làm cơ sở; thống nhất ý nghĩa vai trò rồi cập nhật frontend và UML, không tự ánh xạ chỉ theo tên | Backend, Frontend, người phụ trách UML | Chưa xử lý |

| AL-02 | System.mdj có đăng ký; thiết kế backend chỉ cho ADMIN tạo tài khoản | Điều chỉnh Use Case theo phạm vi v1; nếu cần đăng ký công khai phải cập nhật yêu cầu và security rule | Backend, BA/người phụ trách UML | Chưa xử lý |

| AL-03 | Form đăng nhập frontend dùng email; contract backend dùng username | Thống nhất trường đăng nhập; đề xuất frontend dùng username theo contract hiện tại | Backend, Frontend | Chưa xử lý |

| AL-04 | Frontend dùng page bắt đầu từ 1 và pageSize; contract dùng page bắt đầu từ 0 và size | Thống nhất tham số API; frontend có thể giữ số trang hiển thị từ 1 nhưng chuyển đổi khi gọi API | Backend, Frontend | Chưa xử lý |

| AL-05 | Kiểu lỗi frontend có statusCode; backend trả status cùng timestamp, code, message, path, fieldErrors | Cập nhật kiểu lỗi và cách hiển thị lỗi frontend theo ApiErrorResponse | Backend, Frontend | Chưa xử lý |

| AL-06 | Giao diện upload ghi 50 MB; contract dự kiến giới hạn 20 MiB | Thống nhất một giới hạn và đơn vị; cập nhật giao diện, validation và cấu hình backend | Backend, Frontend | Chưa xử lý |

| AL-07 | Frontend có xóa tài liệu mẫu; Security Matrix chưa hỗ trợ xóa vĩnh viễn | Tạm ẩn thao tác xóa vĩnh viễn; không tự bổ sung API DELETE khi chưa chốt nghiệp vụ | Backend, Frontend | Chưa xử lý |

| AL-08 | Frontend chưa thể hiện rõ hồ sơ chứa tài liệu; API upload cần recordId | Làm rõ hồ sơ khác thư mục như thế nào; bổ sung chọn hồ sơ và kiểm tra EDIT khi upload | Backend, Frontend, Database | Chưa xử lý |

| AL-09 | Frontend có trạng thái draft/pending/approved/rejected/archived và thao tác duyệt | Chốt vòng đời tài liệu, vai trò được duyệt và API chuyển trạng thái trước khi tích hợp | Backend, Frontend, BA | Chưa xử lý |

| AL-10 | Ký số đặt trạng thái verified và mã toàn vẹn được tạo ngẫu nhiên trong dữ liệu mẫu | Ghi rõ đây là mô phỏng; thiết kế xử lý chữ ký và tính hash từ file thật trước khi công bố chức năng hoạt động | Backend, Frontend | Chưa xử lý |

| AL-11 | Frontend có thông báo nhưng chưa có contract chi tiết tương ứng trong bộ đối chiếu | Xác định thông báo có thuộc phạm vi v1; bổ sung contract nếu được chọn triển khai | Backend, Frontend, BA | Chưa xử lý |



\## 4. Thứ tự thực hiện tiếp theo



1\. Thống nhất vai trò, trường đăng nhập và cấu trúc lỗi theo các contract hiện có.

2\. Thống nhất phân trang, hồ sơ chứa tài liệu và giới hạn upload.

3\. Chốt phạm vi xóa, duyệt tài liệu, thông báo và ký số.

4\. Nhận ERD/script SQL để đối chiếu trường dữ liệu và quan hệ.

5\. Bổ sung Sequence Diagram cho các luồng ưu tiên.

6\. Cập nhật frontend, contract và UML theo các quyết định đã thống nhất.

7\. Triển khai API nghiệp vụ, thay dữ liệu mẫu bằng lời gọi backend.

8\. Kiểm thử tích hợp và lưu bằng chứng theo từng chức năng.



\## 5. Điều kiện đánh dấu đã xử lý



Một sai khác chỉ được đánh dấu đã xử lý khi:



\- Có quyết định thống nhất được ghi nhận.

\- Tài liệu hoặc code liên quan đã được cập nhật.

\- Có bằng chứng kiểm tra phù hợp với phạm vi thay đổi.

\- Nếu liên quan tích hợp, frontend và backend đã chạy thử cùng nhau.



Các chức năng có phân quyền cần kiểm tra cả vai trò và quyền tài nguyên, bao gồm tình huống 401, 403, 404 và 409 phù hợp.



\## 6. Kết quả hiện tại



\- Đã nhận mã nguồn, ảnh màn hình và tài liệu thiết kế frontend.

\- Đã thực hiện đối chiếu sơ bộ và ghi nhận 11 nhóm sai khác/cần làm rõ.

\- Chưa sửa hoặc kiểm chứng đầy đủ các sai khác.

\- Chưa hoàn tất đối chiếu Sequence Diagram và database.

\- Chưa có bằng chứng tích hợp các API nghiệp vụ.

\- Chưa kết luận toàn bộ yêu cầu đối chiếu API của đợt 2 đã hoàn thành.

