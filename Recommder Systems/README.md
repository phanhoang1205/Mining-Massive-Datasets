## Recommder System

chia làm 2 loại
+ personalized recommendations
+ Non-personalized recommendations

---

X = là tập người dùng (set of Customers) \
S = set of Items \
Utility function: $X.S \to R \\ (x, s) \to r$

---
(1) Thu thập các Rating ban đầu 
+ Explicit:  
+ Implicit: Theo dõi hành vi trên hệ thống 

(2) Ngoại suy các rating còn thiếu \
_Cold start: 

----
+ Content-based Recommendations: Sử dụng thông tin của sản phẩm tạo ra vector profile, sử dụng thông tin của người dùng tạo ra vector profile.
 + Ưu điểm: \
 _ không cần dùng sử dụng thông tin của người khác \
 _ có thể gợi ý cho những người dùng có sở thích riêng biệt \
 _ có thể gợi ý những sản phẩm mới và sản phẩm ko phổ biến \
 _ dễ giải thích, dễ minh giải 
 + Khuyết điểm: \
 _ khó tìm đặc trưng phù hợp\
 _ Khó gợi ý cho người dùng mới \
 _ Chuyên biệt hóa thái quá

----
+ Collabborative Filltering: sử dụng trực tiếp các cột các dòng trong ma trận ultility để làm vector profile cho người dùng, sản phẩm 

  Cho người dùng X dự đoán rating của X cho I như sau
 + tìm N người dùng giống với X nhất và có rating cho I
 + tính ra, dự đoán rating của X cho I dựa vào N người đã chọn
    + Cách 1: Trung bình cộng các rating lại
    + Cách 2: tính trung bình theo trọng số là hệ số pearson
  + Có thể tìm N sản phẩm được rating bởi X và giống I nhất

  Jaccard: làm mất giá trị \
  Cosine: Xem giá trị còn thiếu là 0 (negative) \
  Pearson correlation coeficient:

 + Ưu:\
 _ Có thể áp dụng cho mọi loại sản phẩm
 + Khuyết điểm: \
 _ Cold Start \
 _ Sparsity: ma trận thưa \
 _ First rater: \
 _ Bị áp đảo bởi số đông (Popularity bias)

----
+ Hybrid Methods: cài đặt nhiều phương pháp gợi ý trong 1 hệ thống, sử dụng kết quả tổng hợp từ các phương pháp này
----
(3) Đánh giá kết quả dự đoán \
dùng độ đo: MSE, MAE, RMSE


----
TF-IDF

$doc_j = (term_i)$ 

$f_{ij}$ là số lần term i xuất hiện trong văn bản j 

$TF_{ij} = \frac{f_{ij}}{max_kf_{kj}} $ 

$IDF_i = \log \frac{N}{n_i} \\ = -log \frac{n_i}{N}$ 

$W_{ij} = TF_{ij} . IDF_i$

vector profile của 1:




