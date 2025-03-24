function trainerDeleteModal() {
    // 모달 참조
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));
    const deleteBtn = document.getElementById("register-delete-btn");
    const deleteConfirmText = document.getElementById("delete-confirm-text");

    // 이벤트 등록
    deleteBtn.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        deleteModal.show();
    });
}

// 데이터가 없으면 JSON.parse() 가 오류를 뱉으므로 이렇게 처리
function parseIfNotEmpty(str) {
    if (str !== null && str.length > 0) {
        return JSON.parse(str);
    }
    return null;
}

function createSelectData(...options) {
    const select = document.createElement("select");
    for (const option of options) {
        const op = document.createElement("option");
        op.value = option;
        op.textContent = option;
        select.appendChild(op);
    }
    select.classList.add("form-control", "do-not-submit");
    return select;
}

function selectOption(select, match) {
    for (const option of select.options) {
        if (option.value === match) {
            option.selected = true;
        }
    }
}

// 이전에 Register 했던 데이터들은 수정 시에도 그대로 가지고 가야 한다.
// Work Time 은 이것으로부터 예외인데, 이건 데이터베이스에 계산된 값만 들어가기 때문
// 추후 필요하면 테이블을 수정한 뒤 정식으로 넣을 수도 있음
function initModValues() {
    // 참조
    const academyContent = parseIfNotEmpty(modDTO.academy);
    const careerContent = parseIfNotEmpty(modDTO.career);
    const licenseContent = parseIfNotEmpty(modDTO.license);
    const prizeContent = parseIfNotEmpty(modDTO.prize);
    const academyTable = document.getElementById('academy-table-body');
    const careerTable = document.getElementById("career-table-body");
    const licenseTable = document.getElementById("license-table-body");
    const prizeTable = document.getElementById("prize-table-body");

    function createTableInputData(type, value) {
        const td = document.createElement("td");
        const input = document.createElement("input");
        td.appendChild(input);
        input.type = type;
        input.classList.add("form-control", "do-not-submit");
        input.value = value;

        return td;
    }

    if (academyContent !== null) {
        for (const key of Object.keys(academyContent)) {
            const academy = academyContent[key];
            const row = document.createElement("tr");

            const td1 = document.createElement("td");
            const td2 = document.createElement("td");

            const ty = createSelectData("고등학교", "전문대학교", "대학교", "대학원");
            const st = createSelectData("졸업", "재학", "휴학", "중퇴");
            const gr = createTableInputData("date", academy["gr"]);
            const sc = createTableInputData("text", academy["sc"]);
            const mj = createTableInputData("text", academy["mj"]);

            selectOption(ty, academy["ty"]);
            selectOption(st, academy["st"]);

            gr.firstElementChild.required = true;
            sc.firstElementChild.required = true;
            mj.firstElementChild.required = true;

            td1.appendChild(ty);
            td2.appendChild(st);

            row.appendChild(td1);
            row.appendChild(td2);
            row.appendChild(gr);
            row.appendChild(sc);
            row.appendChild(mj);

            console.log(row);
            academyTable.appendChild(row);
        }
    }

    if (careerContent !== null) {
        for (const key of Object.keys(careerContent)) {
            const career = careerContent[key];
            const row = document.createElement("tr");

            const c1 = createTableInputData("text", career["in"]);
            const c2 = createTableInputData("text", career["jb"]);
            const c3 = createTableInputData("number", career["yr"]);
            const c4 = createTableInputData("text", career["cn"]);

            row.appendChild(c1);
            row.appendChild(c2);
            row.appendChild(c3);
            row.appendChild(c4);

            careerTable.appendChild(row);
        }
    }

    if (licenseContent !== null) {
        for (const key of Object.keys(licenseContent)) {
            const license = licenseContent[key];
            const row = document.createElement("tr");

            const c1 = createTableInputData("text", license["ti"]);
            const c2 = createTableInputData("text", license["in"]);
            const c3 = createTableInputData("data", license["dt"]);

            row.appendChild(c1);
            row.appendChild(c2);
            row.appendChild(c3);

            licenseTable.appendChild(row);
        }
    }

    if (prizeContent !== null) {
        for (const key of Object.keys(prizeContent)) {
            const prize = prizeContent[key];
            const row = document.createElement("tr");

            const c1 = createTableInputData("text", prize["ti"]);
            const c2 = createTableInputData("text", prize["in"]);
            const c3 = createTableInputData("date", prize["dt"]);

            row.appendChild(c1);
            row.appendChild(c2);
            row.appendChild(c3);

            prizeTable.appendChild(row);
        }
    }
}

