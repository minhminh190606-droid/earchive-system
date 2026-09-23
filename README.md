# Hệ thống lưu trữ điện tử

## Tiến độ hiện tại

- Đã tạo backend Java 21 + Spring Boot.
- Đã cấu hình PostgreSQL 17 chạy bằng Docker.
- Đã có API kiểm tra GET /api/health.
- Chưa triển khai bảng dữ liệu và chức năng nghiệp vụ.

## Yêu cầu

- JDK 21
- Docker Desktop
- Git
- IntelliJ IDEA hoặc công cụ hỗ trợ Java

## Cách chạy backend trên Windows

### 1. Tải mã nguồn

Sau khi được cấp quyền truy cập repository:

```powershell
git clone https://github.com/minhminh190606-droid/earchive-system.git
cd earchive-system\backend
```

### 2. Tạo cấu hình riêng

```powershell
Copy-Item .env.example .env
```

Mở file `.env`, thay `change_me` bằng mật khẩu database
dùng trên máy của mình trước khi khởi tạo PostgreSQL.

Không đưa file `.env` lên GitHub.

Nếu PostgreSQL đã được khởi tạo, dùng mật khẩu hiện có.
Sửa `.env` không tự đổi mật khẩu trong database đã tồn tại.

### 3. Chạy PostgreSQL

Mở Docker Desktop, chờ khởi động xong, rồi chạy:

```powershell
docker compose up -d
```

Kiểm tra:

```powershell
docker compose exec postgres pg_isready -U earchive_user -d earchive_db
```

Kết quả mong đợi: accepting connections.

### 4. Chạy backend

Trong Terminal tại thư mục backend:

```powershell
.\mvnw.cmd spring-boot:run
```

Giữ cửa sổ này mở trong lúc sử dụng backend.

### 5. Kiểm tra API

Mở http://localhost:8080/api/health

Kết quả trả về có `"status": "UP"`.

API này kiểm tra backend có phản hồi, không truy vấn database.

## Phối hợp nhóm

- backend/: mã nguồn Spring Boot hiện có.
- frontend/: nơi bổ sung mã nguồn giao diện.
- database/: nơi bổ sung script SQL.
- docs/: nơi bổ sung sơ đồ và tài liệu.

Mỗi thành viên chạy database riêng trên máy mình.
File SQL trên GitHub không tự cập nhật database trong Docker.

Thống nhất tên bảng, cột, quan hệ và dữ liệu API trước khi
triển khai chức năng. Làm trên nhánh riêng và tạo Pull Request
để gộp vào main.
