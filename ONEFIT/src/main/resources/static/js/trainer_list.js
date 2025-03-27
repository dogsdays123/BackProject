// dtoList 참조
const cardList = resDTO.dtoList;

// 모달 참조
const trainerModal = document.getElementById("trainerModal");

// 페이지네이션 참조
const trainerPagination = document.getElementById("trainer-pagination");

// 검색 필터링 참조
const searchTrainer = document.getElementById("search-trainer");
const experienceFilter = document.getElementById("experience");
const educationFilter = document.getElementById("education");
const regionFilter = document.getElementById("region");
const sortFilter = document.getElementById("sort");
const sizeFilter = document.getElementById("job");
const orderingFilter = document.getElementById("ordering");

// 모달에 데이터 채우는 함수
function fillModalData(index) {
    const viewDTO = cardList[index];

    // 입력정보 삽입
    // value 또는 text 에 직접 들어가므로 초기화 필요 없음
    document.getElementById("trainer-title").innerText = viewDTO.title;
    document.getElementById("trainer-name").value = viewDTO.name;
    document.getElementById("trainer-gender").value = viewDTO.gender;
    document.getElementById("trainer-birthday").value = viewDTO.birthday;
    document.getElementById("trainer-email").value = viewDTO.email;
    document.getElementById("trainer-phone").value = viewDTO.phone;
    document.getElementById("trainer-address").value = viewDTO.address;
    document.getElementById("trainer-content").textContent = viewDTO.content;
    document.getElementById("trainer-acfinal").value = viewDTO.academyFinal;
    document.getElementById("trainer-cperiod").value = viewDTO.careerPeriod;
    document.getElementById("trainer-job").value = viewDTO.wantJob;
    document.getElementById("trainer-type").value = viewDTO.wantType;
    document.getElementById("trainer-region").value = viewDTO.wantLegion;
    document.getElementById("trainer-time").value = viewDTO.wantTime;
    document.getElementById("trainer-day").value = viewDTO.wantDay;
    document.getElementById("trainer-day-type").value = viewDTO.wantDayType;
    document.getElementById("trainer-sal").value = viewDTO.wantSal;
    document.getElementById("trainer-sal-type").value = viewDTO.wantSalType;

    // 데이터 삽입 전 테이블들 초기화
    // 아래에서 appendChild 로 삽입하기 때문에 필요
    document.getElementById("academy-table-body").innerHTML = "";
    document.getElementById("career-table-body").innerHTML = "";
    document.getElementById("license-table-body").innerHTML = "";
    document.getElementById("prize-table-body").innerHTML = "";

    // 테이블 데이터들 삽입
    viewInit(viewDTO);

    // 데이터가 없는 테이블은 숨기기
    // 숨겨진 테이블들을 드러내는 효과도 있음
    if (viewDTO.career === null || viewDTO.career.length === 0) {
        document.getElementById("career-table").hidden = true;
    } else {
        document.getElementById("career-table").hidden = false;
    }

    if (viewDTO.license === null || viewDTO.license.length === 0) {
        document.getElementById("license-table").hidden = true;
    } else {
        document.getElementById("license-table").hidden = false;
    }

    if (viewDTO.prize === null || viewDTO.prize.length === 0) {
        document.getElementById("prize-table").hidden = true;
    } else {
        document.getElementById("prize-table").hidden = false;
    }

    // 프로필란 초기화
    const sFrame = document.getElementById("slider-frame");
    sFrame.innerHTML = "";

    // 프로필 이미지들 삽입
    for (const thumbnail of viewDTO.thumbnails) {
        const img = document.createElement("img");
        img.classList.add("profile-img");
        img.src = `/trainer_thumbnail/view/${thumbnail}`
        sFrame.appendChild(img);
    }

    if (viewDTO.thumbnails === null || viewDTO.thumbnails.length === 0) {
        sFrame.innerHTML = `<img src="https://dummyimage.com/150x150/000/fff" class="profile-img" alt="프로필 사진">`;
    }

    // 이력서를 페이지에서 보기 버튼 이벤트 등록
    document.getElementById("view-submit-btn").href = `/trainer/trainer_view?tid=${viewDTO.trainerId}&${resDTO.link}`;
}

