tableTimeCalc();
thumbnails.dispatchEvent(new Event('change'));

const academyContent = JSON.parse(viewDTO.academy);
const careerContent = JSON.parse(viewDTO.career);
const licenseContent = JSON.parse(viewDTO.license);
const prizeContent = JSON.parse(viewDTO.prize);
const academyTable = document.getElementById('academy-table-body');
const careerTable = document.getElementById("career-table-body");
const licenseTable = document.getElementById("license-table-body");
const prizeTable = document.getElementById("prize-table-body");

function createTableRow(value) {
    const tr = document.createElement("tr");
    const td = document.createElement("td");
    const school = document.createElement("input");
    school.type = "text";
    school.classList.add("form-control", "do-not-submit");
    school.readOnly = true;
    tr.appendChild(td);
    td.appendChild(school);
    school.value = value;
    return tr;
}

for (const academy of academyContent) {

}

for (const career of careerContent) {

}

for (const license of licenseContent) {

}

for (const prize of prizeContent) {

}
