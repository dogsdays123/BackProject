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

trainerDeleteModal();
tableTimeCalc();
tableRowManipulate();