function updateCharacterCount(textarea) {
    const maxLength = textarea.maxLength;
    const currentLength = textarea.value.length;
    const charCount = document.getElementById("char-count");
    charCount.textContent = `${currentLength}/${maxLength}`;
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
