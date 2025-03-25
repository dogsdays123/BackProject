function trainerListModal() {
    // 모달과 버튼 참조
    const trainerModal = new bootstrap.Modal(document.querySelector("#trainerModal"));
    const viewButtonsTest = document.querySelectorAll(".viewbtn");
    
    // 버튼 동작 설정
    for (const key in viewButtonsTest) {
        if (Object.prototype.hasOwnProperty.call(viewButtonsTest, key)) {
            const element = viewButtonsTest[key];
            
            element.addEventListener("click", function(e) {
                trainerModal.show();
            });
        }
    }
}

document.querySelector(".pagination")

trainerListModal();