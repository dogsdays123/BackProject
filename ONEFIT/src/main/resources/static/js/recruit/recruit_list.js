document.querySelector(".pagination").addEventListener("click", function (e) {
    e.preventDefault();
    e.stopPropagation();

    const target = e.target;

    // 클릭된 요소가 <a> 태그인지 확인
    if (target.tagName !== 'A') {
        return;
    }

    // 페이지 번호를 가져옵니다.
    const num = target.getAttribute("data-num");

    // form 객체를 찾아서 해당 페이지 번호를 hidden input으로 추가
    const formObj = document.querySelector("form");

    // hidden input 추가
    formObj.innerHTML += `<input type='hidden' name='page' value='${num}'>`;

    // 폼 제출
    formObj.submit();
}, false);

document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('[data-bs-toggle="collapse"]');

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            const target = document.querySelector(button.getAttribute("data-bs-target"));

            // 현재 열려 있는 collapse 요소 찾기
            document.querySelectorAll(".collapse.show").forEach(openCollapse => {
                if (openCollapse !== target) {
                    new bootstrap.Collapse(openCollapse, { toggle: true });
                }
            });
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const applyBtn = document.getElementById("applyBtn");
    const modalTitle = document.getElementById("applyModalLabel");
    const modalBody = document.querySelector(".apply-modal-body p");
    const applyModal = new bootstrap.Modal(document.getElementById("applyModal"));

    applyBtn.addEventListener("click", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지primary

        // 모달 제목 및 내용 설정
        modalTitle.innerHTML = '<span class="rogo-span">OneFit</span> 지원 확인';
        modalBody.innerHTML = "해당 공고에 이력서를 지원하시겠습니까?";

        // 모달 열기
        applyModal.show();
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const registerBtn = document.getElementById("registerBtn");
    const modalTitle = document.getElementById("registerModalLabel");
    const modalBody = document.querySelector(".register-modal-body p");
    const registerModal = new bootstrap.Modal(document.getElementById("registerModal"));

    registerBtn.addEventListener("click", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 모달 제목 및 내용 설정
        modalTitle.innerHTML = '<span class="rogo-span">OneFit</span> 공고 등록 확인';
        modalBody.innerHTML = "채용 공고 등록을 진행하시겠습니까?";

        // 모달 열기
        registerModal.show();
    });
});

// document.addEventListener("DOMContentLoaded", function () {
//     // X 버튼 클릭 시 모달 닫기
//     document.querySelector(".btn-close").addEventListener("click", function () {
//         var myModal = bootstrap.Modal.getInstance(document.getElementById('registerModal'));
//         if (myModal) {
//             myModal.hide();
//         }
//     });
// });