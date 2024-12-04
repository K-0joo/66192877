// 페이지 로드 후 강제로 새로고침
document.addEventListener('DOMContentLoaded', function () {
    if (!sessionStorage.getItem('reloadOnce')) {
        sessionStorage.setItem('reloadOnce', 'true');
        window.location.reload();
    } else {
        sessionStorage.removeItem('reloadOnce');
    }
});
