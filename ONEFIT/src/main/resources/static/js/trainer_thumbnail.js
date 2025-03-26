// 미리보기 스크롤 참조
const sliderFrame = document.getElementById("slider-frame");
const leftSlider = document.getElementById("left-slider");
const rightSlider = document.getElementById("right-slider");
const thumbnails = document.getElementById("thumbnails");
const thumbnailsList = document.getElementById("thumbnails-list");
let currentSlide = 0;
let thumbnailsTransfer = new DataTransfer();

// 파일 선택창 화면에서 숨기기
thumbnails.style.opacity = "0";
thumbnails.style.pointerEvents = "none";
thumbnails.style.position = "absolute";
thumbnails.style.top = "160px";

// 미리보기 스크롤 함수
function checkSlide() {
    // 혹시 범위를 벗어날 경우 막기
    if (currentSlide < 0) {
        currentSlide = 0;
    } else if (currentSlide > sliderFrame.childElementCount - 1) {
        currentSlide = sliderFrame.childElementCount - 1;
    }

    console.log("slide: " + currentSlide);

    leftSlider.disabled = currentSlide === 0;
    rightSlider.disabled = sliderFrame.childElementCount - 1 <= currentSlide;

    sliderFrame.scroll(currentSlide * sliderFrame.offsetWidth, 0);
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

// 파일 업로드 이벤트
// 업로드 준비중인 파일 리스트를 삭제했을 때 강제 이벤트 발생으로 리셋하는 역할도 하고있다.
thumbnails.addEventListener("change", function(e) {
    e.preventDefault();
    e.stopPropagation();

    const files = e.target.files;

    // 요소 초기화
    sliderFrame.innerHTML = "";
    thumbnailsList.innerHTML = "";
    thumbnailsList.hidden = false;
    thumbnailsTransfer.items.clear();

    // 파일별로 이미지 미리보기 처리
    for (let i = 0; i < files.length; i++) {
        // Element 생성
        const reader = new FileReader();
        const imagePreview = document.createElement("img");
        const imagePara = document.createElement("div");
        const paraSpan = document.createElement("span");
        const paraButton = document.createElement("button");
        const paraRepresentButton = document.createElement("button");

        imagePara.appendChild(paraSpan);
        imagePara.appendChild(paraButton);
        imagePara.appendChild(paraRepresentButton);

        // 업로드 버튼 아래 박스 관련 Element Style
        imagePara.style.position = "relative";
        imagePara.style.width = "100%";
        imagePara.style.display = "flex";
        imagePara.style.alignItems = "center";

        paraSpan.style.whiteSpace = "nowrap";
        paraSpan.style.overflow = "hidden";

        paraButton.type = "button";
        paraButton.style.position = "absolute";
        paraButton.style.right = "0px";
        paraButton.textContent = "x";
        paraButton.classList.add("btn", "btn-light");
        paraButton.style.padding = "0 5px";

        paraRepresentButton.type = "button";
        paraRepresentButton.style.position = "absolute";
        paraRepresentButton.style.right = "20px";
        paraRepresentButton.innerHTML = i === 0 ? "&#9733;" : "&#9734";
        paraRepresentButton.classList.add("btn", "btn-light");
        paraRepresentButton.style.padding = "0 5px";

        // 부모 요소에 append
        sliderFrame.appendChild(imagePreview);
        thumbnailsList.appendChild(imagePara);

        // Transfer 에 파일을 하나씩 기록해준다.
        thumbnailsTransfer.items.add(files[i]);

        // 파일이 로드에 성공하면 미리보기에 반영
        reader.onload = function(e) {
            imagePreview.src = e.target.result;
            imagePreview.style.width = "100%";   // 미리보기 이미지 크기 조정
            imagePreview.style.height = "100%";
            imagePreview.style.objectFit = "cover";
            imagePreview.style.flexShrink = "0";

            paraSpan.textContent = files[i].name;
        };

        // 이미지 제거 버튼용 이벤트 등록
        paraButton.addEventListener("click", function(e) {
            e.preventDefault();
            e.stopPropagation();

            thumbnailsTransfer.items.remove(i);

            const transfer = new DataTransfer();

            for (let i = 0; i < thumbnailsTransfer.items.length; i++) {
                transfer.items.add(thumbnailsTransfer.files[i]);
            }

            thumbnails.files = transfer.files;    // 이 부분이 주소 복사로만 되기 때문에 주의
            thumbnails.dispatchEvent(new Event('change'));
        });

        // 대표 이미지로 만들기 버튼 이벤트 등록
        // 첫 번째 버튼을 제외하고 이벤트를 등록한다.
        if (i !== 0) {
            paraRepresentButton.addEventListener("click", function(e) {
               e.preventDefault();
               e.stopPropagation();

               const transfer = new DataTransfer();
               transfer.items.add(thumbnailsTransfer.files[i]);
               thumbnailsTransfer.items.remove(i);

               for (let i = 0; i < thumbnailsTransfer.items.length; i++) {
                   transfer.items.add(thumbnailsTransfer.files[i]);
               }

               thumbnails.files = transfer.files;
               thumbnails.dispatchEvent(new Event('change'));
            });
        } else {
            // 첫 번째 버튼은 클릭이 안 되도록 한다.
            paraRepresentButton.style.pointerEvents = "none";
        }

        // 파일 읽기
        reader.readAsDataURL(files[i]);
    }

    // 파일이 하나도 없다면 임시 프로필 사진을 넣고 업로드 버튼 아래 박스를 숨겨준다.
    if (files.length === 0) {
        sliderFrame.innerHTML = `<img src="https://dummyimage.com/150x150/000/fff" class="profile-img" alt="프로필 사진">`;
        thumbnailsList.hidden = true;
    }

    currentSlide = 0;
    sliderFrame.scroll(0, 0);
    leftSlider.disabled = false;
    rightSlider.disabled = false;
});
