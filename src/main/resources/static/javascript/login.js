async function login() {
    const id = document.getElementById("id").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/api/member/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ id, password }),
    });

    if (response.ok) {
        const result = await response.json();
        console.log("로그인 성공:", result);
        alert("로그인 성공!");
    } else {
        console.error("로그인 실패");
        alert("로그인 실패!");
    }
}
