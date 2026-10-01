# Business API Contract v1

- Dự án: Hệ thống lưu trữ điện tử

- Người thiết kế: Trần Quyền Minh

- Ngày cập nhật: 01/10/2026

- Trạng thái: Bản thiết kế ban đầu; chưa triển khai API nghiệp vụ.

- Cơ sở phân quyền: security-matrix-v1.md

- Quy ước lỗi: api-error-contract-v1.md

## 1. Phạm vi và nguyên tắc

- API hiện đã triển khai: GET /api/health.

- Các endpoint trong tài liệu này là dự kiến.

- Frontend và Database dùng tài liệu để đối chiếu trước khi tích hợp.

- API trao đổi JSON, trừ upload multipart và download file.

- Tên trường JSON dùng camelCase.

- Thời gian dùng ISO 8601 theo UTC.

- ID truyền dưới dạng chuỗi; đề xuất UUID cho dữ liệu nghiệp vụ mới.

- Kiểu khóa chính và quan hệ cụ thể cần đối chiếu với ERD trước khi viết Entity.

- API được bảo vệ nhận header Authorization: Bearer <accessToken>.

- Backend kiểm tra cả vai trò và ACL; không chỉ dựa vào giao diện.

- Không trả mật khẩu, password hash hoặc đường dẫn lưu file nội bộ.

- Chưa hỗ trợ xóa vĩnh viễn hồ sơ và tài liệu.

## 2. Danh sách API dự kiến

| Module | Method | Endpoint | Mục đích | Thành công |
|---|---|---|---|---|
| Auth | POST | /api/auth/login | Đăng nhập | 200 |
| User | GET | /api/users/me | Thông tin người dùng hiện tại | 200 |
| User | GET | /api/users | Danh sách tài khoản, ADMIN | 200 |
| User | POST | /api/users | Tạo tài khoản, ADMIN | 201 |
| User | PATCH | /api/users/{userId} | Đổi vai trò hoặc trạng thái, ADMIN | 200 |
| Record | POST | /api/records | Tạo hồ sơ, ARCHIVIST | 201 |
| Record | GET | /api/records | Danh sách hồ sơ có VIEW | 200 |
| Record | GET | /api/records/{recordId} | Chi tiết hồ sơ có VIEW | 200 |
| Record | PATCH | /api/records/{recordId} | Sửa metadata, cần EDIT | 200 |
| Document | POST | /api/records/{recordId}/documents | Upload tài liệu mới, ARCHIVIST và EDIT trên hồ sơ | 201 |
| Document | GET | /api/documents/{documentId} | Chi tiết tài liệu có VIEW | 200 |
| Document | PATCH | /api/documents/{documentId} | Sửa metadata, cần EDIT | 200 |
| Document | GET | /api/documents/{documentId}/download | Tải phiên bản hiện tại, cần DOWNLOAD | 200 |
| Version | GET | /api/documents/{documentId}/versions | Danh sách phiên bản, cần VIEW | 200 |
| Version | POST | /api/documents/{documentId}/versions | Upload phiên bản mới, cần EDIT | 201 |
| Version | POST | /api/documents/{documentId}/versions/{versionId}/restore | Khôi phục thành phiên bản mới, cần EDIT | 201 |
| Access | GET | /api/records/{recordId}/access | Xem ACL hồ sơ, cần MANAGE_ACCESS | 200 |
| Access | PUT | /api/records/{recordId}/access/{userId} | Thay tập quyền hồ sơ của người dùng | 200 |
| Access | DELETE | /api/records/{recordId}/access/{userId} | Thu hồi quyền hồ sơ được cấp trực tiếp | 204 |
| Access | GET | /api/documents/{documentId}/access | Xem ACL tài liệu, cần MANAGE_ACCESS | 200 |
| Access | PUT | /api/documents/{documentId}/access/{userId} | Thay tập quyền tài liệu của người dùng | 200 |
| Access | DELETE | /api/documents/{documentId}/access/{userId} | Thu hồi quyền tài liệu được cấp trực tiếp | 204 |
| Search | GET | /api/documents | Tìm kiếm, lọc và phân trang tài liệu có VIEW | 200 |
| Saved Search | POST | /api/saved-searches | Lưu điều kiện tìm kiếm của mình | 201 |
| Saved Search | GET | /api/saved-searches | Danh sách tìm kiếm đã lưu của mình | 200 |
| Saved Search | DELETE | /api/saved-searches/{searchId} | Xóa tìm kiếm đã lưu của mình | 204 |
| Audit | GET | /api/audit-events/me | Lịch sử hành động của mình | 200 |
| Audit | GET | /api/audit-events | Audit toàn hệ thống, ADMIN | 200 |
| Statistics | GET | /api/statistics/summary | Số liệu theo phạm vi được phép | 200 |

