// 일반 게시글: 페이지 번호 클릭 시 이벤트를 처리하는 함수
const listPagination = document.querySelector("#listPagination");

if (listPagination != null) {
    listPagination.addEventListener("click", function (e) {
        paginationEvent(e, 12);
    }, false);
}

function paginationEvent(e, size, code) {
    console.log("페이지 번호 클릭됨!")

    e.preventDefault();
    e.stopPropagation();

    const target = e.target;

    if (target.tagName !== 'A') {
        console.log("-- 리턴 --")
        return;
    }

    const num = target.getAttribute("data-num");

    console.log("num: " + num);

    const formObj = document.querySelector("form");

    formObj.innerHTML += `<input type="hidden" name="page" value="${num}">`;
    formObj.innerHTML += `<input type="hidden" name="size" value="${size}">`;

    formObj.submit();
}

function toggleSellMenu() {
    let menu = document.getElementById("sell-menu");
    menu.classList.toggle("d-none"); // Bootstrap 클래스 사용
}

document.addEventListener('DOMContentLoaded', function () {
    // 판매하기 버튼 관련 js
    // dropdownMenuButton 버튼이 로드된 후에 클릭 이벤트를 설정
    const dropdownMenuButton = document.querySelector("#dropdownMenuButton");

    if (dropdownMenuButton != null) {
        dropdownMenuButton.addEventListener("click", function (e) {
            // th:data-member-type 값을 가져오기
            let memberType = this.getAttribute('data-member-type');

            if (memberType === 'default') {
                // 메세지 창 띄우기
                alert('판매하기는 개인 회원과 기업 회원만 이용 가능합니다. \n회원 전환 후 이용해주세요.');
            }
        });
    }

    // 사이드바 필터링 관련 js
    let categoryType = null;
    let currentUrl = new URL(window.location.href);
    const urlParams = new URLSearchParams(window.location.search);

    // 카테고리 상위(운동기구/운동시설) 메뉴 클릭 시 GET 요청
    document.querySelectorAll('.category-list').forEach(menu => {
        menu.addEventListener("click", function () {
            categoryType = this.getAttribute("data-category-type");
            currentUrl.searchParams.set("categoryType", categoryType);
            console.log(categoryType);

            currentUrl.searchParams.delete("categoryName");
            window.location.href = currentUrl.toString();
        });
    });

    // 카테고리 하위 메뉴 클릭 시 type과 keyword로 GET 요청
    document.querySelectorAll('[data-category-name]').forEach(item => {
        item.addEventListener("click", function () {

            let categoryName = this.getAttribute("data-category-name");
            currentUrl.searchParams.set("categoryName", categoryName);

            console.log(categoryName);
            console.log(currentUrl);

            window.location.href = currentUrl.toString();
        });
    });

    // 현재 URL에서 categoryType과 categoryName을 가져오기
    const categoryType2 = urlParams.get("categoryType");
    const categoryName2 = urlParams.get("categoryName");

    // categoryType에 해당하는 메뉴 펼치기
    if (categoryType2) {
        let menuId = categoryType2 === "eq" ? "#equipmentMenu" : categoryType2 === "fa" ? "#facilityMenu" : null;
        if (menuId) {
            document.querySelector(menuId).classList.add("show"); // Bootstrap collapse 활성화
            document.querySelector(menuId).classList.add("active"); // Bootstrap collapse 활성화
        }
    }

    // categoryName에 해당하는 항목에 active 클래스 추가
    if (categoryName2) {
        let selectedCategory = document.querySelector(`a.list-group-item[data-category-name="${categoryName2}"]`);
        if (selectedCategory) {
            selectedCategory.classList.add("active");
        }
    }

    // 지역 상위(시/도) 메뉴 클릭 시  GET 요청
    document.querySelectorAll('.metro-gov-list').forEach(menu => {
        menu.addEventListener("click", function () {
            let metroGov = this.getAttribute("data-metro-gov");
            currentUrl.searchParams.set("metroGov", metroGov);
            console.log("지역 상위(시/도) 메뉴 클릭:" + metroGov);

            window.location.href = currentUrl.toString();
        });
    });

    // 지역 하위(시/군/구) 메뉴 클릭 시 GET 요청
    document.querySelectorAll('[data-muni-gov]').forEach(item => {
        item.addEventListener("click", function () {

            let muniGov = this.getAttribute("data-muni-gov");
            let metroGov = this.getAttribute("data-metro-gov");
            currentUrl.searchParams.set("muniGov", muniGov);

            console.log("지역 하위(시/군/구) 메뉴 클릭(시/도): " + metroGov);
            console.log("지역 하위(시/군/구) 메뉴 클릭 (시/군/구): " + muniGov);

            if (!currentUrl.searchParams.has('metroGov')) {
                currentUrl.searchParams.set("metroGov", metroGov);
            }

            currentUrl.searchParams.set("muniGov", muniGov);
            window.location.href = currentUrl.toString();
        });
    });

    // 현재 URL에서 metroGov와 muniGov 가져오기
    const metroGovParam = urlParams.get("metroGov");
    const muniGovParam = urlParams.get("muniGov");

    // metroGov에 해당하는 메뉴 펼치기
    if (metroGovParam) {
        if (metroGovParam) {
            document.querySelector('#province').classList.add("show"); // Bootstrap collapse 활성화
            document.querySelector(`#${metroGovParam}`).classList.add("active"); // Bootstrap collapse 활성화
            document.querySelector(`#${metroGovParam}`).classList.add("show"); // Bootstrap collapse 활성화
        }
    }

    // muniGov에 해당하는 항목에 active 클래스 추가
    if (muniGovParam) {
        let selectedMunigov = document.querySelector(`a.list-group-item[data-muni-gov="${muniGovParam}"]`);
        if (selectedMunigov) {
            selectedMunigov.classList.add("active");
        }
    }

    document.querySelector('.price-apply').addEventListener('click', function () {
        const min = parseInt(document.getElementById('minPrice').value.trim());
        const max = parseInt(document.getElementById('maxPrice').value.trim());

        // 최소값이 최대값보다 큰 경우
        if (min > max) {
            alert('최소 가격은 최대 가격보다 작거나 같아야 합니다.');
            return;
        }

        // GET 방식으로 파라미터 전송 (예: 현재 페이지 기준)
        const url = new URL(window.location.href);
        url.searchParams.set('minPrice', min);
        url.searchParams.set('maxPrice', max);

        // 페이지 이동
        window.location.href = url.toString();
    });

    document.querySelector('.status-filter1').addEventListener('change', function () {
        const url = new URL(window.location.href);
        const params = url.searchParams;

        if (this.checked) {
            params.set('onSale', 'true');
        } else {
            params.delete('onSale');
        }

        // 새 URL로 이동 (페이지 리로드)
        window.location.href = url.toString();
    });

    document.querySelector('.status-filter2').addEventListener('change', function () {
        const url = new URL(window.location.href);
        const params = url.searchParams;

        if (this.checked) {
            params.set('reserved', 'true');
        } else {
            params.delete('reserved');
        }

        // 새 URL로 이동 (페이지 리로드)
        window.location.href = url.toString();
    });

    document.querySelector('.status-filter3').addEventListener('change', function () {
        const url = new URL(window.location.href);
        const params = url.searchParams;

        if (this.checked) {
            params.set('soldOut', 'true');
        } else {
            params.delete('soldOut');
        }

        // 새 URL로 이동 (페이지 리로드)
        window.location.href = url.toString();
    });


});

