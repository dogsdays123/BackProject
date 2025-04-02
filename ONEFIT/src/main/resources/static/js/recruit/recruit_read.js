document.getElementById("applyBtn").addEventListener("click", function () {
    // Bootstrap Modal 객체 생성 후 모달을 띄움
    var applyModal = new bootstrap.Modal(document.getElementById('applyModal'));
    applyModal.show();
});

document.getElementById("confirmApplyBtn").addEventListener("click", function () {
    // form 요소를 가져옵니다
    var form = document.getElementById("applyForm");

    // 폼을 제출합니다
    form.submit();

    // 모달을 닫습니다 (모달을 닫는 방법은 Bootstrap에 맞춰 추가)
    var applyModal = new bootstrap.Modal(document.getElementById('applyModal'));
    applyModal.hide();
});