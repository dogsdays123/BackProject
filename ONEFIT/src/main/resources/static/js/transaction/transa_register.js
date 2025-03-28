// (거래) 게시글 등록

// 제출 버튼 클릭 시 이벤트 처리
document.querySelector(".submitBtn").addEventListener("click", function (e) {
    e.preventDefault();
    e.stopPropagation();

    let categoryId = $("select[name='categoryId']");
    let eUseStart = $("#eUseStart").val();
    let eUseEnd =  $("#eUseEnd").val();
    let pContent = $("textarea[name='pContent']").val().trim();

    // 제목
    if ($.trim($('#pTitle').val()) === '') {
        $("#pTitleFeedback").show();
        $("#pTitle").focus();
        return;
    }
    // 판매가
    if ($.trim($('#pPrice').val()) === '') {
        $("#pPriceFeedback").show();
        $("#pPrice").focus();
        return;
    }
    // 카테고리
    if (categoryId.val() === null || categoryId.val() === "") {
        $("#pCategoryFeedback").show();
        categoryId.focus();
        return;
    }

    // 판매 내용
    if (pContent === "") {
        $("#pContentFeedback").show();
        categoryId.focus();
        return;
    }
    //희망 거래 장소
    if ($.trim($('#addressInput').val()) === '') {
        $("#pAddrFeedback").show();
        $("#addressInput").focus();
        return;
    }
    // 오픈채팅
    if ($.trim($('#pChatUrl').val()) === '') {
        $("#urlEmptyFeedback").show();
        $("#pChatUrl").focus();
        return;
    }

    // 사용 시작일
    if (!eUseStart) {
        $("#startDateFeedback").show().text("사용 시작일을 입력해주세요.");
        eUseStart.focus();
        return;
    }
    // 사용 종료일
    if (!eUseEnd) {
        $("#endDateFeedback").show().text("사용 종료일을 입력해주세요.");
        eUseEnd.focus();
        return;
    }

    const target = document.querySelector(".uploadHidden");

    const imagePreview = document.querySelector("#imagePreview");
    const uploadFiles = imagePreview.querySelectorAll("img");

    if (uploadFiles.length === 0) {
        $("#imageFeedback").show();
        $("#imageUpload").focus();
        return;
    }

    let str = '';

    for (let i = 0; i < uploadFiles.length; i++) {
        const uploadFile = uploadFiles[i];
        const imgLink = uploadFile.getAttribute("data-src"); // 이미지의 data-src 속성 값 가져오기

        str += `<input type="hidden" name="imageFileNames" value="${imgLink}">`;
    }

    // 희망 거래 장소 -> 시/도, 시/군/구 분리
    let addr = document.getElementById("addressInput").value;

    const addrArr = extractRegions(addr);

    if (addrArr) {
        // 시/도
        str += `<input type="hidden" name="pAddrMetroGov" value="${addrArr[0]}">`;
        // 시/군/구
        str += `<input type="hidden" name="pAddrMuniGov" value="${addrArr[1]}">`;
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

function removeFile(uuid, fileName, obj) {
    console.log(uuid);
    console.log(fileName);

    const targetDiv = obj.closest(".card");

    removeFileToServer(uuid, fileName).then(data => {
        console.log("파일 삭제 완: " + data);

        // 삭제 성공 시 해당 카드 요소 제거
        targetDiv.remove();
    });
}

// 희망 거래 장소에서 시/도, 시/군/구를 분리해주는 함수
function extractRegions(address) {
    const pattern = /^(서울|부산|대구|인천|광주|대전|울산|세종|강원|경기|경상남도|경상북도|전라남도|전라북도|충청남도|충청북도|제주특별자치도)(?:특별시|광역시|도)?\s+(.*?시|.*?군|.*?구)/;
    const match = address.match(pattern);

    if (match) {
        return [match[1], match[2]]; // [광역자치단체(시/도), 기초자치단체(시/군/구)]
    }

    return null;
}

// 올바른 카카오톡 오픈채팅 링크인지 검증하는 함수
function validateAndFormatLink(link) {
    const regex = /^https:\/\/open\.kakao\.com\/o\/[A-Za-z0-9]+$/;

    if (link.includes("open.kakao.com/o/")) {
        if (!link.startsWith("https://")) {

            link = "https://" + link;
        }
        return regex.test(link);
    }
    return null;
}

// 입력 값 실시간 검증 js
$(document).ready(function () {
    function validateForm() {
        let isValid = true;

        let startDate = new Date($("#eUseStart").val());
        let endDate = new Date($("#eUseEnd").val());
        let chatUrl = $("#pChatUrl").val().trim();

        // 오픈채팅 URL 검증
        if (chatUrl !== "" && !validateAndFormatLink(chatUrl)) {
            $("#urlFeedback").show().text("카카오 오픈채팅 URL 형식이 아닙니다.");
            isValid = false;
        } else {
            $("#urlFeedback").hide();
        }

        // 사용 종료일 검증 (시작일보다 늦어야 함)
        if ($("#eUseStart").val() && $("#eUseEnd").val()) {
            if (endDate <= startDate) {
                $("#endDateFeedback").show().text("사용 종료일은 시작일보다 늦어야 합니다.");
                isValid = false;
            } else {
                $("#startDateFeedback").hide();
                $("#endDateFeedback").hide();
            }
        }

        // 버튼 활성화/비활성화
        $(".submitBtn").prop("disabled", !isValid);
    }

    // 제목
    $("#pTitle").on("input", function () {
        let pTitle = $(this).val().trim();

        if (pTitle === "") {
            $("#pTitleFeedback").show();  // 경고 문구 표시
        } else {
            $("#pTitleFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 판매가
    $("#pPrice").on("input", function () {
        let pPrice = $(this).val().trim();

        if (pPrice === "") {
            $("#pPriceFeedback").show();  // 경고 문구 표시
        } else {
            $("#pPriceFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 카테고리
    $("select[name='categoryId']").on("input", function () {
        let categoryId = $(this).val();

        if (categoryId === null || categoryId === "") {
            $("#pCategoryFeedback").show();  // 경고 문구 표시
        } else {
            $("#pCategoryFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 판매 내용
    $("textarea[name='pContent']").on("input", function () {
        let pContent = $(this).val().trim();

        if (pContent === "") {
            $("#pContentFeedback").show();  // 경고 문구 표시
        } else {
            $("#pContentFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 주소
    $("#addressInput").on("input", function () {
        let pAddr = $(this).val().trim();
        console.log("주소: " + pAddr);

        if (pAddr === "") {
            $("#pAddrFeedback").show();  // 경고 문구 표시
        } else {
            $("#pAddrFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 카카오 오픈채팅
    $("#pChatUrl").on("input", function () {
        let pChatUrl = $(this).val().trim();

        if (pChatUrl === "") {
            $("#urlEmptyFeedback").show();  // 경고 문구 표시
        } else {
            $("#urlEmptyFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 이미지 미리보기
    $("#imagePreview img").on("input", function () {
        let uploadFiles = $(this).val().trim();

        if (uploadFiles.length === 0) {
            $("#imageFeedback").show();  // 경고 문구 표시
        } else {
            $("#imageFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 사용 시작일
    $("#eUseStart").on("input", function () {
        let eUseStart = $(this).val();

        if (eUseStart) {
            $("#startDateFeedback").show().text("사용 시작일을 입력해주세요.");  // 경고 문구 표시
        } else {
            $("#startDateFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    // 사용 종료일
    $("#eUseEnd").on("input", function () {
        let eUseEnd = $(this).val();

        if (eUseEnd) {
            $("#endDateFeedback").show().text("사용 종료일을 입력해주세요.");  // 경고 문구 표시
        } else {
            $("#endDateFeedback").hide();  // 입력 시 경고 문구 숨김
        }
    });

    $("#pChatUrl, #eUseStart, #eUseEnd").on("input", validateForm);

});

