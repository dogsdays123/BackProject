// (거래) 게시글 등록

// 제출 버튼 클릭 시 이벤트 처리
document.querySelector(".submitBtn").addEventListener("click", function (e) {
    e.preventDefault();
    e.stopPropagation();

    const target = document.querySelector(".uploadHidden");

    const imagePreview = document.querySelector("#imagePreview");
    const uploadFiles = imagePreview.querySelectorAll("img");

    let str = '';

    for (let i = 0; i < uploadFiles.length; i++) {
        const uploadFile = uploadFiles[i];
        const imgLink = uploadFile.getAttribute("data-src"); // 이미지의 data-src 속성 값 가져오기

        str += `<input type="hidden" name="imageFileNames" value="${imgLink}">`;
    }

    target.innerHTML = str;

    document.querySelector("form").submit();
}, false);


// 이미지 미리보기 기능
document.addEventListener("DOMContentLoaded", function () {
    // 이미지 미리보기 기능 (복수 이미지 등록)
    $("#imageUpload").on("change", function () {
        const formObj = new FormData(); /// ++

        $("#imagePreview").html("");
        let files = this.files;
        for (let i = 0; i < files.length; i++) {
            formObj.append("files", files[i]); /// ++
        }

        // 서버에 파일 업로드 요청 ++
        uploadToServer(formObj).then(result => {
            console.log(result);
            for (const uploadResult of result) {
                showUploadFile(uploadResult);
            }
        }).catch(e => {
            console.log(e);
            console.log("이미지 파일 업로드 : 예외 발생");
        });
    });

});

function showUploadFile({uuid, fileName, link}) {
    let imgWrapper = $(`
                    <div class="position-relative d-inline-block me-2 mt-2">
                        <img src="/view_transa/${link}" data-src="${uuid + "_" + fileName}" class="preview-img rounded border">
                        <button type="button" 
                        onclick="javascript:removeFile('${uuid}', '${fileName}', this)"
                        class="btn-close position-absolute top-0 start-100 
                        translate-middle p-1 rounded-circle"></button>
                    </div>
                `);

    $("#imagePreview").append(imgWrapper);

    imgWrapper.find(".btn-close").click(function () {
        imgWrapper.remove(); // 클릭 시 이미지 삭제
    });
}


function removeFile(uuid, fileName, obj) {
    console.log(uuid);
    console.log(fileName);

    const targetDiv = obj.closest(".card");

    removeFileToServer(uuid, fileName).then(data => {
        // 삭제 성공 시 해당 카드 요소 제거
        targetDiv.remove();
    });
}