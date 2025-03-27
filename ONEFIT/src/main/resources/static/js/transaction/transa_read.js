function openModal(imgElement) {
    document.getElementById('modalImage').src = imgElement.src; // 클릭한 이미지 src 가져오기
    let imageModal = new bootstrap.Modal(document.getElementById('imageModal')); // 부트스트랩 모달 열기
    imageModal.show();
}

// $("#closeModalBtn").click(function(){
//     $("#modalImage").modal("hide");
// });

document.getElementById("closeModalBtn").addEventListener("click", function () {
    let modalElement = document.getElementById("imageModal");
    let modal = new bootstrap.Modal(modalElement);

    console.log("ddddd");
    console.log(modal);

    modal.hide();
});

// 오픈 채팅 바로가기 팝업창을 띄우는 함수
function openChatPopup() {
    var link = document.getElementById("chatButton").getAttribute("data-link");
    if (link) {
        window.open(link, "_blank", "width=1000,height=800,scrollbars=yes,resizable=yes");
    }
}

// 삭제 버튼
document.querySelector(".removeBtn").addEventListener("click", function (e) {
    e.preventDefault();  // 버튼 클릭 시 페이지 리로드를 방지합니다.

    // 폼 생성
    const formObj = document.createElement('form');
    formObj.action = '/transaction/remove';  // 폼이 제출될 URL
    formObj.method = 'POST';  // POST 방식으로 전송

    const productId = getProductIdFromURL();  // URL에서 productId 가져오기

    if (productId) {
        // productId 값으로 hidden 필드 추가
        const productIdField = document.createElement('input');
        productIdField.type = 'hidden';
        productIdField.name = 'productId';
        productIdField.value = productId;
        formObj.appendChild(productIdField);

        // 폼 전송
        document.body.appendChild(formObj);  // 폼을 문서에 추가
        formObj.submit();  // 폼 제출
    }

}, false);

// URL에서 productId 값을 가져오는 함수
function getProductIdFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('productId');  // productId 값을 반환
}

// (상품 - 기구) 댓글: 페이지 번호 클릭 시 이벤트를 처리하는 함수
const eqPagination = document.querySelector("#eqReplyPagination");
if (eqPagination != null) {
    eqPagination.addEventListener("click", function (e) {
        replyPaginationEvent(e, 5, 'eq');
    }, false);
}

// (상품 - 시설) 댓글: 페이지 번호 클릭 시 이벤트를 처리하는 함수
const faPagination = document.querySelector("#faReplyPagination");
if (faPagination != null) {
    faPagination.addEventListener("click", function (e) {
        replyPaginationEvent(e, 5, 'fa');
    }, false);
}

function replyPaginationEvent(e, size, role) {
    console.log("댓글 페이지 번호 클릭됨!");

    const target = e.target;
    if (target.tagName !== "A") {
        console.log("-- 리턴 --");
        return;
    }

    const num = target.getAttribute("data-num");

    // form 요소 생성
    const formObj = document.createElement("form");
    formObj.method = "get"; // 전송 방식
    formObj.action = "/transaction/transa_" + role + "_read"; // 데이터를 보낼 서버 URL

    // input 요소 추가 (appendChild 사용)
    const pageInput = document.createElement("input");
    pageInput.type = "hidden";
    pageInput.name = "page";
    pageInput.value = num;
    formObj.appendChild(pageInput);

    const sizeInput = document.createElement("input");
    sizeInput.type = "hidden";
    sizeInput.name = "size";
    sizeInput.value = size;
    formObj.appendChild(sizeInput);

    const urlParams = new URLSearchParams(window.location.search);
    let productId = urlParams.get("productId"); // productId 값 가져오기
    const productIdInput = document.createElement("input");
    productIdInput.type = "hidden";
    productIdInput.name = "productId";
    productIdInput.value = productId;
    formObj.appendChild(productIdInput);

    // 🔹 중요! form을 문서에 추가한 후 submit 실행
    document.body.appendChild(formObj);

    formObj.submit();
}

document.addEventListener("DOMContentLoaded", function () {
    let modifyReplyModal = document.getElementById("modifyReplyModal");

    modifyReplyModal.addEventListener("show.bs.modal", function (event) {
        let button = event.relatedTarget;  // 클릭한 버튼
        let productId = button.getAttribute("data-product-id");
        let productReplyId = button.getAttribute("data-reply-id");
        let pReplyText = button.getAttribute("data-reply-content");

        // 모달 내에 값 채우기
        document.getElementById("productReplyId").value = productReplyId;
        document.getElementById("productId").value = productId;
        document.getElementById("pReplyText").value = pReplyText;

        console.log(productReplyId);
        console.log(pReplyText);
    });

});

// 댓글 삭제 버튼
document.querySelectorAll(".remove-reply-btn").forEach(button => {
    button.addEventListener("click", function (e) {
    e.preventDefault();  // 버튼 클릭 시 페이지 리로드를 방지

    if (!confirm("댓글을 삭제하시겠습니까?")) {
        return;
    }

    const button = e.target; // 클릭된 버튼 요소 가져오기

    // 폼 생성
    const formObj = document.createElement('form');
    formObj.action = '/product_reply/remove';  // 폼이 제출될 URL
    formObj.method = 'POST';  // POST 방식으로 전송

    // productId와 productReplyId 가져오기
    let productId = button.getAttribute("data-product-id");
    let productReplyId = button.getAttribute("data-reply-id");
    let productRole = button.getAttribute("data-product-role");

    // hidden input 요소 생성
    const productIdInput = document.createElement('input');
    productIdInput.type = 'hidden';
    productIdInput.name = 'productId';
    productIdInput.value = productId;

    const productReplyIdInput = document.createElement('input');
    productReplyIdInput.type = 'hidden';
    productReplyIdInput.name = 'productReplyId';
    productReplyIdInput.value = productReplyId;

    const productRoleInput = document.createElement('input');
    productRoleInput.type = 'hidden';
    productRoleInput.name = 'productRole';
    productRoleInput.value = productRole;


    // 폼에 hidden input 추가
    formObj.appendChild(productIdInput);
    formObj.appendChild(productReplyIdInput);
    formObj.appendChild(productRoleInput);

    console.log(formObj);

    // 폼을 문서에 추가하고 제출
    document.body.appendChild(formObj);
    formObj.submit();
    });
});

// 관심상품 버튼 js
document.addEventListener("DOMContentLoaded", function () {
    const likeIcon = document.querySelector(".like i");

    // 하트 클릭 시 아이콘 변경
    likeIcon.addEventListener("click", function () {
        if (likeIcon.classList.contains("fa-regular")) {
            likeIcon.classList.remove("fa-regular");
            likeIcon.classList.add("fa-solid");
        } else {
            likeIcon.classList.remove("fa-solid");
            likeIcon.classList.add("fa-regular");
        }
    });

    // `productId`를 URL에서 가져와서 폼을 만들어 전송하는 기능
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('productId'); // URL에서 productId 값을 가져옴

    if (productId) {
        // 폼 생성
        const form = document.createElement("form");
        form.method = "POST";
        form.action = "/your-endpoint"; // 전송할 URL로 변경

        // hidden 필드로 productId 추가
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "productId";
        input.value = productId;

        form.appendChild(input);

        // 폼 전송
        // form.submit();  // 이 줄을 활성화하면 폼이 자동으로 전송됩니다.
    }
});