function originalThumbnailInit() {
    const regiThumbList = document.getElementById("regithumb-list");
    const sliderFrame = document.getElementById("slider-frame");
    const files = modDTO.thumbnails;

    regiThumbList.innerHTML = "";
    regiThumbList.hidden = false;

    for (let i = 0; i < files.length; i++) {
        const imagePreview = sliderFrame.children[i];
        const imagePara = document.createElement("div");
        const paraSpan = document.createElement("span");
        const paraInput = document.createElement("input");
        const paraButton = document.createElement("button");
        const paraRepresentButton = document.createElement("button");

        imagePara.appendChild(paraInput);
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
        paraSpan.textContent = files[i].slice(files[i].indexOf("_") + 1);

        paraInput.type = "text";
        paraInput.value = files[i];
        paraInput.hidden = true;
        paraInput.setAttribute("name", "originalThumbnails");

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
        regiThumbList.appendChild(imagePara);

        paraButton.addEventListener("click", function (e) {
            e.preventDefault();
            e.stopPropagation();

            removeFileToServer(paraInput.value).then(r => {
                console.log(r);
                imagePara.remove();
                imagePreview.remove();
                const btn = regiThumbList.querySelector("button:nth-of-type(2)");
                if (btn !== null) {
                    btn.innerHTML = "&#9733;";
                }
                checkRegithumbList();
            });
        });

        paraRepresentButton.addEventListener("click", function (e) {
            e.preventDefault();
            e.stopPropagation();

            changeRepresent(paraInput.value);

            sliderFrame.prepend(imagePreview);
            const btn = regiThumbList.querySelector("button:nth-of-type(2)");
            if (btn !== null) {
                btn.innerHTML = "&#9734;";
            }

            e.target.innerHTML = "&#9733;";
            regiThumbList.prepend(imagePara);
        });
    }

    if (files.length === 0) {
        regiThumbList.hidden = true;
    }
}

function checkRegithumbList() {
    const regiThumbList = document.getElementById("regithumb-list");
    if (regiThumbList.childElementCount === 0) {
        sliderFrame.innerHTML = `<img src="https://dummyimage.com/150x150/000/fff" class="profile-img" alt="프로필 사진">`;

        // 어차피 기존 이미지를 모두 날린 시점에서 의미가 없어졌으므로 삭제
        regiThumbList.remove();
    }
}

function clearRegiThumbList() {
    const regiThumbList = document.getElementById("regithumb-list");
    if (regiThumbList.childElementCount > 0) {
        for (const item of regiThumbList.children) {
            item.querySelector("button:nth-of-type(1)").dispatchEvent(new Event("click"));
        }
    }
}

// 이미지 선택을 새로 클릭하면 모든 이미지를 새로 등록해야 한다.
thumbnails.addEventListener("click", function (e) {
    clearRegiThumbList();
});

async function removeFileToServer(fileName) {
    const response = await axios.delete(`/trainer_thumbnail/delete/${fileName}?tid=${modDTO.trainerId}`);
    return response.data;
}

async function changeRepresent(fileName) {
    const response = await axios.put(`/trainer_thumbnail/change/${fileName}?tid=${modDTO.trainerId}`);
    return response.data;
}


tableTimeCalc();
initModValues();
originalThumbnailInit();
trainerDeleteModal();