Preview, OCR và Signature/Integrity sẽ được bổ sung contract sau khi chốt luồng xử lý.

## 3. Auth và User

### Đăng nhập

Request:

- username: string, bắt buộc.

- password: string, bắt buộc; không ghi vào log.

Response:

- accessToken: string.

- tokenType: "Bearer".

- expiresIn: integer, số giây hiệu lực.

- user: UserResponse.

Sai thông tin đăng nhập hoặc tài khoản không được phép đăng nhập:

- HTTP 401, code UNAUTHORIZED.

- Dùng thông báo chung, không tiết lộ tài khoản có tồn tại hay không.

Refresh token và logout phía server chưa thuộc contract hiện tại.

### UserResponse

- id: string.

- username: string.

- fullName: string.

- role: ADMIN, ARCHIVIST hoặc USER.

- enabled: boolean.

- createdAt: thời gian UTC.

### Tạo tài khoản

Request:

- username: 3 đến 50 ký tự; chữ, số, dấu chấm, gạch dưới hoặc gạch ngang.

- fullName: 1 đến 150 ký tự sau khi trim.

- password: 12 đến 128 ký tự; không tự trim.

- role: một trong ba vai trò.

- enabled: boolean; mặc định true.

Username không phân biệt hoa thường và phải duy nhất.

Username trùng trả 409, CONFLICT.

Chỉ ADMIN được tạo tài khoản.

### Cập nhật tài khoản

Request PATCH cho phép:

- role.

- enabled.

Không cho đổi username qua endpoint này.

Không cho vô hiệu hóa hoặc hạ quyền ADMIN hoạt động cuối cùng; trả 409.

Luồng đổi mật khẩu sẽ có contract riêng.

## 4. Hồ sơ

### CreateRecordRequest

- title: bắt buộc, 1 đến 255 ký tự sau khi trim.

- description: tùy chọn, tối đa 2000 ký tự.

Backend lấy ownerId từ người đăng nhập; không nhận ownerId từ request.

### RecordResponse

- id.

- title.

- description: có thể null.

- ownerId.

- createdAt.

- updatedAt.

- effectivePermissions: mảng quyền của người đang gọi API.

PATCH chỉ cho sửa title và description.

Trường không gửi giữ nguyên.

description = null dùng để xóa mô tả.

title = null hoặc chuỗi trống trả 400.

## 5. Tài liệu và phiên bản

### Upload tài liệu mới

Content-Type: multipart/form-data.

Các part:

- metadata: application/json, chứa title và description.

- file: file bắt buộc.

Validation metadata giống hồ sơ.

recordId lấy từ đường dẫn.

ownerId lấy từ người đăng nhập.

Giới hạn đề xuất ban đầu:

- Tối đa 20 MiB mỗi file.

- Hỗ trợ PDF, DOCX, XLSX, TXT, PNG và JPEG.

- Backend kiểm tra loại file; không chỉ tin tên mở rộng hoặc Content-Type.

- File trống trả 400, INVALID_REQUEST.

- File quá lớn trả 413, PAYLOAD_TOO_LARGE.

- Loại file bị từ chối trả 400, INVALID_REQUEST.

- Content-Type của request không phù hợp trả 415, UNSUPPORTED_MEDIA_TYPE.

Giới hạn và loại file cần thống nhất với nhóm trước khi triển khai.

### DocumentResponse

- id.

- recordId.

- title.

- description: có thể null.

- ownerId.

- currentVersionId.

- createdAt.

- updatedAt.

- effectivePermissions: quyền hiệu lực của người gọi.

### DocumentVersionResponse

- id.

- documentId.

- versionNumber: integer.

- originalFilename.

- mediaType.

- sizeBytes: integer.

- createdBy.

- createdAt.

Không trả đường dẫn vật lý của file.

### Upload phiên bản mới

Content-Type: multipart/form-data.

Part file là bắt buộc.

Áp dụng cùng giới hạn upload tài liệu mới.

Backend tự cấp versionNumber, không nhận từ frontend.

### Restore phiên bản

Không cần request body.

versionId phải thuộc documentId trên đường dẫn.

Restore tạo phiên bản mới từ nội dung phiên bản cũ.

Không ghi đè hoặc xóa lịch sử phiên bản.

### Download

Trả dữ liệu nhị phân và header:

- Content-Type theo loại file.

- Content-Disposition: attachment, kèm tên file được xử lý an toàn.

Cần DOWNLOAD trên tài liệu và VIEW trên hồ sơ chứa tài liệu.

