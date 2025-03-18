// 근무시간 계산 함수
function calculateWorkTime(workStart, workEnd, worktimeCounter) {
    if (workStart.value === '' || workEnd.value === '') {
        worktimeCounter.innerText = ": 0.0시간";
        return;
    }

    let starting = new Date();
    let ending = new Date();

    let startTimes = workStart.value.split(":");
    let endTimes = workEnd.value.split(":");

    starting.setHours(startTimes[0], startTimes[1], 0);
    ending.setHours(endTimes[0], endTimes[1], 0);

    if (ending <= starting) {
        ending.setDate(ending.getDate() + 1);
    }

    let result = (Math.round((ending - starting) / (3600 * 1000) * 10) / 10.0).toFixed(1);

    worktimeCounter.innerText = ": " + result + "시간";
    document.getElementById("want-time-real").value = result;
}

// 테이블 JSON 직렬화 함수
function tableToJson(table, names) {
    let result = {};

    for (let i = 1; i < table.rows.length; i++) {
        let j = 0;
        let semiResult = {};

        for (const cell of table.rows[i].cells) {
            let content = cell.firstElementChild;
            semiResult[names[j]] = content.value;
            j += 1;
        }

        result[i] = semiResult;
    }
    return JSON.stringify(result);
}

// 근무시간 계산 이벤트 등록
function tableTimeCalc() {
    // 근무시간 계산용 참조
    const worktimeCounter = document.getElementById("worktime-counter");
    const workStart = document.getElementById("work-start");
    const workEnd = document.getElementById("work-end");
    
    workStart.addEventListener("input", function(e) {
        calculateWorkTime(workStart, workEnd, worktimeCounter);
    });
    
    workEnd.addEventListener("input", function(e) {
        calculateWorkTime(workStart, workEnd, worktimeCounter);
    });
}

// 테이블 행 조작버튼 이벤트 등록
function tableRowManipulate() {    
    // 테이블 참조
    const academyTable = document.getElementById("academy-table");
    const careerTable = document.getElementById("career-table");
    const licenseTable = document.getElementById("license-table");
    const prizeTable = document.getElementById("prize-table");
    
    // 행 조작 버튼 참조
    const academyPlus = document.getElementById("academy-plus");
    const careerPlus = document.getElementById("career-plus");
    const licensePlus = document.getElementById("license-plus");
    const prizePlus = document.getElementById("prize-plus");
    
    const academyMinus = document.getElementById("academy-minus");
    const careerMinus = document.getElementById("career-minus");
    const licenseMinus = document.getElementById("license-minus");
    const prizeMinus = document.getElementById("prize-minus");
    
    // 이벤트 등록
    academyPlus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        // academyTable.insertRow(-1).innerHTML = '<td><input type="text" class="form-control"></td>' +
        //                   '<td><input type="text" class="form-control"></td>' +
        //                   '<td><input type="date" class="form-control"></td>' +
        //                   '<td><input type="text" class="form-control"></td>' +
        //                   '<td><input type="text" class="form-control"></td>';
        
        academyTable.insertRow(-1).innerHTML = `
                                <td>
                                    <select class="form-control do-not-submit">
                                        <option value="고등학교">고등학교</option>
                                        <option value="전문대학교">전문대학교</option>
                                        <option value="대학교">대학교</option>
                                        <option value="대학원">대학원</option>
                                    </select>
                                </td>
                                <td>
                                    <select class="form-control do-not-submit">
                                        <option value="졸업">졸업</option>
                                        <option value="재학">재학</option>
                                        <option value="휴학">휴학</option>
                                        <option value="중퇴">중퇴</option>
                                    </select>
                                </td>
                                <td><input type="date" class="form-control do-not-submit" required></td>
                                <td><input type="text" class="form-control do-not-submit" placeholder="학교명" required></td>
                                <td><input type="text" class="form-control do-not-submit" value="-" required></td>`;
    });
    
    careerPlus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        careerTable.insertRow(-1).innerHTML = `
                                  <td><input type="text" class="form-control do-not-submit" placeholder="조직명"></td>
                                  <td><input type="text" class="form-control do-not-submit" placeholder="직함"></td>
                                  <td><input type="number" class="form-control do-not-submit" min="0" placeholder="기간"></td>
                                  <td><input type="text" class="form-control do-not-submit" placeholder="경력 내용"></td>`;
    });
    
    licensePlus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        licenseTable.insertRow(-1).innerHTML = `
                                <tr>
                                  <td><input type="text" class="form-control do-not-submit" placeholder="명칭"></td>
                                  <td><input type="text" class="form-control do-not-submit" placeholder="기관명"></td>
                                  <td><input type="date" class="form-control do-not-submit"></td>
                                </tr>`;
    });
    
    prizePlus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        prizeTable.insertRow(-1).innerHTML = `
                                  <td><input type="text" class="form-control do-not-submit" placeholder="명칭"></td>
                                  <td><input type="text" class="form-control do-not-submit" placeholder="내용"></td>
                                  <td><input type="date" class="form-control do-not-submit"></td>`;
    });
    
    academyMinus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        if (academyTable.rows.length > 2) {
            academyTable.deleteRow(-1);
        }
    });
    
    careerMinus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        if (careerTable.rows.length > 2) {
            careerTable.deleteRow(-1);
        }
    });
    
    licenseMinus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        if (licenseTable.rows.length > 2) {
            licenseTable.deleteRow(-1);
        }
    });
    
    prizeMinus.addEventListener("click", function(e) {
        e.preventDefault();
        e.stopPropagation();
    
        if (prizeTable.rows.length > 2) {
            prizeTable.deleteRow(-1);
        }
    });
}
