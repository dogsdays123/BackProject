// 공지사항 JS ---- //
const notices = document.querySelectorAll('.notice-text');
let noticeCurrentIndex = 0;

function changeNotice() {
    // 현재 보이는 공지사항 숨기기
    notices[noticeCurrentIndex].classList.remove('show');
    // 다음 공지사항 표시
    noticeCurrentIndex = (noticeCurrentIndex + 1) % notices.length;
    notices[noticeCurrentIndex].classList.add('show');
}

// 5초 간격으로 changeNotice 함수 실행
setInterval(changeNotice, 5000);



// 채용 소식 JS ---- //
let currentIndex = 0;  // 현재 슬라이드 인덱스 (0부터 시작)
const totalSlides = 2;  // 슬라이드 총 개수 (4개씩 공고 표시)

// 슬라이드 업데이트 함수
function updateCarousel() {
    const items = document.querySelectorAll(".carousel-item");
    // 모든 슬라이드를 숨기고
    items.forEach(item => item.classList.remove("active"));
    // 현재 인덱스에 해당하는 슬라이드만 활성화
    items[currentIndex].classList.add("active");
}

// 강사 소식 JS--------//

let currentAnnouncementIndex = 0;
const totalAnnouncementSlides = 2;

// 슬라이드 업데이트 함수
function updateCarousel(type) {
    const carouselId = type === 'announcement' ? "announcementItems" : "instructorItems";
    const items = document.querySelectorAll(`#${carouselId} .carousel-item`);

    // 모든 슬라이드를 숨기고
    items.forEach(item => item.classList.remove("active"));
    // 현재 인덱스에 해당하는 슬라이드만 활성화
    items[type === 'announcement' ? currentAnnouncementIndex : currentInstructorIndex].classList.add("active");
}