function updateURL(event) {
    event.preventDefault(); // 폼 기본 제출 방지

    const keyword = document.getElementById('searchKeyword').value.trim();
    const urlParams = new URLSearchParams(window.location.search);

    if (keyword) {
        urlParams.set('keyword', keyword);
    } else {
        urlParams.delete('keyword'); // 입력값이 없을 경우 제거
    }

    urlParams.set('type', 't'); // 항상 type=t 추가

    // 현재 URL을 갱신
    window.location.href = window.location.pathname + '?' + urlParams.toString();
}

// 지역 필터
// 대한민국 지역 데이터
const regions = {
    "시/도": {
        "서울특별시": [
            "종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구",
            "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구",
            "강남구", "송파구", "강동구"
        ],
        "부산광역시": [
            "중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구",
            "강서구", "연제구", "수영구", "사상구", "기장군"
        ],
        "대구광역시": [
            "중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군"
        ],
        "인천광역시": [
            "중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군"
        ],
        "광주광역시": [
            "동구", "서구", "남구", "북구", "광산구"
        ],
        "대전광역시": [
            "동구", "중구", "서구", "유성구", "대덕구"
        ],
        "울산광역시": [
            "중구", "남구", "동구", "북구", "울주군"
        ],
        "세종특별자치시": [
            "세종시"
        ],
        "경기도": [
            "수원시", "성남시", "의정부시", "안양시", "부천시", "광명시", "평택시", "동두천시", "안산시", "고양시", "과천시",
            "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시", "파주시", "이천시", "안성시",
            "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "연천군", "가평군", "양평군"
        ],
        "강원특별자치도": [
            "춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군",
            "정선군", "철원군", "화천군", "양구군", "인제군", "고성군", "양양군"
        ],
        "충청북도": [
            "청주시", "충주시", "제천시", "보은군", "옥천군", "영동군", "증평군", "진천군", "괴산군", "음성군", "단양군"
        ],
        "충청남도": [
            "천안시", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "부여군", "서천군",
            "청양군", "홍성군", "예산군", "태안군"
        ],
        "전라북도": [
            "전주시", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군",
            "순창군", "고창군", "부안군"
        ],
        "전라남도": [
            "목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군",
            "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군"
        ],
        "경상북도": [
            "포항시", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "의성군",
            "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군"
        ],
        "경상남도": [
            "창원시", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시", "의령군", "함안군", "창녕군",
            "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군"
        ],
        "제주특별자치도": [
            "제주시", "서귀포시"
        ]
    }
};

const regionList = document.getElementById("regionList");

// 데이터 기반으로 지역 카테고리 생성
Object.keys(regions).forEach(province => {
    let provinceId = `province`;
    regionList.innerHTML += `<a class="list-group-item"
                                data-bs-toggle="collapse" data-bs-target="#${provinceId}">${province}</a>`;

    let cityList = `<div class="collapse ps-3" id="${provinceId}">`;
    Object.keys(regions[province]).forEach(city => {
        let cityId = `${city}`;
        cityList += `<a class="list-group-item metro-gov-list" data-bs-toggle="collapse" data-metro-gov="${city}"
                        data-bs-target="#${cityId}">${city}</a>`;

        let districtList = `<div class="collapse ps-3" id="${cityId}">`;
        regions[province][city].forEach(district => {
            districtList += `<a class="list-group-item muni-gov-list" data-metro-gov="${city}"
                                data-muni-gov="${district}">${district}</a>`;
        });
        districtList += `</div>`;
        cityList += districtList;
    });
    cityList += `</div>`;
    regionList.innerHTML += cityList;
});








