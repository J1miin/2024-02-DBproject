function updateCharacterCount(textarea) {
    const maxLength = textarea.maxLength;
    const currentLength = textarea.value.length;
    const charCount = document.getElementById("char-count");
    charCount.textContent = `${currentLength}/${maxLength}`;
}

function submitForm(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음

    // 폼 데이터 제출
    const form = document.getElementById('letterForm');
    const formData = new FormData(form);

    // AJAX로 폼 데이터를 서버로 제출
    fetch(form.action, {
        method: form.method,
        body: formData
    })
        .then(response => response.json()) // 서버에서 응답이 온 후
        .then(data => {
            // 서버 응답 처리 후 bakeFish 알림 호출
            bakeFish(); // 알림 및 페이지 이동 함수 호출
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

 function bakeFish() {

    // 현재 시간 저장
    const now = new Date().getTime();

    // 로컬 스토리지에서 기존 상태 가져오기
    const fishStates = JSON.parse(localStorage.getItem("fishStates") || "[]");

    // 새로운 안 익은 붕어빵 추가
    fishStates.push({state: "nonbaked", addedTime: now});

    // 로컬 스토리지 업데이트
    localStorage.setItem("fishStates", JSON.stringify(fishStates));

    // 메인 페이지로 이동
    alert("붕어빵이 추가되었습니다!");
    window.location.href = "main_writer.html";
}
