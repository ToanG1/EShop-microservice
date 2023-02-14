# ToanG1-EShop-microservice
Chức năng cơ bản 

Eshop project

*** EShopProduct ***
db: mongodb
Pagination cho các response

User: 
- tìm kiếm sản phẩm theo tên, cate, bộ lọc, cửa hàng, style, cate

Vendor:
+Product
- gửi yêu cầu tạo sản phẩm
- thay đổi trạng thái selling của sản phẩm
- yêu cầu sửa thông tin sản phẩm, thay đổi số lượng hàng
- tìm kiếm sản phẩm theo tên, cate, bộ lọc, cửa hàng, style, cate

Admin: 
+Product
- lấy danh sách sản phẩm yêu cầu(…)
- xóa sản phẩm
- thay đổi trạng thái active sản phẩm

+Category
- thêm category
- sửa thông tin category
- xóa category
- tìm kiếm theo id, name, lọc mới nhất, lọc số lượng sản phẩm của cate

+Style
- thêm style
- tìm kiếm style theo id, name, cate, lọc mới nhất


*** EShop4User ***
db: mysql

User: 
+User: ( firebase authentication )
- tạo tài khoản 
- sửa thông tin người dùng

+Store:
- tim kiem cua hang

+Address: 
- thêm thông tin giao hàng
- chỉnh sửa thông tin giao hàng
- lay danh sach thong tin giao hang
- xoa thong tin giao hang

+UserFollowStore:
- lay danh sach cua hang theo doi
- theo doi/ bo theo doi cua hang

+UserFollowProduct:
- lay danh sach san pham theo doi
- theo doi/ bo theo doi san pham
Vendor:
+User: 
- 

+Store:
- tìm dach sách cửa hàng 
- gui yeu cau tao cửa hàng
- gui yeu cau thay doi thong tin cua hang
- tim kiem cua hang theo ownerId
- thay doi trang thai isOpen

+Address:

+UserFollowStore:
- lay danh sach khach hang theo doi cua hang
- lay so luong nguoi theo doi cua hang

+UserFollowProduct:
- lay danh sach khach hang theo doi san pham
- lay so luong nguoi theo doi san pham


Admin: 
+User:

+Store:
- thay doi trang thai isActive

+Adress



