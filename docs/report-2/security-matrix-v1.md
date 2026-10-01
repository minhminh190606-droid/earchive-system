# Security Matrix v1

- Dự án: Hệ thống lưu trữ điện tử

- Người thiết kế: Trần Quyền Minh

- Ngày tạo: 01/10/2026

- Trạng thái: Phương án thiết kế do Backend/Technical Lead lựa chọn; chưa triển khai trong code.

- Mục đích: Thống nhất vai trò và quyền để Frontend, Backend và Database cùng sử dụng.

## 1. Vai trò người dùng

| Mã vai trò | Tên | Trách nhiệm |
|---|---|---|
| ADMIN | Quản trị viên | Quản lý tài khoản, vai trò, cấu hình; xem audit và thống kê toàn hệ thống |
| ARCHIVIST | Nhân viên lưu trữ | Tạo hồ sơ/tài liệu; quản lý tài nguyên thuộc sở hữu hoặc được cấp quyền |
| USER | Người khai thác | Tìm kiếm, xem và tải tài liệu được cấp quyền; quản lý tìm kiếm đã lưu của mình |

Trong phiên bản đầu, mỗi tài khoản có một vai trò.

Người dùng không được tự thay đổi vai trò của mình.

Tài khoản do ADMIN tạo; chưa triển khai đăng ký công khai.

## 2. Quyền trên hồ sơ và tài liệu

| Quyền | Ý nghĩa |
|---|---|
| VIEW | Xem metadata và preview nội dung nếu định dạng hỗ trợ |
| DOWNLOAD | Tải tài liệu hoặc lấy nội dung để in |
| EDIT | Sửa metadata, upload phiên bản mới và restore phiên bản |
| MANAGE_ACCESS | Cấp hoặc thu hồi quyền trên tài nguyên |

DOWNLOAD, EDIT và MANAGE_ACCESS phải đi kèm VIEW.

Các quyền này áp dụng cho tài nguyên cụ thể, không tự động áp dụng toàn hệ thống.

## 3. Ma trận chức năng

| Chức năng | ADMIN | ARCHIVIST | USER |
|---|---|---|---|
| Đăng nhập và xem thông tin bản thân | Có | Có | Có |
| Quản lý tài khoản và vai trò | Có | Không | Không |
| Tạo hồ sơ | Không | Có | Không |
| Tạo tài liệu/upload lần đầu | Không | Có, cần EDIT trên hồ sơ chứa tài liệu | Không |
| Xem metadata và preview | Cần VIEW | Cần VIEW | Cần VIEW |
| Tải hoặc in tài liệu | Cần DOWNLOAD | Cần DOWNLOAD | Cần DOWNLOAD |
| Sửa metadata hồ sơ/tài liệu | Cần EDIT | Cần EDIT | Không |
| Upload phiên bản mới hoặc restore | Cần EDIT | Cần EDIT | Không |
| Cấp hoặc thu hồi quyền | Cần MANAGE_ACCESS | Cần MANAGE_ACCESS | Không |
| Tìm kiếm tài liệu | Chỉ trong phạm vi có VIEW | Chỉ trong phạm vi có VIEW | Chỉ trong phạm vi có VIEW |
| Lưu và sử dụng lại tìm kiếm | Chỉ tìm kiếm của mình | Chỉ tìm kiếm của mình | Chỉ tìm kiếm của mình |
| Xem lịch sử cá nhân | Có | Có | Có |
| Xem audit toàn hệ thống | Có | Không | Không |
| Xem thống kê | Toàn hệ thống | Phạm vi có VIEW | Phạm vi có VIEW |
| Xóa vĩnh viễn hồ sơ/tài liệu | Chưa hỗ trợ | Chưa hỗ trợ | Chưa hỗ trợ |

Quyết định truy cập phải kiểm tra cả vai trò và quyền tài nguyên.

Ví dụ: USER không được sửa metadata dù có bản ghi EDIT được cấp nhầm.

## 4. Quy tắc sở hữu và ACL

