// 입력창에 숫자만 입력할 수 있도록 하는 js/제이쿼리
$(document).ready(function () {
    const maxLength = 10;

    $('input.number-input[type="text"]').on('keypress', function (e) {
        const char = String.fromCharCode(e.which);

        // 숫자 외 입력 방지
        if (!/[0-9]/.test(char)) {
            e.preventDefault();
            return;
        }

        const val = $(this).val();

        // 첫 자리에 0 입력 방지
        if (val.length === 0 && char === '0') {
            e.preventDefault();
            return;
        }

        // 최대 길이 제한
        if (val.length >= maxLength) {
            e.preventDefault();
        }
    });

    // 붙여넣기 등 input 처리
    $('input.number-input[type="text"]').on('input', function () {
        let val = $(this).val();

        // 숫자만 남기기
        val = val.replace(/[^0-9]/g, '');

        // 첫 자리 0 제거
        if (val.length > 0) {
            val = val.replace(/^0+/, '');
        }

        // 최대 길이 제한
        if (val.length > maxLength) {
            val = val.substring(0, maxLength);
        }

        $(this).val(val);
    });
});
