// 게시글 (수정)

// 삭제할 파일 정보 리스트
const removeFileList = [];

document.addEventListener("DOMContentLoaded", function () {
    // 제출 버튼 클릭 시 이벤트 처리
    document.querySelector(".submitBtn").addEventListener("click", function (e) {
        e.preventDefault();
        e.stopPropagation();

        let categoryId = $("select[name='categoryId']");
        let pContent = $("textarea[name='pContent']").val().trim();
        // (기구)
        let eName = $("input[name='eName']").val();
        let eBrand = $("input[name='eBrand']").val();
        let eStatus = $("input[name='eStatus']:checked").val();
        let eAs = $("input[name='eAs']:checked").val();
        let ePurPrice = $("input[name='ePurPrice']").val();
        let eUseStart = $("#eUseStart");
        let eUseEnd = $("#eUseEnd");


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
            console.log($.trim($('#pChatUrl').val()));
            $("#urlEmptyFeedback").show();
            $("#pChatUrl").focus();
            return;
        }

        // (기구) 제품명 입력값 검증
        if ($("input[name='eName']").length > 0 && !eName) {
            $("#eNameFeedback").show().text("제품명을 입력해주세요.");
            $("input[name='eName']").focus();
            return;
        }

        // (기구) 제조사 입력값 검증
        if ($("input[name='eBrand']").length > 0 && !eBrand) {
            $("#eBrandFeedback").show().text("제조사를 입력해주세요.");
            $("input[name='eBrand']").focus();
            return;
        }

        // (기구) 구매 가격 입력값 검증
        if ($("input[name='ePurPrice']").length > 0 && !ePurPrice) {
            $("#ePurPriceFeedback").show().text("구매 가격을 입력해주세요.");
            $("input[name='ePurPrice']").focus();
            return;
        }

        // (기구) 사용 시작일
        if (eUseStart.length > 0) {
            if (!eUseStart.val()) {
                $("#startDateFeedback").show().text("사용 시작일을 입력해주세요.");
                eUseStart.focus();
                return;
            }
        }

        // (기구) 사용 종료일
        if (eUseEnd.length > 0) {
            if (!eUseEnd.val()) {
                $("#endDateFeedback").show().text("사용 종료일을 입력해주세요.");
                eUseEnd.focus();
                return;
            }
        }

        // (시설) 센터명
        let fCenterName = $("input[name='fCenterName']").val();
        if ($("input[name='fCenterName']").length > 0 && !fCenterName) {
            $("#fCenterNameFeedback").show().text("센터명을 입력해주세요.");
            $("input[name='fCenterName']").focus();
            return;
        }


        // (시설) 보증금
        let fDeposit = $("input[name='fDeposit']").val();
        if ($("input[name='fDeposit']").length > 0 && !fDeposit) {
            $("#fDepositFeedback").show().text("보증금을 입력해주세요.");
            $("input[name='fDeposit']").focus();
            return;
        }

        // (시설) 월세
        let fMonthRent = $("input[name='fMonthRent']").val();
        if ($("input[name='fMonthRent']").length > 0 && !fMonthRent) {
            $("#fMonthRentFeedback").show().text("월세를 입력해주세요.");
            $("input[name='fMonthRent']").focus();
            return;
        }

        // (시설) 관리비
        let fAdminCost = $("input[name='fAdminCost']").val();
        if ($("input[name='fAdminCost']").length > 0 && !fAdminCost) {
            $("#fAdminCostFeedback").show().text("관리비를 입력해주세요.");
            $("input[name='fAdminCost']").focus();
            return;
        }

        // (시설) 매매 사유
        let fReasonSale = $("input[name='fReasonSale']").val();
        if ($("input[name='fReasonSale']").length > 0 && !fReasonSale) {
            $("#fReasonSaleFeedback").show().text("매매 사유를 입력해주세요.");
            $("input[name='fReasonSale']").focus();
            return;
        }

        // (시설) 계약 면적
        let fContArea = $("input[name='fContArea']").val();
        if ($("input[name='fContArea']").length > 0 && !fContArea) {
            $("#fContAreaFeedback").show().text("계약 면적을 입력해주세요.");
            $("input[name='fContArea']").focus();
            return;
        }

        // (시설) 실면적
        let fRealArea = $("input[name='fRealArea']").val();
        if ($("input[name='fRealArea']").length > 0 && !fRealArea) {
            $("#fRealAreaFeedback").show().text("실면적을 입력해주세요.");
            $("input[name='fRealArea']").focus();
            return;
        }

        const target = document.querySelector(".uploadHidden");

        const imagePreview = document.querySelector("#imagePreview");
        const uploadFiles = imagePreview.querySelectorAll("img");

        // 이미지
        if (uploadFiles.length === 0) {
            $("#imageFeedback").show();
            $("#imageUpload").focus();
            return;
        }

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

        // 삭제 대상 파일들에 대한 삭제 요청
        callRemoveFiles();

        document.querySelector("form").submit();
    }, false);

});

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

// 희망 거래 장소에서 시/도, 시/군/구를 분리해주는 함수
function extractRegions(address) {
    const fullNames = {
        "서울": "서울특별시",
        "부산": "부산광역시",
        "대구": "대구광역시",
        "인천": "인천광역시",
        "광주": "광주광역시",
        "대전": "대전광역시",
        "울산": "울산광역시",
        "세종": "세종특별자치시",
        "경기": "경기도",
        "강원": "강원특별자치도",
        "충북": "충청북도",
        "충남": "충청남도",
        "전북": "전라북도",
        "전남": "전라남도",
        "경북": "경상북도",
        "경남": "경상남도",
        "제주": "제주특별자치도"
    };

    const parts = address.trim().split(/\s+/);

    if (parts.length < 2) return ["", ""];

    let metroGov = parts[0];
    if (fullNames[metroGov]) {
        metroGov = fullNames[metroGov];
    }

    const muniGov = parts[1];

    return [metroGov, muniGov];
}


