function trainerDeleteModal() {
    // 모달 참조
    const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));
    const deleteBtn = document.getElementById("register-delete-btn");
    
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

tableTimeCalc();
initModValues();
trainerDeleteModal();
