# LSH alogorithm

LSH include 3 step:

  * Step1: shingling

  * Step2: Min-Hashing

  * Step3: locality-sensitive hashing

---

## Step1: convert document into boolean vector

K-shingle have 2 benefic:

- Tài liệu có nhiều điểm chung thì sẽ có nhiều shingle chung
- Khi thay đổi 1 từ thì có k-shingle ảnh hưởng

Jaccard similarity:

$
\begin{equation}
Sim(D1, D2)=  \frac{|C1 \cup C2|}{|C1 \cup C2|}
\end{equation}
$

Jaccard distance:

$
\begin{equation}
d(D1, D2) = 1 - Sim(D1, D2)
\end{equation}
$


---
## Step2: min-Hashing

**Convert boolean vector into signature vector**

  Sử dụng 1 hàm hash để chuyển đổi 1 vector nhị phân sang vector signature sao cho bảo toàn tính tương đồng

- Bước 1: sử dụng hàm pi nhận vào 1 vector nhị phân, sắp xếp lại các phần tử theo 1 thứ tự nhất định

- Bước 2: Giá trị min hash của 1 vector là giá trị đầu tiên khác 0

- Bước 3: Sử dụng nhiều hoán vị, tìm giá trị min hash tương ứng, ghép thành vector signature

Độ tương đồng của 2 vector signature là tỷ lệ số cặp phần tử khớp nhau chia cho độ dài

---

## Step 3: locality-sensitive hashing

- Tìm ra các candidate pair
- Chia vector signature ra thành b(band) đoạn mỗi đoạn có n dòng
- Hash mỗi band và bỏ vào bucket tương ứng
- 2 vector signature đc gọi là candidate nếu chúng ít nhất, tối thiểu 1 cặp band nằm chung 1 bucket