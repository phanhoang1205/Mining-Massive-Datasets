# Frequent Itemsets

## Mô hình hóa bài toán

Frequent itemsets: tập phổ biến là các sản phẩm đc mua chung của người dùng

Support: số lần tập phầm tử được mua

An items is frequent <=> $sup(I) \geq$ Support threshold

---
## Association rule:

VT -> VP

---
## Confidence of association rule:

Là xác suất người dùng lấy sản phẩm bên ở vế phải khi họ lấy các sản phẩm ở vế trái

\begin{equation}
Conf(I -> j)
\end{equation}

  * I: là lấy hết sản phẩm ở vế trái

  * j: là lấy hết sản phẩm vế phải

$I_j = I \cup {j}$

P(x) = sup(X) / baskets

\begin{equation}
Conf(I -> j) = \frac{Sup(I \cup {j})}{Sup(I)} = \frac{P(I \cup j)}{P(I)} =
\frac{P(I_j)}{P(I)} = P(j | I)
\end{equation}

---
## Interest of association rule
Là trị tuyệt đối giữa hiệu số của conf và xác suất của vế phải

\begin{equation}
Interest(I -> j) = | conf(I -> j) - P[j] | = |P(j|I) - P(j)|
\end{equation}

Interest càng lớn confi càng có ý nghĩa

---
## Association Rule Mining
Tìm ra các luật liên kết mà ngưỡng $support \geq s$ và $confidence \leq c$

Step 1: tìm các frequent Itemset

Step 2: phát sinh luật và chọn lọc

---
## Evaluation
Đánh giá mức độ phức tạp của thuật toán qua thời gian được đánh giá bằng pass

Pass: là 1 lần duyệt qua tập dữ liệu

sparse: thưa (ít)

Dense: dày

---
## A-priori

A-priori là thuật toán có 2-pass
* Monotonicity:

  Nếu tập I là tập phổ biến thì tập con của nó cũng là tập phổ biến

  Nếu 1 phần tử i không phổ biến thì các tập chứa i cũng vậy

1. Pass 1:

  duyệt qua giỏ hàng, tìm support cho từng phần tử riêng lẻ. Lọc bỏ các phần tử không phổ biến

2. Pass 2:

  duyệt qua các giỏ hàng, tìm support cho các cặp mà cả 2 phần tử phổ biến. Lọc bỏ các cặp ko phổ biến

Thuật toán A-priori với k passes có thể tìm đc tập phổ biến với k phần tử

---
## PCY algorithm

* Pass 1:

  Support của 1 bucket bằng tổng support của các pair đưa vào bucket đó

  Nếu 1 bucket không phổ biến thì các pair trong bucket đó không phổ biến

* Pass 2:

  Chỉ đếm những cái pair nào mà cả 2 phần tử là phổ biến và cái pair này thuộc về 1 cái bucket phổ biến