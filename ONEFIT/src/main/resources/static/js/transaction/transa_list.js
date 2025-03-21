// 일반 게시글: 페이지 번호 클릭 시 이벤트를 처리하는 함수
document.querySelector("#listPagination").addEventListener("click", function (e) {
    paginationEvent(e, 12);
}, false);

function paginationEvent(e, size, code) {
    console.log("페이지 번호 클릭됨!")

    e.preventDefault();
    e.stopPropagation();

    const target = e.target;

    if (target.tagName !== 'A') {
        console.log("-- 리턴 --")
        return;
    }

    const num = target.getAttribute("data-num");

    console.log("num: " + num);

    const formObj = document.querySelector("form");

    formObj.innerHTML += `<input type="hidden" name="page" value="${num}">`;

    formObj.innerHTML += `<input type="hidden" name="size" value="${size}">`;

    formObj.submit();
}


