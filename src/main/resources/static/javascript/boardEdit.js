document.addEventListener("DOMContentLoaded", function () {
    const editForm = document.getElementById("editForm");

    editForm.addEventListener("submit", function (event) {
        event.preventDefault(); // 기본 동작 방지

        const boardIdInput = document.getElementById("boardId");
        if (!boardIdInput) {
            console.error("boardId 요소를 찾을 수 없습니다.");
            return;
        }

        const boardId = boardIdInput.value;
        const title = document.getElementById("title").value.trim();
        const context = document.getElementById("context").value.trim();

        if (!title || !context) {
            alert("제목과 내용을 모두 입력해주세요.");
            return;
        }

        const updateData = { title, context };

        fetch(`/api/board/edit/${boardId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updateData),
        })
            .then((response) => {
                if (response.ok) {
                    alert("게시글이 수정되었습니다.");
                    location.href = `/api/board/${boardId}`; // 상세 페이지로 이동
                } else {
                    return response.json().then((data) => {
                        alert(data.message || "게시글 수정에 실패했습니다.");
                    });
                }
            })
            .catch((error) => {
                console.error("수정 요청 중 오류 발생:", error);
                alert("서버 오류로 게시글 수정에 실패했습니다.");
            });
    });
});
