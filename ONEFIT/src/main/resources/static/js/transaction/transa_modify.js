// 게시글 (수정)

// 삭제할 파일 정보 리스트
const removeFileList = [];

// 제출 버튼 클릭 시 이벤트 처리
document.querySelector(".submitBtn").addEventListener("click", function (e) {
    e.preventDefault();
    e.stopPropagation();

    const target = document.querySelector(".uploadHidden");

    const imagePreview = document.querySelector("#imagePreview");
    const uploadFiles = imagePreview.querySelectorAll("img");

    let str = '';

    // 업로드 될 파일 정보 리스트들을 숨겨진 요소로 추가
    for (let i = 0; i < uploadFiles.length; i++) {
        const uploadFile = uploadFiles[i];
        const imgLink = uploadFile.getAttribute("data-src"); // 이미지의 data-src 속성 값 가져오기

        str += `<input type="hidden" name="imageFileNames" value="${imgLink}">`;
    }

    // 삭제할 파일 리스트들을 숨겨진 요소로 추가
    removeFileList.forEach(({uuid, fileName}) => {
        str += `<input type="hidden" name="removeImageFileUuid" value="${uuid}">`;
    });

    target.innerHTML = str;

    // 삭제 대상 파일들에 대한 삭제 요청
    callRemoveFiles();

    document.querySelector("form").submit();
}, false);


// 이미지 미리보기 기능
document.addEventListener("DOMContentLoaded", function () {
    // 이미지 미리보기 기능 (복수 이미지 등록)
    $("#imageUpload").on("change", function () {
        const formObj = new FormData(); /// ++

        // $("#imagePreview").html("");
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
                    <div class="position-relative d-inline-block me-2 mt-2 preview-img-div">
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

// 이미지 파일을 삭제하는 함수
function removeFile(uuid, fileName, obj) {
    if (!confirm("파일을 삭제하시겠습니까?")) {
        return; // 취소 버튼 클릭 시 아무 동작도 하지 않음
    }

    // 삭제할 파일 정보 입력
    console.log(uuid);
    console.log(fileName);
    console.log(obj);

    // 삭제할 파일 정보를 리스트에 추가
    removeFileList.push({uuid, fileName});
    obj.closest('.position-relative.d-inline-block').remove();
}

function callRemoveFiles() {
    removeFileList.forEach(({uuid, fileName}) => {
        removeFileToServer(uuid, fileName).then(data => {
            console.log("파일 삭제 완: " + data);
        });
    });
}


