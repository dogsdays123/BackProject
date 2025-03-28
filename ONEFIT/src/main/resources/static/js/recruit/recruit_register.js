
// 채용공고 스텝 1~7 분할
let currentStep = 0;  // 현재 단계 (0부터 시작)
const steps = document.querySelectorAll(".step");  // 모든 단계 (step)
const prevBtn = document.getElementById("prevBtn");  // 이전 버튼
const nextBtn = document.getElementById("nextBtn");  // 다음 버튼
const submitBtn = document.getElementById("submitBtn");  // 제출 버튼
const jobForm = document.getElementById("jobForm"); // 폼 참조

function updateStep() {
    steps.forEach((step, index) => {
        step.style.display = index === currentStep ? "block" : "none";
    });

    prevBtn.style.display = currentStep === 0 ? "none" : "inline-block"; // 첫 단계에서 이전 버튼 숨기기
    nextBtn.style.display = currentStep === steps.length - 1 ? "none" : "inline-block"; // 마지막 단계에서 다음 버튼 숨기기
    submitBtn.classList.toggle("d-none", currentStep !== steps.length - 1); // 마지막 단계에서 제출 버튼 보이기
}

nextBtn.addEventListener("click", () => {
    if (currentStep < steps.length - 1) {
        currentStep++;
        updateStep();
    }
});

prevBtn.addEventListener("click", () => {
    if (currentStep > 0) {
        currentStep--;
        updateStep();
    }
});

// 모달 관련
document.addEventListener("DOMContentLoaded", function () {
    const modalTitle = document.getElementById("deleteModalLabel");
    const modalBody = document.querySelector(".delete-modal-body p");
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));

    // 제출 버튼 클릭 시 모달 띄우기
    submitBtn.addEventListener("click", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 모달 제목 및 내용 변경
        modalTitle.innerHTML = '<span class="rogo-span">OneFit</span> 등록 확인';
        modalBody.innerHTML = "등록하시겠습니까?";

        // 모달 열기
        deleteModal.show();
    });





    document.getElementById("jobForm").addEventListener("submit", function(event) {
        // business_id를 임의로 설정 (예: 12345)
        const businessIdInput = document.createElement("input");
        businessIdInput.type = "hidden"; // 숨겨진 필드로 설정
        businessIdInput.name = "businessId";
        businessIdInput.value = "1"; // 임의의 business_id 값 설정

        // 폼에 business_id 추가
        this.appendChild(businessIdInput);
    });
});

updateStep();  // 초기 단계 설정


// 등록 버튼
document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById("submitBtn");
    const modalTitle = document.getElementById("deleteModalLabel");
    const modalBody = document.querySelector(".delete-modal-body p");
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));

    submitBtn.addEventListener("click", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 모달 제목 및 내용 변경
        modalTitle.innerHTML = '<span class="rogo-span">OneFit</span> 등록 확인';
        modalBody.innerHTML = "등록하시겠습니까?";

        // 삭제 확인 입력 필드 및 버튼 숨기기


        // 모달 열기
        deleteModal.show();
    });
});


// 글자 수 카운트
const textInput = document.getElementById('recuit-title');
const counter = document.getElementById('title-counter');

textInput.addEventListener('input', () => {
    const currentLength = textInput.value.length;
    counter.textContent = `${currentLength}/40`;
});

const textInput1 = document.getElementById('recuit-name');
const counter1 = document.getElementById('name-counter');

textInput1.addEventListener('input', () => {
    const currentLength1 = textInput1.value.length;
    counter1.textContent = `${currentLength1}/20`;
});

const textInput2 = document.getElementById('recuit-salary-detail');
const counter2 = document.getElementById('salary-detail-counter');

textInput2.addEventListener('input', () => {
    const currentLength2 = textInput2.value.length;
    counter2.textContent = `${currentLength2}/40`;
});

// reJopType 라디오버튼 처리
// 라디오 버튼을 클릭할 때 체크를 해제하도록 하는 이벤트 리스너 추가


