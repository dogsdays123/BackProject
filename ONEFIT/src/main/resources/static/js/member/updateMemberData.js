// AJAX 요청 보내기
$.ajax({
    url: '/checkId', // Controller의 URL로 수정 (예: '/user/checkId')
    type: 'POST',
    data: { allId: allId }, // 전송할 데이터
    success: function(response) {
        // 서버에서 응답이 성공적으로 왔을 때 처리
        if (response.isAvailable) {
            textarea.value = "사용 가능한 아이디입니다.";
            textarea.classList.add('text-success'); // 새 클래스를 추가
            textarea.classList.remove('text-danger'); // 기존 클래스를 제거
            checkAll.idCheck = true;
        } else {
            textarea.value = "이미 존재하는 아이디입니다.";
            textarea.classList.remove('text-success'); // 새 클래스를 추가
            textarea.classList.add('text-danger'); // 기존 클래스를 제거
        }
        var event = new Event('input', {bubbles: true});
        textarea.dispatchEvent(event);

        toggleSignupButton();
        signupButton.dispatchEvent(eventCheck);
    },
    error: function(xhr, status, error) {
        alert("서버 오류" + error);
    }
});