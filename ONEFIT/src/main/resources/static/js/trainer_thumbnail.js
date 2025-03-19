// 썸네일 업로드 함수
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

// 썸네일 삭제 함수
async function removeThumbnails(uuid, fileName) {
    const response = await axios.delete(`/trainer_thumbnail/remove/${uuid}_${fileName}`);
    return response.data;
}

// 파일 미리보기
document.getElementById("thumbnails").addEventListener("change", function(e) {
    const files = e.target.files;
    const previewContainer = document.createElement("div");    // 미리보기 컨테이너

    for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const imagePreview = document.createElement("img");
            imagePreview.src = e.target.result;
            imagePreview.style.width = "100px";    // 미리보기 이미지 크기 조정
            previewContainer.appendChild(imagePreview);
        };
        reader.readAsDataURL(files[i]);
    }

    document.body.appendChild(previewContainer);
});
