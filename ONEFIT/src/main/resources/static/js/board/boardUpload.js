//서버에 파일을 업로드하는 비동기 함수
async function uploadToServer (formObj) {

    console.log("upload to server.....")
    console.log(formObj)

    //axios를 이용하여 서버에 파일 업로드 요청
    const response = await axios({
        method: 'post',
        url: '/upload_board', //업로드 API 엔드포인트
        data: formObj, //업로드할 데이터
        headers: {
            'Content-Type': 'multipart/form-data', //파일 업로드를 위한 헤더 설정
        },
    });
    return response.data //서버 응답 데이터 반환
}

//서버에 파일을 삭제하는 비동기 함수
async function removeFileToServer(uuid, fileName){

    //axios를 이용하여 파일 삭제 요청
    const response = await axios.delete(`/remove_board/${uuid}_${fileName}`)

    return response.data
}