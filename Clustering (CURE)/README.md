# Clustering

Chia dữ liệu thành những cụm có đặc điểm “giống nhau”, khác nhóm thì “khác nhau”

Mỗi nhóm gọi là cluster

Outlier: là điểm không thuộc về cluster nào

The curse of Dimensionality: almost pairs of pints are very far from each other

Dùng 3 tiêu chí để phân loại thuật toán

1. Cấu trúc thuật toán
2. Khong gian biểu diễn dữ liệu
3. Kích thước dữ liệu

----
## Tiêu chí 1: Cấu trúc thuật toán
Chia làm 2 nhóm:
- Hierarchical: Phân cụm phân cấp
- Point assignment

Hierarchical:
+ Agglomerative (bottom up): ban đầu mỗi điểm giả sử thuộc về 1 cluster riêng biệt, lặp đi lặp lại liên tục quá trình gộp cluster, quá trình dừng lại khi thỏa mãn điều kiện nhất định
+ Divisive (top down): ban đầu xem tất cả dữ liệu thuộc cùng cluster, lặp đi lặp quá trình phân chia, quá trình dừng lại khi thỏa mãn điều kiện nhất định

Point assignment:
+ Duy trì 1 tập cluster
+ Gán dữ liệu vào cluster gần nhất


----
## Tiêu chí 2: Không gian biểu diễn dữ liệu

Dự vào Không gian biểu diễn dữ liệu chia làm 2 loại:
1. Không gian euclidean
2. Không gian phi euclidean

* Không gian euclidean

  Trong không gian euclidean dữ liệu là các vector số thực.

  Thường biểu diễn cluster bằng centroid

  Khoảng cách thường dùng: L1 , L2 (mặc định), L1L2

* Không gian non-euclidean

  Trong không gian non euclidean Không có khái niệm về vị trí và centroid

  Lưu trữ dữ liệu 1 cách trực tiếp

  Các loại khoảng cách thường dùng: jaccard, hamming, cosine

----

## Tiêu chí 3: kích thước dữ liệu
Dữ liệu có chứa vừa trong bộ nhớ ko?

Chia làm 2 loại:

- In-memory: K-means
- large-data clustering: đọc dữ liệu thành từng batch để xử lý (BFR, CURE) \

----

Phương pháp point-assignment: hoạt động tốt với các điểm có hình dạng đa giác lồi

Hierarchical: có thể xử lý dữ liệu kì dị

* Hierarchical (Agglomerative):

1. How to represent a cluster
    * Trường hợp euclidean
        * Biểu diễn các cluster bằng điểm centroid
        * Khoảng cách giữa 2 cluster là khoảng cách giữa 2 centroid
    * Trường hợp non euclidean
        * Tiếp cận 1: chọn ra điểm dữ liệu gần với các điểm dữ liệu còn lại “nhất” để làm điểm đại diện, gọi là clustroid
            * Điểm dữ liệu mà có khoảng-cách-cực-đại-đến-các-điểm-dữ liệu-còn-lại nhỏ nhất
            * Điểm có khoảng-cách-trung-bình-đến-các-điểm-còn-lại nhỏ nhất
            * Điểm có tổng-bình-phương-khoảng-cách-đến-các-điểm-còn-lại nhỏ nhất
            * Khoảng các giữa 2 cluster là 2 clustroid
            * Centroid (không có thật)
        * Tiếp cận 2: Biểu diễn cluster là tập hợp của các điểm
            * Inter-cluster: Khoảng cách liên cụm
            * Khoảng cách nhỏ nhất giữa các cặp điểm, mỗi điểm lấy từ một cluster
            * Trung bình khoảng cách giữa các cặp điểm, mỗi điểm lấy từ một cluster
        * Tiếp Cận 3: Mỗi cluster là tập hợp của các điểm
            * Để đo khoảng cách giữa 2 cluster A và B, gộp thành C, dùng 1 đặc tính của C để làm khoảng cách, ví dụ đường kính, mật độ, …
            * Đường kính của 1 cluster là độ dài (khoảng cách) lớn nhất của 2 điểm trong cluster đó
            * Khoảng cách trung bình: là khoảng cách trung bình của 2 điểm trong cluster
            * Mật độ: là số điểm dữ liệu của C chia cho đường kính của nó
$\frac{|C|}{diameter(C)} = \frac{( |A| + |B| )}{diameter(C)}$

2. When do we stop merging cluster
    * Khi có đủ K-cluster
    * Dừng lại gặp các trường hợp sau:
        * Khi đường kính của 1 cluster vượt quá ngưỡng cho phép
        * Mật độ giảm xuống 1 ngưỡng cho phép
        * Khi quá trinh gộp xảy ra bất thường
    * Gộp đến khi nào còn 1 cluster thì thôi

Trong trường hợp cluster có dạng lồi: phương pháp sử dụng centroid sẽ hiệu quả, tuy nhiên việc hợp nhất 2 cluster sẽ dẫn đến sai số