// 근무 시간
function generateTimeOptions(selectElement) {
    const startHour = 0;  // 00:00부터 시작
    const endHour = 23;   // 23:30까지
    for (let hour = startHour; hour <= endHour; hour++) {
        for (let min of ["00", "30"]) { // 30분 단위
            let formattedHour = hour.toString().padStart(2, "0"); // 두 자리 유지
            let time = `${formattedHour}:${min}`;
            let option = new Option(time, time);
            selectElement.add(option);
        }
    }
}


// 드롭다운 옵션 생성
const startTimeSelect = document.getElementById("re_work_start_time");
const endTimeSelect = document.getElementById("re_work_end_time");
generateTimeOptions(startTimeSelect);
generateTimeOptions(endTimeSelect);

// 기본값 설정 (예: 09:00 ~ 18:00)
startTimeSelect.value = "09:00";
endTimeSelect.value = "18:00";
updateHiddenInput(); // 초기값 반영

function updateHiddenInput() {
    const startTime = startTimeSelect.value;
    const endTime = endTimeSelect.value;
    const hiddenStart = document.getElementById("hidden_start_time");
    const hiddenEnd = document.getElementById("hidden_end_time");
    if (startTime >= endTime) {
        alert("근무 시작 시간은 종료 시간보다 이전이어야 합니다.");
        return;
    }

    hiddenStart.value = startTime;
    hiddenEnd.value = endTime;
}

// 연령 처리 함수
function updateAge() {
    const ageRadios = document.getElementsByName('reAgeType');
    let ageValue = '';
    let minAgeValue = document.getElementById('age_min').value;
    let maxAgeValue = document.getElementById('age_max').value;

    // 라디오 버튼 상태에 따라 값 업데이트
    ageRadios.forEach(radio => {
        if (radio.checked) {
            ageValue = radio.nextElementSibling.innerText;  // 라벨 텍스트 가져오기
            if (ageValue === "연령무관") {
                document.getElementById('hidden_age_min').value = '연령무관';
                document.getElementById('hidden_age_max').value = '연령무관';
            } else if (ageValue === "연령제한 있음" && minAgeValue && maxAgeValue) {
                document.getElementById('hidden_age_min').value = minAgeValue;
                document.getElementById('hidden_age_max').value = maxAgeValue;
            }
        }
    });
}

// 라디오 버튼 이벤트 리스너
document.getElementById('age_no_limit').addEventListener('change', () => {
    document.getElementById('age_min').disabled = true;
    document.getElementById('age_max').disabled = true;
    document.getElementById('age_min').value = '';
    document.getElementById('age_max').value = '';
    updateAge();
});

document.getElementById('age_limit').addEventListener('change', () => {
    document.getElementById('age_min').disabled = false;
    document.getElementById('age_max').disabled = false;
    updateAge();
});

// 나이 범위 변경 시 이벤트 리스너
document.getElementById('age_min').addEventListener('change', updateAge);
document.getElementById('age_max').addEventListener('change', updateAge);

// 초기 설정
updateAge();

document.addEventListener("DOMContentLoaded", function () {
    const emailLocal = document.getElementById("email_local");
    const emailCustom = document.getElementById("email_custom");
    const emailOutput = document.getElementById("email_output");

    function updateEmailOutput() {
        let localPart = emailLocal.value.trim();
        let domainPart = emailCustom.value.trim();

        if (localPart && domainPart) {
            emailOutput.value = `${localPart}@${domainPart}`;
        } else {
            emailOutput.value = "";
        }
    }

    // 입력 필드 값이 변경될 때마다 이메일 주소 업데이트
    emailLocal.addEventListener("input", updateEmailOutput);
    emailCustom.addEventListener("input", updateEmailOutput);
});

