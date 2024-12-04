document.querySelector("form").addEventListener("submit", function (e) {
    e.preventDefault(); // 기본 동작 방지

    const formData = new FormData(e.target);

    fetch("/api/board/write", {
        method: "POST",
        body: formData,
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            if (data.code === "BOARD_SUCCESS" && data.data.boardId) {
                const boardId = data.data.boardId;
                console.log(`게시글 작성 완료, boardId: ${boardId}`);
                // 상세 페이지로 이동
                location.href = `/api/board/${boardId}`;
            } else {
                console.error("응답 데이터 오류:", data);
                alert(data.message || "게시글 작성에 실패했습니다.");
            }
        })
        .catch((err) => {
            console.error("에러 발생:", err);
            alert("서버 요청 중 오류 발생: " + err.message);
        });
});
