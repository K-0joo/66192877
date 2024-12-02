document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            const id = document.getElementById("id").value;
            const password = document.getElementById("password").value;

            if (!id || !password) {
                alert("아이디와 비밀번호를 모두 입력해주세요.");
                return;
            }

            console.log(`ID: ${id}, Password: ${password}`); // 디버깅용

            const formData = new URLSearchParams();
            formData.append("id", id);
            formData.append("password", password);

            try {
                const response = await fetch('/api/member/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData.toString(),
                });

                if (response.ok) {
                    alert("로그인 성공!");
                    window.location.href = "/api/board";
                } else {
                    alert("로그인 실패. 다시 시도해주세요.");
                }
            } catch (error) {
                console.error("오류 발생:", error);
                alert("서버 요청 중 오류가 발생했습니다.");
            }

        });
    } else {
        console.error("loginForm 요소를 찾을 수 없습니다.");
    }
});
