document.addEventListener("DOMContentLoaded", function () {
    // CKEditor 초기화
    CKEDITOR.replace('context', {
        language: 'ko',
        toolbar: [
            ['Bold', 'Italic', 'Underline', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink', '-', 'Image', 'Table'],
            ['FontSize', 'TextColor', 'BGColor', '-', 'Maximize', 'Source']
        ]
    });

    // Form 데이터와 파일 전송 처리
    document.querySelector("form").addEventListener("submit", function (e) {
        e.preventDefault();

        const formData = new FormData(e.target);

        fetch("/api/board/write", {
            method: "POST",
            body: formData,
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("서버 응답 오류");
                }
            })
            .then((data) => {
                alert("게시글이 성공적으로 저장되었습니다!");
                location.href = "/api/board";
            })
            .catch((err) => {
                console.error("Error:", err);
                alert("게시글 저장 중 오류 발생: " + err.message);
            });
    });
});