document.addEventListener("DOMContentLoaded", function () {
    const phoneFirst = document.getElementById("phone_first");
    const phoneMid = document.getElementById("phone_mid");
    const phoneLast = document.getElementById("phone_last");
    const phoneOutput = document.getElementById("phone_output");

    function updatePhoneOutput() {
        let firstPart = phoneFirst.value.trim();
        let midPart = phoneMid.value.trim();
        let lastPart = phoneLast.value.trim();

        if (firstPart && midPart && lastPart) {
            phoneOutput.value = `${firstPart}-${midPart}-${lastPart}`;
        } else {
            phoneOutput.value = "";
        }
    }

    phoneFirst.addEventListener("input", updatePhoneOutput);
    phoneMid.addEventListener("input", updatePhoneOutput);
    phoneLast.addEventListener("input", updatePhoneOutput);
})




const uploadResult = document.querySelector(".uploadResult");
const uploadBasicResult = document.querySelector(".uploadBasicResult");

document.querySelector(".uploadMainBtn").addEventListener("click", function (e){
    const formObj = new FormData();

    const fileInput = document.querySelector("input[name='mainFiles']")

    console.log(fileInput.files);

    const files = fileInput.files;

    for(let i = 0 ; i<files.length;i++){
        formObj.append("files", files[i]);
    }

    uploadToServer(formObj).then(result=>{
        console.log(result)
        for(const uploadResult of result){
            showUploadFile(uploadResult);
        }
    }).catch(e=>{
        alert("upload error")
    })
},false)

document.querySelector(".uploadBasicBtn").addEventListener("click", function (e){
    const formObj = new FormData();

    const fileInput1 = document.querySelector("input[name='basicFiles']")

    console.log(fileInput1.files);

    const files = fileInput1.files;

    for(let i = 0 ; i<files.length;i++){
        formObj.append("files", files[i]);
    }

    uploadToServer(formObj).then(result=>{
        console.log(result)
        for(const uploadResult of result){
            showUploadFile(uploadResult);
        }
    }).catch(e=>{
        alert("upload error")
    })
},false)

function showUploadBasicFile({uuid, fileName, link}) {
    const str =

        ` <div class="imgcard card col-4 card-${uuid}">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span class="file-name" style="width:400px">${fileName}</span>
                <button class="btn-sm btn-danger" onclick="javascript:removeFile('${uuid}', '${fileName}', this)">X</button>
            </div>
                        <div class="card col-4" hidden="hidden">
            <div class="card-body" >
                <img src="/view_recruit/${link}" data-src="${uuid + "_" + fileName}" class="img-fluid img-thumbnail">
            </div>
        </div>`;

    uploadBasicResult.innerHTML += str;
}


function showUploadFile({uuid, fileName, link}) {
    const str =

       ` <div class="imgcard card col-4 card-${uuid}">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span class="file-name" style="width:400px">${fileName}</span>
                <button class="btn-sm btn-danger" onclick="javascript:removeFile('${uuid}', '${fileName}', this)">X</button>
            </div>
            <div class="card col-4" hidden="hidden">
            <div class="card-body" >
                <img src="/view_recruit/${link}" data-src="${uuid + "_" + fileName}" class="img-fluid img-thumbnail">
            </div>
        </div>
       </div>`;

    uploadResult.innerHTML += str;
}

function removeFile(uuid, fileName, obj){
    event.preventDefault();
    console.log(uuid);
    console.log(fileName);
    console.log(obj);

    const targetDiv = obj.closest(".card")

    removeFileToServer(uuid, fileName).then(data => {
        targetDiv.remove();
    })
}

// 모달에서 확인 버튼 클릭 시 폼 제출
document.getElementById("register-submit-ok-btn").addEventListener("click", function (e) {

    const target = document.querySelector(".uploadHidden")
    const uploadFiles = uploadResult.querySelectorAll("img");
    let str = ''
    for(let i = 0 ; i<uploadFiles.length; i++){
        const uploadFile = uploadFiles[i]
        const imgLink = uploadFile.getAttribute("data-src")
        str += `<input type='hidden' name='fileNames' value="${imgLink}">`
    }

    target.innerHTML = str;
    // 모달 닫기
    deleteModal.hide();

    // 폼 제출
    jobForm.submit();
});