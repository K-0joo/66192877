// 수정하기 버튼 클릭 시
const editButton = document.getElementById("editButton");
if (editButton) {
    editButton.addEventListener("click", function () {
        const boardId = editButton.getAttribute("data-board-id");
        location.href = `/api/board/edit/${boardId}`; // 수정 페이지로 이동
    });
}

document.getElementById("deleteButton").addEventListener("click", function () {
    const boardId = this.getAttribute("data-board-id");

    if (confirm("정말 삭제하시겠습니까?")) {
        fetch(`/api/board/delete/${boardId}`, {
            method: "DELETE",
        })
            .then((response) => {
                if (response.ok) {
                    alert("게시글이 삭제되었습니다.");
                    location.href = "/api/board"; // 게시판 목록으로 이동
                } else {
                    return response.json().then((data) => {
                        alert(data.message || "게시글 삭제에 실패했습니다.");
                    });
                }
            })
            .catch((error) => {
                console.error("삭제 요청 중 오류 발생:", error);
                alert("서버 오류로 게시글 삭제에 실패했습니다.");
            });
    }
});
