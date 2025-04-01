// 입력창에 숫자만 입력할 수 있도록 하는 js
document.querySelectorAll('.number-input').forEach(input => {
    input.addEventListener('oninput', function (e) {
        const allowedKeys = [
            'Backspace', 'Delete', 'ArrowLeft', 'ArrowRight', 'Tab'
        ];
        if (
            !(e.key >= '0' && e.key <= '9') &&
            !allowedKeys.includes(e.key)
        ) {
            e.preventDefault();
        }
    });

    input.addEventListener('input', function () {
        // let value = this.value.replace(/[^0-9]/g, '');
        // // 천 단위 콤마 추가
        // this.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        this.value = this.value.replace(/[^0-9]/g, '');
    });
});

document.querySelectorAll('.only-number').forEach(input => {
    input.addEventListener('keydown', function (e) {
        const key = e.key;
        const value = this.value;
        const cursorPos = this.selectionStart;
        const allowedKeys = [
            'Backspace', 'Delete', 'ArrowLeft', 'ArrowRight', 'Tab'
        ];

        // 숫자 입력 허용
        if (key >= '0' && key <= '9') return;

        // 소수점 1개만 허용
        if (key === '.' && !value.includes('.')) return;

        // 특수 키 허용
        if (allowedKeys.includes(key)) return;

        // 그 외는 막기
        e.preventDefault();
    });

    input.addEventListener('input', function () {
        let val = this.value;

        // 복붙 등으로 이상한 문자 들어온 경우만 필터
        val = val.replace(/[^0-9.]/g, '');

        // 소수점이 여러 개인 경우 첫 번째만 유지
        const parts = val.split('.');
        if (parts.length > 2) {
            val = parts[0] + '.' + parts[1];
        }

        // 소수점 둘째 자리까지만 유지
        if (parts.length === 2) {
            parts[1] = parts[1].slice(0, 2);
            val = parts[0] + '.' + parts[1];
        }

        // 최대값 제한
        const num = parseFloat(val);
        if (!isNaN(num) && num > 99999.99) {
            val = '99999.99';
        }

        this.value = val;
    });
});

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
        let chatUrl = $("#pChatUrl").val().trim();

        // 오픈채팅 URL 검증
        if (chatUrl !== "" && !validateAndFormatLink(chatUrl)) {
            $("#urlFeedback").show().text("카카오 오픈채팅 URL 형식이 아닙니다.");
            isValid = false;
        } else {
            $("#urlFeedback").hide();
        }

        // 사용 종료일 검증 (시작일보다 늦어야 함)
        if ($("#eUseStart").length > 0 && $("#eUseEnd").length > 0) {
            let startDate = new Date($("#eUseStart").val());
            let endDate = new Date($("#eUseEnd").val());

            if ($("#eUseStart").val() && $("#eUseEnd").val()) {
                if (endDate <= startDate) {
                    $("#endDateFeedback").show().text("사용 종료일은 시작일보다 늦어야 합니다.");
                    isValid = false;
                } else {
                    $("#startDateFeedback").hide();
                    $("#endDateFeedback").hide();
                }
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

    // (기구) - 제품명
    if ($("input[name='eName']").length > 0) {
        $("input[name='eName']").on("input", function () {
            let eName = $(this).val();

            if (!eName) {
                $("#eNameFeedback").show().text("제품명을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#eNameFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // (기구) - 제조사
    if ($("input[name='eBrand']").length > 0) {
        $("input[name='eBrand']").on("input", function () {
            let eBrand = $(this).val();

            if (!eBrand) {
                $("#eBrandFeedback").show().text("제조사를 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#eBrandFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // (기구) - 구매 가격
    if ($("input[name='ePurPrice']").length > 0) {
        $("input[name='ePurPrice']").on("input", function () {
            let ePurPrice = $(this).val();

            if (!ePurPrice) {
                $("#ePurPriceFeedback").show().text("구매 가격을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#ePurPriceFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // (기구) - 사용 시작일
    if ($("#eUseStart").length > 0) {
        $("#eUseStart").on("input", function () {
            let eUseStart = $(this).val();

            if (!eUseStart) {
                $("#startDateFeedback").show().text("사용 시작일을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#startDateFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // (기구) - 사용 종료일
    if ($("#eUseEnd").length > 0) {
        $("#eUseEnd").on("input", function () {
            let eUseEnd = $(this).val();

            if (!eUseEnd) {
                $("#endDateFeedback").show().text("사용 종료일을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#endDateFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // (시설) - 센터명
    if ($("input[name='fCenterName']").length > 0) {
        $("input[name='fCenterName']").on("input", function () {
            let fCenterName = $(this).val();
            if (!fCenterName) {
                $("#fCenterNameFeedback").show().text("센터명을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fCenterNameFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // (시설) - 보증금
    if ($("input[name='fDeposit']").length > 0) {
        $("input[name='fDeposit']").on("input", function () {
            let fDeposit = $(this).val();
            if (!fDeposit) {
                $("#fDepositFeedback").show().text("보증금을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fDepositFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // 월세
    if ($("input[name='fMonthRent']").length > 0) {
        $("input[name='fMonthRent']").on("input", function () {
            let fMonthRent = $(this).val();
            if (!fMonthRent) {
                $("#fMonthRentFeedback").show().text("월세를 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fMonthRentFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // 관리비
    if ($("input[name='fAdminCost']").length > 0) {
        $("input[name='fAdminCost']").on("input", function () {
            let fAdminCost = $(this).val();
            if (!fAdminCost) {
                $("#fAdminCostFeedback").show().text("관리비를 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fAdminCostFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // 매매 사유
    if ($("input[name='fReasonSale']").length > 0) {
        $("input[name='fReasonSale']").on("input", function () {
            let fReasonSale = $(this).val();
            if (!fReasonSale) {
                $("#fReasonSaleFeedback").show().text("매매 사유를 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fReasonSaleFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // 계약 면적
    if ($("input[name='fContArea']").length > 0) {
        $("input[name='fContArea']").on("input", function () {
            let fContArea = $(this).val();
            if (!fContArea) {
                $("#fContAreaFeedback").show().text("계약 면적을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fContAreaFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    // 실면적
    if ($("input[name='fRealArea']").length > 0) {
        $("input[name='fRealArea']").on("input", function () {
            let fRealArea = $(this).val();
            if (!fRealArea) {
                $("#fRealAreaFeedback").show().text("실면적을 입력해주세요.");  // 경고 문구 표시
            } else {
                $("#fRealAreaFeedback").hide();  // 입력 시 경고 문구 숨김
            }
        });
    }

    if ($("#eUseStart").length > 0 && $("#eUseEnd").length > 0) {
        $("#pChatUrl, #eUseStart, #eUseEnd").on("input", validateForm);
    } else {
        $("#pChatUrl").on("input", validateForm);
    }

});