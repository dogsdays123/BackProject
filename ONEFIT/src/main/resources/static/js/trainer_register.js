tableTimeCalc();
tableRowManipulate();

function testAcademy() {
    return tableToJson(document.getElementById("academy-table"), ["ty", "st", "gr", "sc", "mj"]);
}

function testCareer() {
    return tableToJson(document.getElementById("career-table"), ["in", "jb", "yr", "cn"]);
}

function testLicense() {
    return tableToJson(document.getElementById("license-table"), ["ti", "in", "dt"]);
}

function testPrize() {
    return tableToJson(document.getElementById("prize-table"), ["ti", "in", "dt"]);
}