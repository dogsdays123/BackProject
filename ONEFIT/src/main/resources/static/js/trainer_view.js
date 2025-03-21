thumbnails.dispatchEvent(new Event('change'));

const academyContent = parseIfNotEmpty(viewDTO.academy);
const careerContent = parseIfNotEmpty(viewDTO.career);
const licenseContent = parseIfNotEmpty(viewDTO.license);
const prizeContent = parseIfNotEmpty(viewDTO.prize);
const academyTable = document.getElementById('academy-table-body');
const careerTable = document.getElementById("career-table-body");
const licenseTable = document.getElementById("license-table-body");
const prizeTable = document.getElementById("prize-table-body");

function parseIfNotEmpty(str) {
    if (str !== null && str.length > 0) {
        return JSON.parse(str);
    }
    return null;
}

function createTableData(value) {
    const td = document.createElement("td");
    const school = document.createElement("input");
    td.appendChild(school);
    school.type = "text";
    school.classList.add("form-control", "do-not-submit");
    school.readOnly = true;
    school.value = value;

    return td;
}

if (academyContent !== null) {
    for (const key of Object.keys(academyContent)) {
        const academy = academyContent[key];
        const row = document.createElement("tr");

        const c1 = createTableData(academy["ty"]);
        const c2 = createTableData(academy["st"]);
        const c3 = createTableData(academy["gr"]);
        const c4 = createTableData(academy["sc"]);
        const c5 = createTableData(academy["mj"]);

        row.appendChild(c1);
        row.appendChild(c2);
        row.appendChild(c3);
        row.appendChild(c4);
        row.appendChild(c5);

        academyTable.appendChild(row);
    }
}

if (careerContent !== null) {
    for (const key of Object.keys(careerContent)) {
        const career = careerContent[key];
        const row = document.createElement("tr");

        const c1 = createTableData(career["in"]);
        const c2 = createTableData(career["jb"]);
        const c3 = createTableData(career["yr"]);
        const c4 = createTableData(career["cn"]);

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

        const c1 = createTableData(license["ti"]);
        const c2 = createTableData(license["in"]);
        const c3 = createTableData(license["dt"]);

        row.appendChild(c1);
        row.appendChild(c2);
        row.appendChild(c3);

        careerTable.appendChild(row);
    }
}

if (prizeContent !== null) {
    for (const key of Object.keys(prizeContent)) {
        const prize = prizeContent[key];
        const row = document.createElement("tr");

        const c1 = createTableData(license["ti"]);
        const c2 = createTableData(license["in"]);
        const c3 = createTableData(license["dt"]);

        row.appendChild(c1);
        row.appendChild(c2);
        row.appendChild(c3);

        prizeTable.appendChild(row);
    }
}

tableTimeCalc();