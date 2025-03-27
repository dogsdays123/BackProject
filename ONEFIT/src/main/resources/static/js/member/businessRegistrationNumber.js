// 모달 열기
document.getElementById("openModalBtn").onclick = function() {
    document.getElementById("myModal").style.display = "block";
}

// 모달 닫기
document.getElementsByClassName("close")[0].onclick = function() {
    document.getElementById("myModal").style.display = "none";
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    if (event.target == document.getElementById("myModal")) {
        document.getElementById("myModal").style.display = "none";
    }
}

// checkInput_null 함수 정의
function checkInput_null(formName, fieldIds) {

    var form = document.forms[formName];
    var fields = fieldIds.split(",");

    for (var i = 0; i < fields.length; i++) {
        var field = document.getElementById(fields[i]);
        if (field.value.trim() === "") {
            alert(field.alt + "을(를) 입력해주세요.");
            field.focus();
            return false;
        }
    }
    return true;
}

function code_check(e) {
    e.preventDefault()
    e.stopPropagation()

    if (!checkInput_null('frm1', 'c1,c2,c3')) {
        frm1.overlap_code_ok.value = "";
    } else {
        var code = frm1.c1.value + frm1.c2.value + frm1.c3.value;
        var data = {
            "b_no": [code]
        };

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=LrTpLPKTnvj5yzmwkL75xRIB9O2U312Ev%2F8KItvAzmjHhOUBRtJerxRdShcm%2FiitivEMqUsKxDrla%2FNi2%2BSfoA%3D%3D", //활용 신청 시 발급 되는 serviceKey값을 [서비스키]에 입력해준다.
            type: "POST",
            data: JSON.stringify(data),
            dataType: "JSON",
            traditional: true,
            contentType: "application/json; charset:UTF-8",
            accept: "application/json",
            success: function (result) {

                console.log("응답 결과: ", result);
                console.log(data);

                if (result.match_cnt == "1") {
                    console.log(result);

                    // 사업자 상태 등 데이터를 받아서 서버로 전송
                    var businessData = {
                        b_no: result.data[0].b_no,
                        b_stt: result.data[0].b_stt,
                        tax_type: result.data[0].tax_type,
                        rbf_tax_type: result.data[0].rbf_tax_type,
                        utcc_yn: result.data[0].utcc_yn
                    }

                    console.log(businessData);

                    // 숨겨진 필드에 값 설정
                    document.getElementById("b_no").value = businessData.b_no;
                    document.getElementById("b_stt").value = businessData.b_stt;
                    document.getElementById("tax_type").value = businessData.tax_type;
                    document.getElementById("rbf_tax_type").value = businessData.rbf_tax_type;
                    document.getElementById("utcc_yn").value = businessData.utcc_yn;

                    // bRegNum 텍스트 필드에 사업자 등록 번호 값 설정
                    var bRegNumField = document.getElementsByName("bRegNum")[0];
                    if (bRegNumField) {
                        bRegNumField.value = businessData.b_no; // textarea에 값 설정

                        // 변경된 값이 화면에 반영되도록 강제 트리거
                        var event = new Event('input', { 'bubbles': true });
                        bRegNumField.dispatchEvent(event); // input 이벤트 발생시켜 UI 반영
                    }

                    alert("올바른 사업자번호 입니다.\n" + businessData.tax_type);
                    document.getElementById("myModal").style.display = "none";


                    //document.frm1.submit();

                } else {
                    //실패
                    console.log("fail");
                    alert(result.data[0]["tax_type"]);
                }
            },
            error: function (result) {
                console.log("error");
                console.log(result.responseText); //responseText의 에러메세지 확인
            }
        });
    }
}