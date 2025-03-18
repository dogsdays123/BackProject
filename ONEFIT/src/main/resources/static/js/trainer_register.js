tableTimeCalc();
tableRowManipulate();

document.getElementById("register-submit-btn").addEventListener("click", function(e) {
    e.preventDefault();
    e.stopPropagation();

    const form = document.getElementById("register-trainer");
    const noSubmit = document.querySelectorAll(".do-not-submit");

    // 값 매핑
    document.getElementById("academy-real").value = academyToJson();
    document.getElementById("career-real").value = careerToJson();
    document.getElementById("license-real").value = licenseToJson();
    document.getElementById("prize-real").value = prizeToJson();

    // 제출하지 말아야 할 input 비활성화
    for (const noSubmitElement of noSubmit) {
        noSubmitElement.disabled = true;

    }

    form.submit();
})

function academyToJson() {
    return tableToJson(document.getElementById("academy-table"), ["ty", "st", "gr", "sc", "mj"]);
}

function careerToJson() {
    return tableToJson(document.getElementById("career-table"), ["in", "jb", "yr", "cn"]);
}

function licenseToJson() {
    return tableToJson(document.getElementById("license-table"), ["ti", "in", "dt"]);
}

function prizeToJson() {
    return tableToJson(document.getElementById("prize-table"), ["ti", "in", "dt"]);
}