function trainerListModal() {
    // 모달과 버튼 참조
    const trainerModal = new bootstrap.Modal(document.querySelector("#trainerModal"));
    const modalElement = document.getElementById("trainerModal");
    const viewButtonsTest = document.querySelectorAll(".viewbtn");

    // 모달이 닫히기 전 이벤트
    modalElement.addEventListener("hide.bs.modal", function(e) {
        currentSlide = 0;
        checkSlide("instant");
    });

    // 버튼 동작 설정
    for (const key in viewButtonsTest) {
        if (Object.prototype.hasOwnProperty.call(viewButtonsTest, key)) {
            const element = viewButtonsTest[key];

            element.addEventListener("click", function(e) {
                e.preventDefault();
                e.stopPropagation();
                fillModalData(element.getAttribute("name"))
                checkSlide();
                trainerModal.show();
            });
        }
    }
}

// 검색 필터링 이벤트 등록
experienceFilter.addEventListener("change", function(e) {
    e.preventDefault();
    e.stopPropagation();
    searchTrainer.submit();
});

educationFilter.addEventListener("change", function(e) {
    e.preventDefault();
    e.stopPropagation();
    searchTrainer.submit();
});

regionFilter.addEventListener("change", function(e) {
    e.preventDefault();
    e.stopPropagation();
    searchTrainer.submit();
});

// 오름차순으로 하고 싶으면 해당 조건에 대해 orderingFilter 를 0 이상의 수치로 바꿀것.
sortFilter.addEventListener("change", function(e) {
    e.preventDefault();
    e.stopPropagation();
    searchTrainer.submit();
});

sizeFilter.addEventListener("change", function(e) {
    e.preventDefault();
    e.stopPropagation();
    searchTrainer.submit();
});

trainerPagination.addEventListener("click", function(e) {
    e.preventDefault();
    e.stopPropagation();

    const target = e.target;
    console.log(target);

    if (target.tagName != 'A') {
        return;
    }

    const pageInput = document.createElement("input");
    pageInput.hidden = true;
    pageInput.type = "text";
    pageInput.setAttribute("name", "page");
    pageInput.value = target.getAttribute("name");
    searchTrainer.appendChild(pageInput);
    searchTrainer.submit();
});

const sliderFrame = document.getElementById("slider-frame");
const leftSlider = document.getElementById("left-slider");
const rightSlider = document.getElementById("right-slider");
let currentSlide = 0;

// 미리보기 스크롤 함수
function checkSlide(flag) {
    // 혹시 범위를 벗어날 경우 막기
    if (currentSlide < 0) {
        currentSlide = 0;
    } else if (currentSlide > sliderFrame.childElementCount - 1) {
        currentSlide = sliderFrame.childElementCount - 1;
    }

    console.log("slide: " + currentSlide);

    leftSlider.disabled = currentSlide === 0;
    rightSlider.disabled = sliderFrame.childElementCount - 1 <= currentSlide;

    if (flag !== null) {
        sliderFrame.scroll({top: 0, left: currentSlide * sliderFrame.offsetWidth, behavior: flag});
    } else {
        sliderFrame.scroll(currentSlide * sliderFrame.offsetWidth, 0);
    }
}

// 썸네일 슬라이드 화살표 버튼 이벤트
leftSlider.addEventListener("click", function(e) {
    e.preventDefault();
    e.stopPropagation();

    currentSlide = currentSlide - 1;
    checkSlide();
});

rightSlider.addEventListener("click", function(e) {
    e.preventDefault();
    e.stopPropagation();

    currentSlide = currentSlide + 1;
    checkSlide();
});

trainerListModal();