

----

A: là ma trận input đầu vào \
U: là ma trận left singular vectors, U được gộp từ các cột mỗi cột là một vector \
V: được ghép từ nhiều dòng, mỗi dòng được gọi là right singular vectors \
Sigma: là ma trận singular values

----

SVD: singular value decomposition

Trong phân rã SVD \
_ U, $Σ$, V là duy nhất \
_ U, V có tính chất orthonormal (trực chuẩn) \
_ $U^T . U =  I, V^T . V = I$ \
_ ma trận Σ: là ma trận đường chéo, các phần tử trên dường chéo chính ko âm và được sắp xếp giảm dần ($\sigma_1 >= \sigma_2 >= … >= \sigma$) \

Ma trận U thể hiện tương quan giữa người dùng và concept dữ liệu \
Ma trận V thể hiện sự tương quan giữa bộ phim và concept \
Ma trận sigma thể hiện tỉ trọng hay cường độ của các trend \

----

Frobenius Norm: \
$|| M || _F = \sqrt{Σ(M_{ij})^2} $

----
Energy của 1 ma trận đường chéo = tổng bình phương của các phần tử trên đường chéo chính \
Xóa các phần từ trên sigma từ nhỏ tới lớn đảm bảo energy tối thiểu còn lại 90% \
\
EigenPair = {ergenvector, ergenvalue} \
$A.v = v.λ$ \
A(2, 3, 1) = vector OA = (2, 3, 1) = 2i + 3j + k (linear combination) \

$OA.i = I.2$ \
$OA.j = j.3$ \
$OA.k = k.1$

Ergen vector: Power Iteration
M: symmetric
X0 \
$X = \frac{M_x}{||Mx||_F}$ \

$λ = x^T.M_x$ \

$M = M - λ$

+ ưu điểm: tối ưu cho việc xấp xỉ các ma trận hạng thấp
+ khuyết điểm: Khó diễn giải, khó giải thích
+ U và V là 2 ma trận dense

----
CUR decomposition

A = C.U.R

C: chọn ngẫu nhiên các cột trên ma trận A ghép thành ma trận C \
R: Chọn ngẫu nhiên các dòng trên A ghép thành ma trận R \
U: Ghép các phần tử tại giao điểm giữa các dòng và các cột thành ma trận W, DÙng thuật toán SVD phân rã ma trận W thành X.Z.Y^T \
Tìm ma trận W^+ là giả nghịch đảo (pseudo inverse) = $Y.(Z+).X^T$ \
$U = Y.(Z+)^2.X^T$ \


Important của 1 vector là tổng binh phương các phần tử của vector đó \
Ưu Tiên chọn các vector có important lớn (có nhiều phần tử khác 0) để thành lập C và R \
Chọn các dòng các cột có xác suất tỉ lệ thuận important \

Ưu điểm: dễ hiểu, C và R là ma trận thưa \
Khuyết điểm: dễ chọn dữ liệu trùng lặp \

Trong thuật toán SVD: \
Embedding của từng cột là $U.\Sigma$ \
Embedding của từng dòng là $V.\Sigma$