function trainerListModal() {
    // 모달과 버튼 참조
    const trainerModal = new bootstrap.Modal(document.querySelector("#trainerModal"));
    const viewButtonsTest = document.querySelectorAll(".viewbtn");
    
    // 버튼 동작 설정
    for (const key in viewButtonsTest) {
        if (Object.prototype.hasOwnProperty.call(viewButtonsTest, key)) {
            const element = viewButtonsTest[key];
            
            element.addEventListener("click", function(e) {
                trainerModal.show();
            });
        }
    }
}

// 페이지네이션 참조
const trainerPagination = document.getElementById("trainer-pagination");

// 검색 필터링 참조
const searchTrainer = document.getElementById("search-trainer");
const experienceFilter = document.getElementById("experience");
const educationFilter = document.getElementById("education");
const regionFilter = document.getElementById("region");
const sortFilter = document.getElementById("sort");
const sizeFilter = document.getElementById("job");

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

trainerListModal();