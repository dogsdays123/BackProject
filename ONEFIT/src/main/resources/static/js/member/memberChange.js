 document.querySelectorAll('input[name="memberType"]').forEach(function(radio) {
    radio.addEventListener('change', function() {
        // 선택한 라디오 버튼 값에 따라 창을 띄움
        var selectedValue = document.querySelector('input[name="memberType"]:checked').value;

        var url = '';
        if (selectedValue === 'user') {
            url = '/member/set_type'; // 일반회원 창 열기
        } else {
            url = '/member/set_type_b'; // 개인회원 창 열기
        }

        if (url) {
            // 새 창 열기 (팝업창 크기, 위치 등 설정)
            var newWindow = window.open(url, '새 창', 'width=600,height=800,left=400,top=200');

            // 새 창이 닫힐 때 부모 창 새로 고침
            var checkWindowClosed = setInterval(function() {
                if (newWindow.closed) {
                    clearInterval(checkWindowClosed); // 인터벌 종료
                    location.reload(); // 부모 창 새로 고침
                }
            }, 1000); // 1초마다 새 창이 닫혔는지 체크
        }
    });
});