## 6. Cấp quyền

Request PUT:

- permissions: mảng gồm VIEW, DOWNLOAD, EDIT, MANAGE_ACCESS.

Quy tắc:

- PUT thay toàn bộ tập quyền cấp trực tiếp cho người dùng đích.

- permissions không được rỗng; dùng DELETE để thu hồi.

- Các quyền khác phải đi kèm VIEW.

- USER chỉ được nhận VIEW và DOWNLOAD.

- Người cấp phải có MANAGE_ACCESS và không được cấp vượt quyền hiệu lực của mình.

- Không dùng ACL để xóa quyền sở hữu.

- Không thay owner qua API cấp quyền.

- ACL hồ sơ không tự kế thừa xuống tài liệu.

- Xem tài liệu cần VIEW trên tài liệu và hồ sơ chứa nó.

AccessGrantResponse:

- userId.

- permissions: tập quyền được cấp trực tiếp.

GET access trả danh sách quyền cấp trực tiếp và ownerId riêng.

## 7. Tìm kiếm và phân trang

GET /api/documents nhận:

- q: tùy chọn, tối đa 200 ký tự.

- recordId: tùy chọn.

- page: mặc định 0, tối thiểu 0.

- size: mặc định 20, từ 1 đến 100.

- sort: mặc định createdAt,desc.

Chỉ cho sort theo title, createdAt hoặc updatedAt.

Khi giá trị sort bằng nhau, backend dùng id để ổn định thứ tự.

Response danh sách:

- items: mảng dữ liệu.

- page.

- size.

- totalElements.

- totalPages.

Backend lọc quyền trước khi tính tổng và phân trang.

Không trả số lượng tài liệu ngoài phạm vi VIEW.

Giai đoạn đầu q tìm trong title và description.

Full-text nội dung và OCR sẽ được bổ sung sau.

## 8. Tìm kiếm đã lưu

CreateSavedSearchRequest:

- name: bắt buộc, 1 đến 100 ký tự.

- criteria: gồm q, recordId và sort theo quy tắc tìm kiếm.

SavedSearchResponse:

- id.

- name.

- criteria.

- createdAt.

Backend xác định chủ sở hữu từ người đăng nhập.

Không lưu page hoặc quyền truy cập vào criteria.

Frontend dùng criteria gọi lại GET /api/documents.

Backend luôn kiểm tra quyền hiện tại khi chạy lại tìm kiếm.

Không cho đọc hoặc xóa tìm kiếm của người khác.

## 9. Audit và thống kê

### AuditEventResponse

- id.

- actorId.

- action.

- resourceType.

- resourceId: có thể null.

- outcome: SUCCESS hoặc FAILURE.

- occurredAt.

Danh sách audit dùng phân trang như mục 7.

Không trả mật khẩu, token hoặc nội dung tài liệu trong audit.

Lịch sử cá nhân không tiết lộ metadata tài liệu ngoài quyền hiện tại.

### StatisticsSummaryResponse

- totalRecords.

- totalDocuments.

- totalVersions.

ADMIN nhận số liệu tổng hợp toàn hệ thống.

ARCHIVIST và USER nhận số liệu trong phạm vi VIEW.

Quyền xem thống kê không tự cấp quyền đọc tài liệu.

## 10. HTTP và lỗi

- 200: đọc hoặc cập nhật thành công.

- 201: tạo tài nguyên thành công; trả body và Location khi có URL chi tiết.

- 204: xóa quyền hoặc tìm kiếm đã lưu thành công; không có body.

- 400: dữ liệu sai hoặc tổ hợp quyền không hợp lệ.

- 401: chưa xác thực hoặc xác thực không hợp lệ.

- 403: vai trò hoặc quyền thao tác không cho phép.

- 404: tài nguyên không tồn tại hoặc không có VIEW.

- 409: trùng username hoặc xung đột nghiệp vụ.

- 413: nội dung upload quá lớn.

- 415: Content-Type request không được hỗ trợ.

- 500: lỗi không dự kiến.

Response lỗi theo api-error-contract-v1.md.

Thứ tự kiểm tra quyền theo security-matrix-v1.md.

## 11. Phần cần đối chiếu khi tích hợp

- Database: kiểu ID, user, owner, ACL, hồ sơ, tài liệu và phiên bản.

- Frontend: tên trường, multipart, phân trang và hiển thị lỗi.

- Nhóm nghiệp vụ: metadata bắt buộc, giới hạn file và luồng quản lý tài liệu.

- UML: actor, thứ tự kiểm tra và luồng thành công/thất bại.

Chưa đánh dấu API nghiệp vụ hoàn thành cho đến khi có code và kiểm thử.