ACL là danh sách quyền được cấp cho từng người dùng trên một tài nguyên.

- Người tạo hồ sơ/tài liệu là chủ sở hữu.

- Chủ sở hữu có VIEW, DOWNLOAD, EDIT và MANAGE_ACCESS trên tài nguyên mình tạo, trong giới hạn vai trò.

- ADMIN không mặc định có quyền đọc nội dung mọi tài liệu.

- ADMIN có thể quản lý tài khoản nhưng việc này không tự cấp quyền tài liệu.

- Mặc định từ chối truy cập nếu không có quyền phù hợp.

- USER chỉ được cấp VIEW và DOWNLOAD.

- ADMIN và ARCHIVIST có thể được cấp đủ bốn quyền.

- Người cấp quyền phải có MANAGE_ACCESS và không được cấp quyền vượt quá quyền hiệu lực của mình.

- Quyền trên hồ sơ không tự động kế thừa xuống tài liệu trong phiên bản đầu.

- Mỗi tài liệu có ACL riêng; người được xem tài liệu phải có VIEW trên hồ sơ chứa tài liệu.

- Khi tạo tài liệu trong hồ sơ của người khác, người tạo phải có EDIT trên hồ sơ và trở thành chủ sở hữu tài liệu mới.

- Thu hồi quyền hồ sơ có thể làm tài liệu bên trong không còn truy cập được.

- Thay đổi ACL và thao tác tài liệu quan trọng phải được ghi audit khi module Audit được triển khai.

## 5. Quy tắc tìm kiếm và thống kê

- Kết quả tìm kiếm chỉ chứa tài nguyên người dùng có quyền VIEW.

- Điều kiện tìm kiếm đã lưu không lưu quyền truy cập.

- Mỗi lần chạy lại tìm kiếm phải kiểm tra quyền hiện tại.

- USER và ARCHIVIST chỉ thấy thống kê trong phạm vi được xem.

- ADMIN được xem số liệu tổng hợp toàn hệ thống; quyền này không cho phép đọc nội dung tài liệu.

- Không trả metadata hoặc nội dung ngoài phạm vi được phép.

## 6. Quy ước mã phản hồi

| HTTP status | Trường hợp |
|---|---|
| 400 | Dữ liệu đầu vào hoặc tổ hợp quyền không hợp lệ |
| 401 | Chưa đăng nhập, token thiếu, hết hạn hoặc không hợp lệ |
| 403 | Đã xác thực nhưng vai trò hoặc quyền không cho phép thao tác |
| 404 | Tài nguyên không tồn tại hoặc người dùng không có VIEW trên tài nguyên |
| 409 | Xung đột dữ liệu hoặc trạng thái nghiệp vụ |

Kiểm tra theo thứ tự:

1. Xác thực người dùng.

2. Kiểm tra vai trò có được thực hiện chức năng.

3. Kiểm tra tài nguyên tồn tại và quyền VIEW.

4. Kiểm tra quyền thao tác cụ thể.

5. Thực hiện nghiệp vụ.

Ví dụ:

- Chưa đăng nhập gọi API được bảo vệ: 401.

- USER gọi chức năng quản lý tài khoản: 403.

- Người không có VIEW yêu cầu chi tiết tài liệu: 404.

- Người có VIEW nhưng thiếu DOWNLOAD yêu cầu tải: 403.

## 7. Nội dung cần phối hợp

- Frontend hiển thị chức năng theo vai trò và quyền hiệu lực.

- Backend luôn kiểm tra quyền; ẩn nút trên giao diện không thay thế kiểm tra backend.

- Database cần lưu user, role, owner và ACL theo thiết kế được thống nhất.

- Nhóm cần quy định cách khôi phục quyền khi chủ sở hữu bị khóa hoặc rời hệ thống.

- OCR và Signature/Integrity sẽ được bổ sung quy tắc riêng khi chốt luồng nghiệp vụ.

- Tài liệu này là thiết kế; chưa phải bằng chứng tính năng bảo mật đã hoạt động.


