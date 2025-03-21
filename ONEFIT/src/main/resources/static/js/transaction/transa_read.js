function openModal(imgElement) {
    document.getElementById('modalImage').src = imgElement.src; // 클릭한 이미지 src 가져오기
    let imageModal = new bootstrap.Modal(document.getElementById('imageModal')); // 부트스트랩 모달 열기
    imageModal.show();
}

// $("#closeModalBtn").click(function(){
//     $("#modalImage").modal("hide");
// });


document.getElementById("closeModalBtn").addEventListener("click", function() {
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