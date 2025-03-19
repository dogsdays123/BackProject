// 썸네일 파일 업로드 함수
async function uploadThumbnails(formObj) {
    console.log("upload thumbnails");
    console.log(formObj);

    const response = await axios({
        method: 'post',
        url: '/trainer_thumbnail/upload',
        data: formObj,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });

    return response.data;
}

// 썸네일 파일 삭제 함수
async function removeThumbnails(uuid, fileName) {
    const response = await axios.delete(`/trainer_thumbnail/remove/${uuid}_${fileName}`);
    return response.data;
}

// 미리보기 스크롤 참조
const sliderFrame = document.getElementById("slider-frame");
const leftSlider = document.getElementById("left-slider");
const rightSlider = document.getElementById("right-slider");
let currentSlide = 0;

// 미리보기 스크롤 함수
function checkSlide() {
    // 혹시 범위를 벗어날 경우 막기
    if (currentSlide < 0) {
        currentSlide = 0;
    } else if (currentSlide > sliderFrame.childElementCount - 1) {
        currentSlide = sliderFrame.childElementCount - 1;
    }

    console.log(currentSlide);

    leftSlider.disabled = currentSlide === 0;
    rightSlider.disabled = sliderFrame.childElementCount - 1 <= currentSlide;

    sliderFrame.scroll(currentSlide * sliderFrame.offsetWidth, 0);
}

// 미리보기 스크롤 이벤트
document.getElementById("thumbnails").addEventListener("change", function(e) {
    const files = e.target.files;
    sliderFrame.innerHTML = "";

    for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        const imagePreview = document.createElement("img");
        sliderFrame.appendChild(imagePreview);

        reader.onload = function(e) {
            imagePreview.src = e.target.result;
            imagePreview.style.width = "100%";   // 미리보기 이미지 크기 조정
            imagePreview.style.height = "100%";
            imagePreview.style.objectFit = "cover";
            imagePreview.style.flexShrink = "0";
        };

        reader.readAsDataURL(files[i]);
    }

    if (files.length === 0) {
        sliderFrame.innerHTML = `<img src="https://dummyimage.com/150x150/000/fff" class="profile-img" alt="프로필 사진">`;
    }

    currentSlide = 0;
    sliderFrame.scroll(0, 0);
    leftSlider.disabled = false;
    rightSlider.disabled = false;
});

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
