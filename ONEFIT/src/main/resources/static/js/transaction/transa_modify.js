// 미완성

// 삭제할 파일 정보 리스트
const removeFileList = [];

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

    //
    const targetDiv = obj.closest(".card");

    // 해당 카드 요소 제거 (파일 삭제 UI 처리)
    targetDiv.remove();
}

document.querySelector(".removeBtn").addEventListener("click", function (e) {
    e.preventDefault();
    e.stopPropagation();

    appendFileData();

    appendNotShownData();

    // 화면에서 안 보이도록 처리한 파일들을 form 태그에 추가
    formObj.action = `/board/remove`;
    formObj.method = 'post';
    formObj.submit();

}, false);

// 삭제할 파일들의 정보를 숨겨진 입력 필드로 추가하는 함수
function appendNotShownData() {
    // 삭제할 파일이 없다면 함수 종료
    if(removeFileList.length == 0) {
        return;
    }
    // 숨겨진 데이터 영역 가져오기
    const target = document.querySelector(".uploadHidden");
    let str = '';
    // 삭제할 파일 리스트를 순회하면서 숨겨진 입력 필드를 추가
    for (let i = 0; i < removeFileList.length; i++) {
        const {uuid, fileName} = removeFileList[i];
        // 파일 정보(uuid, 파일명)를 숨겨진 입력 요소로 추가
        str += `<input type="hidden" name="fileNames" value="${uuid}_${fileName}">`;
    }
    // 생성된 숨겨진 입력 필드를 target 영역에 추가
    target.innerHTML += str;
}

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