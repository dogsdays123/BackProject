document.addEventListener("DOMContentLoaded", function() {
    const apiKey = 'fa220b2ab314e7cc946b01754f58a2f0';

    let clickLat = null;  // 전역 변수로 위도 저장
    let clickLng = null;  // 전역 변수로 경도 저장
    let marker = null;    // 전역 변수로 마커 저장

    kakao.maps.load(() => {
        var container = document.getElementById('kakaoMap');
        var options = {
            center: new kakao.maps.LatLng(33.450701, 126.570667),  // 초기 중심 위치
            level: 3
        };
        var kakaoMap = new kakao.maps.Map(container, options);  // 카카오 지도 객체 생성

        // 페이지 로딩 시 자동으로 주소를 좌표로 변환하여 지도에 마커 표시
        const mainAddressElement = document.querySelector('#mainAddress');
        if (!mainAddressElement) {
            console.error('주소를 찾을 수 없습니다. #mainAddress 요소가 없습니다.');
            alert('주소를 찾을 수 없습니다.');
            return;
        }

        const address = mainAddressElement.value.trim();  // p 태그에서 텍스트 가져오기
        if (!address) {
            console.error('주소가 없습니다.');
            alert('주소를 입력해주세요!');
            return;
        }

        // 카카오 REST API를 사용하여 주소를 좌표로 변환
        const url = `https://dapi.kakao.com/v2/local/search/address.json?query=${encodeURIComponent(address)}`;

        fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `KakaoAK ${apiKey}`,  // Authorization 헤더에 앱 키를 포함
            }
        })
            .then(response => response.json())  // JSON 형식으로 응답 처리
            .then(data => {
                if (data.documents && data.documents.length > 0) {
                    const result = data.documents[0];
                    const lat = result.y;  // 위도
                    const lng = result.x;  // 경도
                    console.log(`주소: ${result.address_name}`);
                    console.log(`위도: ${lat}, 경도: ${lng}`);

                    clickLat = lat;  // 전역 변수로 위도 저장
                    clickLng = lng;

                    // 좌표를 이용해 지도에 마커 표시
                    var coords = new kakao.maps.LatLng(lat, lng);

                    // 마커 표시
                    if (marker) {
                        marker.setPosition(coords);  // 기존 마커 위치 변경
                    } else {
                        marker = new kakao.maps.Marker({
                            map: kakaoMap,
                            position: coords
                        });
                    }

                    // 인포윈도우 표시
                    var infowindow = new kakao.maps.InfoWindow({
                        content: `<div style="width:150px;text-align:center;padding:6px 0;">${result.address_name}</div>`
                    });
                    infowindow.open(kakaoMap, marker);

                    // 지도 중심을 결과값으로 받은 위치로 이동
                    kakaoMap.setCenter(coords);
                } else {
                    console.log('주소를 찾을 수 없습니다.');
                    alert('주소를 찾을 수 없습니다.');
                }
            })
            .catch(error => {
                console.error('요청 중 오류 발생:', error);
                alert('주소 검색 중 오류가 발생했습니다.');
            });
    });
});
// const apiKey = 'fa220b2ab314e7cc946b01754f58a2f0';
//
// let clickLat = null;  // 전역 변수로 위도 저장
// let clickLng = null;  // 전역 변수로 경도 저장
// let marker = null;
//
// kakao.maps.load(() => {
//     var container = document.getElementById('kakaoMap');
//     var options = {
//         center: new kakao.maps.LatLng(33.450701, 126.570667),
//         level: 3
//     };
//     var kakaoMap = new kakao.maps.Map(container, options);
//
//
//
//     // 주소를 좌표로 변환하기 위한 버튼 클릭 이벤트
//     document.querySelector("#getAddress").addEventListener("click", function (e) {
//         e.stopPropagation();
//         e.preventDefault();
//
//         const address = document.querySelector('#mainAddress').value.trim();
//         if (!address) {
//             alert('주소를 입력해주세요!');
//             return;
//         }
//
//         // 카카오 REST API를 사용하여 주소를 좌표로 변환
//         const url = `https://dapi.kakao.com/v2/local/search/address.json?query=${encodeURIComponent(address)}`;
//
//         // Fetch API를 사용하여 GET 요청
//         fetch(url, {
//             method: 'GET',
//             headers: {
//                 'Authorization': `KakaoAK ${apiKey}`,  // Authorization 헤더에 앱 키를 포함
//             }
//         })
//             .then(response => response.json())  // JSON 형식으로 응답 처리
//             .then(data => {
//                 if (data.documents && data.documents.length > 0) {
//                     const result = data.documents[0];
//                     const lat = result.y;  // 좌표 (위도)
//                     const lng = result.x;  // 좌표 (경도)
//                     console.log(`주소: ${result.address_name}`);
//                     console.log(`위도: ${lat}, 경도: ${lng}`);
//
//                     clickLat = lat;  // 전역 변수로 위도 저장
//                     clickLng = lng;
//
//                     // 좌표를 이용해 지도에 마커 표시
//                     var coords = new kakao.maps.LatLng(lat, lng);
//
//                     // 마커 표시
//                     var marker = new kakao.maps.Marker({
//                         map: kakaoMap,
//                         position: coords
//                     });
//
//                     // 인포윈도우 표시
//                     var infowindow = new kakao.maps.InfoWindow({
//                         content: `<div style="width:150px;text-align:center;padding:6px 0;">${result.address_name}</div>`
//                     });
//                     infowindow.open(kakaoMap, marker);
//
//                     // 지도 중심을 결과값으로 받은 위치로 이동
//                     kakaoMap.setCenter(coords);
//                 } else {
//                     console.log('주소를 찾을 수 없습니다.');
//                     alert('주소를 찾을 수 없습니다.');
//                 }
//             })
//             .catch(error => {
//                 console.error('요청 중 오류 발생:', error);
//                 alert('주소 검색 중 오류가 발생했습니다.');
//             });
//     });





    // 버튼 클릭 이벤트 추가: 버튼 클릭 시 좌표를 주소로 변환
    // document.getElementById('getAddress').addEventListener('click', function (e) {
    //     e.stopPropagation()
    //     e.preventDefault()
    //     if (clickLat === null || clickLng === null) {
    //         alert('먼저 지도에서 클릭하여 좌표를 선택해주세요.');
    //         return;
    //     }
    //
    //     const url = `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${clickLng}&y=${clickLat}&input_coord=WGS84`;
    //
    //     fetch(url, {
    //         method: 'GET',
    //         headers: {
    //             'Authorization': `KakaoAK ${apiKey}`,  // 인증 헤더
    //         }
    //     })
    //         .then(response => response.json())  // JSON 응답 처리
    //         .then(data => {
    //             if (data.meta.total_count > 0) {
    //                 const address = data.documents[0].road_address ? data.documents[0].road_address.address_name : data.documents[0].address.address_name;
    //                 console.log('주소:', address);
    //                 alert('주소: ' + address);
    //                 document.getElementById('addressInput').value = address;
    //             } else {
    //                 alert('주소를 찾을 수 없습니다.');
    //             }
    //         })
    //         .catch(error => {
    //             console.error('오류 발생:', error);
    //             alert('주소 검색 중 오류가 발생했습니다.');
    //         });
    // });