Trong trường hợp cluster đồng tâm: sử dụng phương pháp link-base (gộp, phân cụm phân cấp)

----
## K-means

Biểu diễn dữ liệu trong không gian euclidean, các điểm dữ liệu là vector

Sử dụng khoảng cách euclidean (L2)

Sử dụng centroid, khoảng cách 2 cluster được đo bằng khoảng cách của 2 centroid


- Khởi tạo k centroid phân biệt
- Lặp đến khi hội tụ
	+ duyệt qua từng điểm dữ liệu, gắn nó vào cluster có centroid gần nó nhất
	+ sau khi gán cập nhật centroid cho từng cluster (tính trung bình của các điểm dữ liệu)
- Hội tụ: khi các centroid di chuyển không đáng kể (tổng độ dời của các centroid không vượt quá 1 ngưỡng cho phép)

K-mean phù hợp với dữ liệu có dạng lồi

Để đánh giá kết quả phân cụm, ta có thể sử dụng trung bình khoảng cách (hoặc bình phương khoảng cách) mỗi điểm dữ liệu đến centroid của nó

$$
Avg = \frac{1}{N} \sum_{i}^{N} d(Xi, Ci)
$$

Để xác định giá trị k phù hợp, lần lượt khảo sát nhiều giá trị k để tìm giá trị tốt nhất

----

## BFR algorithm

_ Biểu diễn dữ liệu trong không gian euclidean, các điểm dữ liệu là vector \
_ Sử dụng khoảng cách euclidean (L2) \
_ Sử dụng centroid, khoảng cách 2 cluster được đo bằng khoảng cách của 2 centroid \
_ phù hợp với dữ liệu có các cluster dạng lồi 


Tổng quan thuật toán BFR \
Bước 1: khởi tạo k centroid phân biệt

Bước 2: load 1 batch (lô) dữ liệu

Bước 3: với lô dữ liệu vừa load, các điểm dữ liệu nào đủ nào đủ gần các centroid thì gán nó vào cluster tương ứng (distance threshold)

Bước 4: các điểm dữ liệu ko được gán, tiến hành phân cụm để tạo ra các cluster mới

Bước 5: gộp các cluster mới với các cluster hiện có nếu đủ gần

Bước 6: lặp bước từ 2 đến 5 đến khi nào đến hết dữ liệu thì thôi \


* Bước 1: tạo k centroid phân biệt. Load lô dữ liệu đầu tiên, chọn 1 trong 3 cách sau để khởi tạo
  + chọn ngẫu nhiên k điểm dữ liệu có sẵn trong lô
  + tiến hành phân cụm lô dữ liệu để có k centroid
  + lần lượt chọn từng điểm dữ liệu, sao cho điểm dữ liệu đến sau càng xa điểm dữ liệu đến trước càng tốt
* Bước 2: load 1 lô dữ liệu
* Bước 3: với các điểm dữ liệu nào đủ gần các centroid thì gán nó vào cluster gần nhất (các điểm này đưa vào DS)
* Bước 4: với các điểm dữ liệu còn lại và đang có trong RS, sử dụng thuật toán phân cụm in-memory.Các điểm dữ liệu mà được gán vào cluster thì đưa vào CS, các điểm dữ liệu outliner đưa vào RS
* Bước 5: Với các điểm dữ liệu trong DS, summarize các cluster tương ứngCó thể gộp các CS với DS
* Bước 6: lặp lại 2-5. Khi hết tập dữ liệu, gộp các cluster trong CS và các điểm trong RS vào trong cluster gần nhất

Discard set (DS): chứa các điểm dữ liệu đủ gần với các centroid và được gán vào các cluster tương ứng \
Compressed set (CS): Chứa các điểm dữ liệu gần nhau nhưng ko đủ gần các centroid \
Retained set (RS): chứa các điểm dữ liệu cô lập, điểm dữ liệu outliner

1 cluster trong thuật toán BFR được biểu diễn (summarized) với các thông tin
+ N: số điểm dữ liệu
+ vector SUM: chứa tổng các điểm dữ liệu
+ vector SUMSQ: tổng bình phương của các điểm dữ liệu
Variance càng nhỏ thì kết quả phân cụm càng tốt

----
## CURE (Clustering Using Respresentative) algorithm

Dùng trong không gian Euclidean \
Có thể lý dữ liệu có hình dạng bất kì \
Không sử dụng centroid - biểu diễn cluster bằng 1 tập hợp các điểm đại diện \

Đây là thuật toán có 2 Pass\
Pass 1:\
_ load ngẫu nhiên 1 phần nhỏ dữ liệu, tiến hành phân cụm phân cấp bằng thuật toán in-memory\
_ Chọn điểm đại diện cho các cluster: \
+ càng xa nhau càng tốt
+ sau đó di chuyển điểm đại diện hướng vào tâm cluster
+ gộp các cluster có điểm đại diện gần nhau

Pass 2:\
_ duyệt qua tập dữ liệu 1 lần nữa, gán điểm dữ liệu vào cluster có điểm đại diện gần nó nhất
